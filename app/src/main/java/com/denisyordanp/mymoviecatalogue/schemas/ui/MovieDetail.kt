package com.denisyordanp.mymoviecatalogue.schemas.ui

data class MovieDetail(
    val id: Long,
    val backdropPath: String,
    val budget: Long,
    val genres: List<Genre>,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val revenue: Long,
    val tagline: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Long
)
