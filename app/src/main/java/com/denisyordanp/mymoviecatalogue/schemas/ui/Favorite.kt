package com.denisyordanp.mymoviecatalogue.schemas.ui

import com.denisyordanp.mymoviecatalogue.schemas.database.Favorite as DbFavorite

data class Favorite(
    val id: Long,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: String,
    val voteCount: Long,
) {
    fun toDb() = DbFavorite(
        id = id,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        voteAverage = voteAverage.toDouble(),
        voteCount = voteCount
    )
}
