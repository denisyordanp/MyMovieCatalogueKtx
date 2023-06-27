package com.denisyordanp.mymoviecatalogue.schemas.response

data class Movies(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)