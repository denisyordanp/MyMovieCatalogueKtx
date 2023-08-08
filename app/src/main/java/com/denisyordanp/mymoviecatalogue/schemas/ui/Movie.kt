package com.denisyordanp.mymoviecatalogue.schemas.ui

open class Movie(
    open val id: Long,
    open val overview: String,
    open val posterPath: String,
    open val releaseDate: String,
    open val title: String,
    open val voteAverage: String,
    open val voteCount: Long
)
