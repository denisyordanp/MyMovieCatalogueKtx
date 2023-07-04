package com.denisyordanp.mymoviecatalogue.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.denisyordanp.mymoviecatalogue.database.AppDatabase
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.schemas.database.Movie
import com.denisyordanp.mymoviecatalogue.schemas.database.bridge.RemoteKeys
import com.denisyordanp.mymoviecatalogue.tools.StackTrace
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val service: MovieService,
    private val database: AppDatabase,
    private val genreId: Long,
) : RemoteMediator<Int, Movie>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        try {
            val page: Int = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                    prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                    nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
            }

            val response = service.fetchMovies(
                page = page,
                genre = genreId
            )
            val movies = response.toEntities(genreId = genreId, page = page)
            val endOfPaginationReached = response.page >= response.totalPages

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.remoteKeysDao().clearRemoteKeys()
                    database.movieDao().clearAll(genreId)
                }
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = movies.map {
                    RemoteKeys(
                        movieID = it.id,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }

                database.remoteKeysDao().insertAll(remoteKeys)
                database.movieDao().insertMovies(movies.map { it.copy(page = page) })
            }
            return MediatorResult.Success(
                endOfPaginationReached = endOfPaginationReached
            )
        } catch (e: IOException) {
            StackTrace.printStackTrace(e)
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            StackTrace.printStackTrace(e)
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            database.remoteKeysDao().getRemoteKeyByMovieID(movie.id)
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Movie>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            database.remoteKeysDao().getRemoteKeyByMovieID(movie.id)
        }
    }
}