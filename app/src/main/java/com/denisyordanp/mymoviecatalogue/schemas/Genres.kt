package com.denisyordanp.mymoviecatalogue.schemas

data class Genres(
    val genres: List<Genre>
) {
    companion object {
        val dummies = listOf(
            Genre(
                id = 1,
                name = "Action"
            ),
            Genre(
                id = 2,
                name = "Thriller"
            ),
            Genre(
                id = 3,
                name = "Drama"
            ),
            Genre(
                id = 4,
                name = "Adventure"
            ),
            Genre(
                id = 5,
                name = "Animation"
            ),
            Genre(
                id = 6,
                name = "Comedy"
            ),
            Genre(
                id = 7,
                name = "Crime"
            ),
            Genre(
                id = 8,
                name = "Documentary"
            ),
            Genre(
                id = 9,
                name = "Family"
            ),
            Genre(
                id = 10,
                name = "Fantasy"
            ),
            Genre(
                id = 11,
                name = "History"
            ),
            Genre(
                id = 11,
                name = "Horror"
            ),
            Genre(
                id = 11,
                name = "Music"
            ),
            Genre(
                id = 11,
                name = "Romance"
            ),
            Genre(
                id = 11,
                name = "Mystery"
            ),
        )
    }
}