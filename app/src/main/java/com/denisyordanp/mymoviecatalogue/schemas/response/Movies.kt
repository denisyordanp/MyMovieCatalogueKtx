package com.denisyordanp.mymoviecatalogue.schemas.response

import com.denisyordanp.mymoviecatalogue.schemas.database.Movie as DbMovie

data class Movies(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
) {
    fun toEntities(genreId: Long): List<DbMovie> {
        return results.map {
            it.toEntity(genreId)
        }
    }

    companion object {
        const val PAGE_SIZE = 30
    }
}