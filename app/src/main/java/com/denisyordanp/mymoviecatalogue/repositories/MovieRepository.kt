package com.denisyordanp.mymoviecatalogue.repositories

import androidx.paging.PagingSource
import com.denisyordanp.mymoviecatalogue.schemas.database.Movie

interface MovieRepository {
    fun getMovies(genreId: Long): PagingSource<Int, Movie>
}
