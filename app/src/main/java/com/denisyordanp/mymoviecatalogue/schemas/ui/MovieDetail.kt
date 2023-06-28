package com.denisyordanp.mymoviecatalogue.schemas.ui

data class MovieDetail(
    val id: Int,
    val backdropPath: String,
    val budget: Int,
    val genres: List<Genre>,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val revenue: Int,
    val tagline: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int
)
