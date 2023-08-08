package com.denisyordanp.mymoviecatalogue.schemas.ui

import com.denisyordanp.mymoviecatalogue.schemas.database.Favorite as DbFavorite

data class Favorite(
    override val id: Long,
    override val overview: String,
    override val posterPath: String,
    override val releaseDate: String,
    override val title: String,
    override val voteAverage: String,
    override val voteCount: Long,
) : Movie(id, overview, posterPath, releaseDate, title, voteAverage, voteCount) {
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
