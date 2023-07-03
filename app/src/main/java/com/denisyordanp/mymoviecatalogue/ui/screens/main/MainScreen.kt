package com.denisyordanp.mymoviecatalogue.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie
import com.denisyordanp.mymoviecatalogue.ui.components.ErrorContent
import com.denisyordanp.mymoviecatalogue.ui.components.Genres
import com.denisyordanp.mymoviecatalogue.ui.components.MovieItem
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onMovieClicked: (movie: Movie) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.loadGenres(false)
    }
    val viewState = viewModel.viewState.collectAsState()

    MainScreenContent(
        viewModel = viewModel,
        state = viewState.value,
        onRefresh = {
            viewModel.loadGenres(true)
        },
        onGenresRetryError = {
            viewModel.loadGenres(isForce = true)
        },
        onMoviesRetryError = {
//            viewModel.loadMovies(isForce = true)
        },
        onGenreClicked = {
            viewModel.selectGenre(it)
        },
        onMovieClicked = onMovieClicked
    )
}

@Composable
private fun MainScreenContent(
    viewModel: MainViewModel,
    state: MainViewState,
    onRefresh: () -> Unit,
    onGenresRetryError: () -> Unit,
    onMoviesRetryError: () -> Unit,
    onGenreClicked: (genre: Genre) -> Unit,
    onMovieClicked: (movie: Movie) -> Unit
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
                    modifier = Modifier.fillMaxSize(),
                    error = state.genreViewState.error,
                    onRetryError = onGenresRetryError
                )
            } else {
                Column {
                    // Genres
                    Genres(
                        list = state.genreViewState.genres,
                        selectedGenre = state.selectedGenre,
                        onItemClicked = onGenreClicked
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Movies
                    Movies(
                        viewModel,
                        state = state.movieViewState,
                        onRetryError = onMoviesRetryError,
                        onClickItem = onMovieClicked
                    )
                }
            }
        }
    }
}

@Composable
private fun Movies(
    viewModel: MainViewModel,
    state: MovieViewState,
    onRetryError: () -> Unit,
    onClickItem: (movie: Movie) -> Unit
) {
    if (state.error != null) {
        ErrorContent(
            modifier = Modifier.fillMaxWidth(),
            error = state.error,
            onRetryError = onRetryError
        )
    } else {
        val moviePaging = viewModel.loadMovies().collectAsLazyPagingItems()
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(moviePaging.itemCount) {
                moviePaging[it]?.let { movie ->
                    MovieItem(movie = movie, onClickItem = onClickItem)
                }
            }
            val loadState = moviePaging.loadState.mediator
            item {
                if (loadState?.refresh == LoadState.Loading) {
                    Column(
                        modifier = Modifier
                            .fillParentMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = "Refresh Loading"
                        )

                        CircularProgressIndicator(color = MaterialTheme.colors.primary)
                    }
                }

                if (loadState?.append == LoadState.Loading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colors.primary)
                    }
                }

                if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                    val isPaginatingError =
                        (loadState.append is LoadState.Error) || moviePaging.itemCount > 1
                    val error = if (loadState.append is LoadState.Error)
                        (loadState.append as LoadState.Error).error
                    else
                        (loadState.refresh as LoadState.Error).error

                    val modifier = if (isPaginatingError) {
                        Modifier.padding(8.dp)
                    } else {
                        Modifier.fillParentMaxSize()
                    }
                    Column(
                        modifier = modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if (!isPaginatingError) {
                            Icon(
                                modifier = Modifier
                                    .size(64.dp),
                                imageVector = Icons.Rounded.Warning, contentDescription = null
                            )
                        }

                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = error.message ?: error.toString(),
                            textAlign = TextAlign.Center,
                        )

                        Button(
                            onClick = {
                                moviePaging.refresh()
                            },
                            content = {
                                Text(text = "Refresh")
                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = MaterialTheme.colors.primary,
                                contentColor = Color.White,
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun Preview() {
    MyMovieCatalogueTheme {
//        MainScreenContent(
//            state = MainViewState.idle().copy(
//                genreViewState = GenreViewState.idle().copy(
//                    genres = Dummy.getGenres()
//                ),
//                movieViewState = MovieViewState.idle().copy(
//                    movies = flowOf(PagingData.from(Dummy.getMovies()))
//                ),
//                selectedGenre = Dummy.getGenres()[2]
//            ),
//            onRefresh = { },
//            onGenresRetryError = { },
//            onMoviesRetryError = { },
//            onGenreClicked = { },
//            onMovieClicked = {}
//        )
    }
}
