package com.denisyordanp.mymoviecatalogue.schemas.response

import com.denisyordanp.mymoviecatalogue.schemas.database.Review as DbReview

data class Reviews(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val totalPages: Int,
    val totalResults: Int
) {

    fun toEntity(movieId: Long): List<DbReview> {
        return results.map {
            it.toEntity(movieId)
        }
    }
}
