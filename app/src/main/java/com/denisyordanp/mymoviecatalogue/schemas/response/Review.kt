package com.denisyordanp.mymoviecatalogue.schemas.response

import com.denisyordanp.mymoviecatalogue.schemas.database.Review as DbReview

data class Review(
    val author: String,
    val authorDetails: AuthorDetail,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String
) {

    fun toEntity(movieId: Int): DbReview {
        return DbReview(
            id = id,
            movieId = movieId,
            author = author,
            content = content,
            createdAt = createdAt
        )
    }
}
