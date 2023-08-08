package com.denisyordanp.mymoviecatalogue.tools

import androidx.navigation.NavBackStackEntry
import com.denisyordanp.mymoviecatalogue.ui.MainDestinations

val NavBackStackEntry.currentActiveRoute: String?
    get() {
        val route = this.destination.route ?: return null
        return when  {
            route == MainDestinations.MAIN_SCREEN -> MainDestinations.MAIN_SCREEN
            route == MainDestinations.FAVORITE_SCREEN -> MainDestinations.FAVORITE_SCREEN
            route.contains(MainDestinations.DETAIL_SCREEN_PATH) -> MainDestinations.DETAIL_SCREEN
            else -> throw IllegalStateException("Route doesn't exist: $route")
        }
    }
