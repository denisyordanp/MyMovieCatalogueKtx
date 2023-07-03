package com.denisyordanp.mymoviecatalogue.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.denisyordanp.mymoviecatalogue.database.AppDatabase
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.schemas.database.Movie
import com.denisyordanp.mymoviecatalogue.schemas.database.RemotePages
import com.denisyordanp.mymoviecatalogue.tools.StackTrace
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class MovieRemoteMediator @Inject constructor(
    private val service: MovieService,
    private val database: AppDatabase,
) : RemoteMediator<Int, Movie>() {

    private var genreId: Long? = null

    fun setGenreId(genreId: Long): MovieRemoteMediator {
        this.genreId = genreId
        return this
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        return try {
            val label = Movie.getPageLabel(genreId!!)
            val remotePage = database.withTransaction {
                database.remotePagesDao().getPages(label)
            }
            // The network load method takes an optional after=<user.id>
            // parameter. For every page after the first, pass the last user
            // ID to let it continue from where it left off. For REFRESH,
            // pass null to load the first page.
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                // In this example, you never need to prepend, since REFRESH
                // will always load the first page in the list. Immediately
                // return, reporting end of pagination.
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {

                    // You must explicitly check if the last item is null when
                    // appending, since passing null to networkService is only
                    // valid for initial load. If lastItem is null it means no
                    // items were loaded after the initial REFRESH and there are
                    // no more items to load.
                    if (remotePage?.nextPage == 0) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remotePage?.nextPage ?: 1
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = service.fetchMovies(
                page = loadKey,
                genre = genreId!!
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.movieDao().clearAll(genreId!!)
                    val nextPage = response.page + 1
                    val newRemote = RemotePages(
                        label = label,
                        nextPage = nextPage
                    )
                    database.remotePagesDao().insertOrUpdate(newRemote)
                } else {
                    val nextPage = response.page + 1
                    val newRemote = remotePage?.copy(
                        nextPage = response.page + 1
                    ) ?: RemotePages(
                        label = label,
                        nextPage = nextPage
                    )
                    database.remotePagesDao().insertOrUpdate(newRemote)
                }

                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                database.movieDao().insertMovies(response.toEntities(genreId!!))
            }
            val isEnd = response.page >= response.totalPages
            MediatorResult.Success(
                endOfPaginationReached = isEnd
            )
        } catch (e: NullPointerException) {
            StackTrace.printStackTrace(e)
            MediatorResult.Error(e)
        } catch (e: IOException) {
            StackTrace.printStackTrace(e)
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            StackTrace.printStackTrace(e)
            MediatorResult.Error(e)
        }
    }

}