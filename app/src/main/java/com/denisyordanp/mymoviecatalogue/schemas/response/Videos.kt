package com.denisyordanp.mymoviecatalogue.schemas.response

import com.denisyordanp.mymoviecatalogue.schemas.database.Video as DbVideo

data class Videos(
    val id: Int,
    val results: List<Video>
) {
    fun toEntity(movieId: Long): List<DbVideo> {
        return results.filter {
            it.isYoutube()
        }.map {
            it.toEntity(movieId)
        }
    }
}