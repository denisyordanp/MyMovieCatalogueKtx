package com.denisyordanp.mymoviecatalogue.schemas.response

import com.denisyordanp.mymoviecatalogue.schemas.database.Genre as DbGenre

data class Genres(
    val genres: List<Genre>
) {

    fun toEntities(): List<DbGenre> {
        return genres.map {
            it.toEntity()
        }
    }
}