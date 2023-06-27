package com.denisyordanp.mymoviecatalogue.schemas.response

data class Videos(
    val id: Int,
    val results: List<Video>
)