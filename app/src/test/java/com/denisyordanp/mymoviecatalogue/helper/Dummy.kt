package com.denisyordanp.mymoviecatalogue.helper

import kotlin.random.Random
import com.denisyordanp.mymoviecatalogue.schemas.response.Genre as ResponseGenre
import com.denisyordanp.mymoviecatalogue.schemas.database.Genre as DbGenre

object Dummy {
    fun getResponseGenres() = listOf(
        ResponseGenre(
            id = Random.nextLong(),
            name = String.random(2)
        ),
        ResponseGenre(
            id = Random.nextLong(),
            name = String.random(2)
        ),
    )

    fun getDbGenres() = listOf(
        DbGenre(
            id = Random.nextLong(),
            name = String.random(2)
        ),
        DbGenre(
            id = Random.nextLong(),
            name = String.random(2)
        ),
    )
}
