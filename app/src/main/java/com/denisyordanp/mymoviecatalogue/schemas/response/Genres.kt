package com.denisyordanp.mymoviecatalogue.schemas.response

data class Genres(
    val genres: List<Genre>
) {

    fun toEntities(): List<com.denisyordanp.mymoviecatalogue.schemas.database.Genre> {
        return genres.map {
            it.toEntity()
        }
    }
}