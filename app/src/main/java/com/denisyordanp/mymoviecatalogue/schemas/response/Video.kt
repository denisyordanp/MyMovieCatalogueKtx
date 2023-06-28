package com.denisyordanp.mymoviecatalogue.schemas.response

import com.denisyordanp.mymoviecatalogue.schemas.database.Video as DbVideo

data class Video(
    val id: String,
    val iso31661: String,
    val iso6391: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val publishedAt: String,
    val site: String,
    val size: Int,
    val type: String
) {
    fun toEntity(movieId: Int): DbVideo {
        return DbVideo(
            id = id,
            movieId = movieId,
            key = key,
            name = name,
            publishedAt = publishedAt
        )
    }
}