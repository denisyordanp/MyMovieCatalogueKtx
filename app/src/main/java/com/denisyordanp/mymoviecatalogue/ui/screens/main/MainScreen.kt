package com.denisyordanp.mymoviecatalogue.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.denisyordanp.mymoviecatalogue.R
import com.denisyordanp.mymoviecatalogue.ui.components.GeneralError
import com.denisyordanp.mymoviecatalogue.ui.components.Genres
import com.denisyordanp.mymoviecatalogue.ui.components.MovieItem
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.loadGenres(false)
    }
    val viewState = viewModel.viewState.collectAsState().value

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        val refreshState = rememberSwipeRefreshState(isRefreshing = viewState.isLoading)
        SwipeRefresh(
            state = refreshState,
            onRefresh = {
                viewModel.loadGenres(true)
            }
        ) {
            if (viewState.genreViewState.error != null) {
                ErrorContent(
                    error = viewState.genreViewState.error,
                    onRetryError = {
                        viewModel.loadGenres(isForce = true)
                    }
                )
            } else {
                Column {
                    // Genres
                    Genres(
                        list = viewState.genreViewState.genres,
                        selectedGenre = viewState.selectedGenre,
                        onItemClicked = {
                            viewModel.selectGenre(it)
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Movies
                    Movies(
                        state = viewState.movieViewState,
                        onRetryError = {
                            viewModel.loadMovies(isForce = true)
                        }
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
private fun ErrorContent(
    error: Throwable,
    onRetryError: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        GeneralError(
            title = stringResource(R.string.something_wrong),
            desc = stringResource(R.string.please_try_again_later),
            error = error,
            onRetry = onRetryError
        )
    }
}