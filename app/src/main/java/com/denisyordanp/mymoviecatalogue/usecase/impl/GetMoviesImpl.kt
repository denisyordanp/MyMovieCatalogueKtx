package com.denisyordanp.mymoviecatalogue.usecase.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
import com.denisyordanp.mymoviecatalogue.database.AppDatabase
import com.denisyordanp.mymoviecatalogue.mediator.MovieRemoteMediator
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.MovieRepository
import com.denisyordanp.mymoviecatalogue.schemas.response.Movies
import com.denisyordanp.mymoviecatalogue.usecase.GetMovies
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMoviesImpl @Inject constructor(
    private val repository: MovieRepository,
    private val service: MovieService,
    private val database: AppDatabase
) : GetMovies {
    @OptIn(ExperimentalPagingApi::class)
    override fun invoke(genreId: Long, isForce: Boolean) = Pager(
        config = PagingConfig(
            pageSize = Movies.PAGE_SIZE,
            initialLoadSize = Movies.PAGE_SIZE,
            prefetchDistance = 10
        ),
        remoteMediator = MovieRemoteMediator(
            service = service, database = database, genreId = 28, isForce = isForce
        ),
        pagingSourceFactory = {
            database.movieDao().getMovies(genreId)
//            repository.getMovies(genreId)
        }
    ).flow.map {
        it.map { movie ->
            movie.toUi()
        }
    }
}
