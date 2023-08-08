package com.denisyordanp.mymoviecatalogue.schemas.response

import com.denisyordanp.mymoviecatalogue.schemas.database.MovieDetail as DbMovieDetail
import com.denisyordanp.mymoviecatalogue.schemas.database.Genre as DbGenre

data class MovieDetail(
    val id: Long,
    val backdropPath: String,
    val posterPath: String,
    val budget: Long,
    val genres: List<Genre>,
    val overview: String,
    val releaseDate: String,
    val revenue: Long,
    val tagline: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Long
) {
    fun toEntity(): Pair<DbMovieDetail, List<DbGenre>> {
        return Pair(
            first = DbMovieDetail(
                id = id,
                backdropPath = backdropPath,
                posterPath = posterPath,
                budget = budget,
                overview = overview,
                releaseDate = releaseDate,
                revenue = revenue,
                tagline = tagline,
                title = title,
                voteAverage = voteAverage,
                voteCount = voteCount
            ),
            second = genres.map { it.toEntity() }
        )
    }
}
