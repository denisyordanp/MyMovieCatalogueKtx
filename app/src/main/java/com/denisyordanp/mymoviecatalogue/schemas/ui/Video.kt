package com.denisyordanp.mymoviecatalogue.schemas.ui

data class Video(
    val id: String,
    val thumbnailPath: String,
    val name: String,
    val publishedAt: String,
    val key: String
)