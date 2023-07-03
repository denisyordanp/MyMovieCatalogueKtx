package com.denisyordanp.mymoviecatalogue.repositories.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.denisyordanp.mymoviecatalogue.database.AppDatabase
import com.denisyordanp.mymoviecatalogue.mediator.MovieRemoteMediator
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.MovieRepository
import com.denisyordanp.mymoviecatalogue.schemas.response.Movies
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val database: AppDatabase
) : MovieRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(genreId: Long, isForce: Boolean) = Pager(
        config = PagingConfig(
            pageSize = Movies.PAGE_SIZE,
            initialLoadSize = Movies.PAGE_SIZE,
            prefetchDistance = 10
        ),
        remoteMediator = MovieRemoteMediator(
            service = service, database = database, genreId = genreId, isForce = isForce
        ),
        pagingSourceFactory = {
            database.movieDao().getMovies(genreId)
        }
    ).flow.map {
        it.map { movie ->
            movie.toUi()
        }
    }
}
