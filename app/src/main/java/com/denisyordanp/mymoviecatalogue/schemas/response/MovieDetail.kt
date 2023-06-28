package com.denisyordanp.mymoviecatalogue.schemas.response

import com.denisyordanp.mymoviecatalogue.schemas.database.MovieDetail as DbMovieDetail
import com.denisyordanp.mymoviecatalogue.schemas.database.Genre as DbGenre

data class MovieDetail(
    val id: Long,
    val backdropPath: String,
    val budget: Long,
    val genres: List<Genre>,
    val homepage: String,
    val imdbId: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val revenue: Long,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Long
) {
    fun toEntity(): Pair<DbMovieDetail, List<DbGenre>> {
        return Pair(
            first = DbMovieDetail(
                id = id,
                backdropPath = backdropPath,
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
