package com.denisyordanp.mymoviecatalogue.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.denisyordanp.mymoviecatalogue.ui.components.GeneralBottomBar
import com.denisyordanp.mymoviecatalogue.ui.screens.detail.DetailScreen
import com.denisyordanp.mymoviecatalogue.ui.screens.favorite.FavoriteScreen
import com.denisyordanp.mymoviecatalogue.ui.screens.main.MainScreen
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            MyMovieCatalogueTheme {
                SetupSystemUI()
                val mainNavController = rememberNavController()

                Scaffold(
                    modifier = Modifier
                        .systemBarsPadding(),
                    bottomBar = {
                        GeneralBottomBar(mainNavController = mainNavController)
                    }
                ) { padding ->
                    NavHost(
                        modifier = Modifier.padding(padding),
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
                            route = MainDestinations.FAVORITE_SCREEN,
                        ) {
                            FavoriteScreen(
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
                                    },
                                    onVideoClicked = {
                                        handleYoutubeIntent(it.key)
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun SetupSystemUI() {
        val systemUiController = rememberSystemUiController()
        val statusBarColor = MaterialTheme.colors.background
        SideEffect {
            systemUiController.setStatusBarColor(
                color = statusBarColor,
                darkIcons = true,
            )
            systemUiController.setNavigationBarColor(
                color = Color.Black,
                darkIcons = false,
            )
        }
    }

    private fun handleYoutubeIntent(key: String) {
        val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key"))
        val webIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("http://www.youtube.com/watch?v=$key")
        )
        try {
            startActivity(appIntent)
        } catch (ex: ActivityNotFoundException) {
            startActivity(webIntent)
        }
    }
}
