package com.denisyordanp.mymoviecatalogue.schemas.response

import com.denisyordanp.mymoviecatalogue.schemas.database.Video as DbVideo

data class Videos(
    val id: Int,
    val results: List<Video>
) {
    fun toEntity(movieId: Int): List<DbVideo> {
        return results.map { it.toEntity(movieId) }
    }
}