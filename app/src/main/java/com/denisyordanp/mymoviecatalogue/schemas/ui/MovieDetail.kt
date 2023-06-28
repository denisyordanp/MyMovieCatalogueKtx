package com.denisyordanp.mymoviecatalogue.schemas.ui

data class MovieDetail(
    val id: Long,
    val backdropPath: String,
    val budget: String,
    val genres: List<Genre>,
    val overview: String,
    val releaseDate: String,
    val revenue: String,
    val tagline: String,
    val title: String,
    val voteAverage: String,
    val voteCount: Long
)
