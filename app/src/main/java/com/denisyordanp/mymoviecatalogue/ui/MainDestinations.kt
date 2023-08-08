package com.denisyordanp.mymoviecatalogue.ui

import androidx.navigation.NavType
import androidx.navigation.navArgument

object MainDestinations {
    const val MOVIE_ID = "movie_id"

    const val MAIN_SCREEN = "main_screen"
    const val FAVORITE_SCREEN = "favorite_screen"
    const val DETAIL_SCREEN_PATH = "detail_screen"
    const val DETAIL_SCREEN = "$DETAIL_SCREEN_PATH/{$MOVIE_ID}"

    fun detailScreen(movieId: Long) = "$DETAIL_SCREEN_PATH/$movieId"

    val detailScreenArguments = listOf(
        navArgument(name = MOVIE_ID) {
            type = NavType.LongType
        }
    )
}
