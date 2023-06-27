package com.denisyordanp.mymoviecatalogue.schemas.response

import com.denisyordanp.mymoviecatalogue.schemas.database.Movie as DbMovie

data class Movie(
    val id: Int,
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
) {
    fun toEntity(genreId: Int): DbMovie {
        return DbMovie(
            id = id,
            overview = overview,
            posterPath = posterPath,
            releaseDate = releaseDate,
            title = title,
            voteAverage = voteAverage,
            voteCount = voteCount,
            genreId = genreId
        )
    }
}