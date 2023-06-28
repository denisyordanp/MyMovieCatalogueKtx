package com.denisyordanp.mymoviecatalogue.ui

import androidx.navigation.NavType
import androidx.navigation.navArgument

object MainDestinations {
    const val MOVIE_ID = "movie_id"

    const val MAIN_SCREEN = "main_screen"
    const val DETAIL_SCREEN = "detail_screen/{$MOVIE_ID}"

    fun detailScreen(movieId: Long) = "detail_screen/$movieId"

    val detailScreenArguments = listOf(
        navArgument(name = MOVIE_ID) {
            type = NavType.LongType
        }
    )
}