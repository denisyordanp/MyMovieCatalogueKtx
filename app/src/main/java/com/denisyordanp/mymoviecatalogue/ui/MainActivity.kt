package com.denisyordanp.mymoviecatalogue.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.denisyordanp.mymoviecatalogue.ui.screens.detail.DetailScreen
import com.denisyordanp.mymoviecatalogue.ui.screens.main.MainScreen
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMovieCatalogueTheme {
                val mainNavController = rememberNavController()

                NavHost(
                    navController = mainNavController,
                    startDestination = MainDestinations.MAIN_SCREEN
                ) {
                    composable(route = MainDestinations.MAIN_SCREEN) {
                        MainScreen(
                            onMovieClicked = {
                                mainNavController.navigate(MainDestinations.detailScreen(it.id))
                            }
                        )
                    }

                    composable(
                        route = MainDestinations.DETAIL_SCREEN,
                        arguments = MainDestinations.detailScreenArguments
                    ) { backStack ->
                        backStack.arguments?.getLong(MainDestinations.MOVIE_ID)?.let { movieId ->
                            DetailScreen(
                                movieId = movieId,
                                onBackPressed = {
                                    mainNavController.navigateUp()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}