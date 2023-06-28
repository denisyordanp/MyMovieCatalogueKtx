package com.denisyordanp.mymoviecatalogue.schemas.database.bridge

import androidx.room.Embedded
import androidx.room.Relation
import com.denisyordanp.mymoviecatalogue.schemas.database.Genre
import com.denisyordanp.mymoviecatalogue.schemas.database.MovieDetail

data class MovieWithGenres(
    @Embedded
    val movie: MovieDetail,

    @Relation(parentColumn = Genre.ID_COLUMN, entityColumn = Genre.ID_COLUMN)
    val genres: List<Genre> = emptyList(),
)
