package com.denisyordanp.mymoviecatalogue.schemas.database.bridge

import androidx.room.Embedded
import androidx.room.Relation
import com.denisyordanp.mymoviecatalogue.schemas.database.Genre

data class MovieWithGenres(
    @Embedded
    val MovieGenre: MovieGenre,

    @Relation(parentColumn = Genre.ID_COLUMN, entityColumn = Genre.ID_COLUMN)
    val genre: Genre,
)
