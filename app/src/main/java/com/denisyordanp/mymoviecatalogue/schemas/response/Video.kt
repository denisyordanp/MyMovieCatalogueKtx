package com.denisyordanp.mymoviecatalogue.schemas.response

import com.denisyordanp.mymoviecatalogue.schemas.database.Video as DbVideo

data class Video(
    val id: String,
    val key: String,
    val name: String,
    val official: Boolean,
    val publishedAt: String,
    val site: String,
    val size: Int,
    val type: String
) {
    fun toEntity(movieId: Long): DbVideo {
        return DbVideo(
            id = id,
            movieId = movieId,
            key = key,
            name = name,
            publishedAt = publishedAt
        )
    }

    fun isYoutube(): Boolean {
        return site.lowercase() == YOUTUBE
    }

    companion object {
        const val YOUTUBE = "youtube"
    }
}
