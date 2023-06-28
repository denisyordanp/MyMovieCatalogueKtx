package com.denisyordanp.mymoviecatalogue.schemas.response

import com.denisyordanp.mymoviecatalogue.schemas.database.Genre as DbGenre

data class Genre(
    val id: Long,
    val name: String
) {

    fun toEntity(): DbGenre {
        return DbGenre(
            id = id,
            name = name
        )
    }
}