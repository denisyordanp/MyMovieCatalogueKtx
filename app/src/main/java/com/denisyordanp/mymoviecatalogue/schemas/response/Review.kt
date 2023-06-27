package com.denisyordanp.mymoviecatalogue.schemas.response

data class Review(
    val author: String,
    val authorDetails: AuthorDetail,
    val content: String,
    val createdAt: String,
    val id: String,
    val updatedAt: String,
    val url: String
)
