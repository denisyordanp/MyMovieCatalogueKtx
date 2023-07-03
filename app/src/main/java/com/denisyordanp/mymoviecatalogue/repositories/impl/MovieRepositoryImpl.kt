package com.denisyordanp.mymoviecatalogue.repositories.impl

//import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.map
//import com.denisyordanp.mymoviecatalogue.database.AppDatabase
import com.denisyordanp.mymoviecatalogue.database.MoviesDao
import com.denisyordanp.mymoviecatalogue.mediator.MoviePagingSource
//import com.denisyordanp.mymoviecatalogue.mediator.MovieRemoteMediator
import com.denisyordanp.mymoviecatalogue.network.MovieService
import com.denisyordanp.mymoviecatalogue.repositories.MovieRepository
import com.denisyordanp.mymoviecatalogue.schemas.response.Movies
import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val service: MovieService,
    private val movieDao: MoviesDao,
//    private val database: AppDatabase
) : MovieRepository {

    override suspend fun reloadMovies(genreId: Long) {
        val movies = service.fetchMovies(genre = genreId).toEntities(genreId)
        movieDao.insertMovies(movies)
    }

    override fun getMovies(genreId: Long): Flow<List<Movie>> {
        return movieDao.getMovies(genreId).map { movies ->
            movies.map { movie ->
                movie.toUi()
            }
        }
    }

    override fun getMoviesPager(genreId: Long) = Pager(
        config = PagingConfig(pageSize = Movies.PAGE_SIZE),
        pagingSourceFactory = {
            MoviePagingSource(service, genreId)
        }
    ).flow.map {
        it.map {movie ->
            movie.toUi()
        }
    }

//    @OptIn(ExperimentalPagingApi::class)
//    override fun getMoviesPager(genreId: Long) = Pager(
//        config = PagingConfig(pageSize = 20),
//        remoteMediator = MovieRemoteMediator(
//            service, database, genreId
//        ),
//        pagingSourceFactory = {
//            movieDao.getMoviesPaging(genreId)
//        }
//    ).flow.map {
//        it.map {movie ->
//            movie.toUi()
//        }
//    }
}
