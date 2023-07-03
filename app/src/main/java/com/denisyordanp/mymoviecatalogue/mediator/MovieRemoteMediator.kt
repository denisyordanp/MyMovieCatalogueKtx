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
    private val genreId: Long
) : RemoteMediator<Int, Movie>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        return try {
            val label = Movie.getPageLabel(genreId)
            val remotePage = database.withTransaction {
                database.remotePagesDao().getPages(label)
            }
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    if (remotePage?.nextPage == 0) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }

                    remotePage?.nextPage ?: 1
                }
            }

            val response = service.fetchMovies(
                page = loadKey,
                genre = genreId
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.movieDao().clearAll(genreId)
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
                database.movieDao().insertMovies(response.toEntities(genreId))
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