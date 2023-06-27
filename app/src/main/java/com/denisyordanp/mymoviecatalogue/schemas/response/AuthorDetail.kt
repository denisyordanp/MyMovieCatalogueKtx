package com.denisyordanp.mymoviecatalogue.schemas.response

data class AuthorDetail(
    val avatarPath: String,
    val name: String,
    val rating: Int,
    val username: String
)
