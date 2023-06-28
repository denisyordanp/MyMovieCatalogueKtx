package com.denisyordanp.mymoviecatalogue.schemas.ui

data class Movie(
    val id: Long,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Long
)