package com.denisyordanp.mymoviecatalogue.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import com.denisyordanp.mymoviecatalogue.ui.components.ErrorContent
import com.denisyordanp.mymoviecatalogue.ui.components.Genres
import com.denisyordanp.mymoviecatalogue.ui.components.MovieItem
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.loadGenres(false)
    }
    val viewState = viewModel.viewState.collectAsState()

    MainScreenContent(
        state = viewState.value,
        onRefresh = {
            viewModel.loadGenres(true)
        },
        onGenresRetryError = {
            viewModel.loadGenres(isForce = true)
        },
        onMoviesRetryError = {
            viewModel.loadMovies(isForce = true)
        },
        onGenreClicked = {
            viewModel.selectGenre(it)
        }
    )
}

@Composable
private fun MainScreenContent(
    state: MainViewState,
    onRefresh: () -> Unit,
    onGenresRetryError: () -> Unit,
    onMoviesRetryError: () -> Unit,
    onGenreClicked: (genre: Genre) -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val refreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)
        SwipeRefresh(
            state = refreshState,
            onRefresh = onRefresh
        ) {
            if (state.genreViewState.error != null) {
                ErrorContent(
                    modifier = Modifier.fillMaxWidth(),
                    error = state.genreViewState.error,
                    onRetryError = onGenresRetryError
                )
            } else {
                Column {
                    // Genres
                    Genres(
                        list = state.genreViewState.genres,
                        onItemClicked = onGenreClicked
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Movies
                    Movies(
                        state = state.movieViewState,
                        onRetryError = onMoviesRetryError
                    )
                }
            }
        }
    }
}

@Composable
private fun Movies(
    state: MovieViewState,
    onRetryError: () -> Unit
) {
    if (state.error != null) {
        ErrorContent(
            modifier = Modifier.fillMaxWidth(),
            error = state.error,
            onRetryError = onRetryError
        )
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(state.movies) { movie ->
                MovieItem(movie = movie)
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun Preview() {
    MyMovieCatalogueTheme {
        MainScreenContent(
            state = MainViewState.idle().copy(
                genreViewState = GenreViewState.idle().copy(
                    genres = Dummy.getGenres()
                ),
                movieViewState = MovieViewState.idle().copy(
                    movies = Dummy.getMovies()
                ),
                selectedGenre = Dummy.getGenres()[2]
            ),
            onRefresh = { },
            onGenresRetryError = { },
            onMoviesRetryError = { },
            onGenreClicked = { }
        )
    }
}
