package com.denisyordanp.mymoviecatalogue.schemas.response

import com.denisyordanp.mymoviecatalogue.schemas.database.Genre

data class Genre(
    val id: Int,
    val name: String
) {

    fun toEntity(): Genre {
        return Genre(
            id = id,
            name = name
        )
    }
}