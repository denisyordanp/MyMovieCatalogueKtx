package com.denisyordanp.mymoviecatalogue.schemas.response

data class Reviews(
    val id: Int,
    val page: Int,
    val results: List<Review>,
    val totalPages: Int,
    val totalResults: Int
)
