package com.denisyordanp.mymoviecatalogue.ui.screens.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.ui.components.ErrorContent
import com.denisyordanp.mymoviecatalogue.ui.components.TopBar
import com.denisyordanp.mymoviecatalogue.ui.screens.detail.components.Body
import com.denisyordanp.mymoviecatalogue.ui.screens.detail.components.Footer
import com.denisyordanp.mymoviecatalogue.ui.screens.detail.components.Header
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun DetailScreen(
    movieId: Int,
    viewModel: DetailViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = movieId) {
        viewModel.loadMovieDetail(movieId)
    }
    val state = viewModel.viewState.collectAsState()

    DetailScreenContent(
        state = state.value,
        onRefresh = { },
        onRetryError = { },
        onBackPressed = { },
        onMoreReviewsClicked = {},
        onVideosRetry = {},
        onReviewRetry = {}
    )
}

@Composable
private fun DetailScreenContent(
    state: DetailViewState,
    onRefresh: () -> Unit,
    onRetryError: () -> Unit,
    onBackPressed: () -> Unit,
    onMoreReviewsClicked: () -> Unit,
    onVideosRetry: () -> Unit,
    onReviewRetry: () -> Unit,
) {
    state.movieDetail?.let { detail ->
        Scaffold(
            topBar = {
                TopBar(
                    title = detail.title,
                    onBackPressed = onBackPressed
                )
            },
            content = { padding ->
                Surface(
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val refreshState = rememberSwipeRefreshState(isRefreshing = false)
                    SwipeRefresh(
                        state = refreshState,
                        onRefresh = onRefresh
                    ) {
                        if (state.error != null) {
                            ErrorContent(
                                modifier = Modifier.fillMaxWidth(),
                                error = state.error,
                                onRetryError = onRetryError
                            )
                        } else {
                            LazyColumn {
                                item {
                                    Header(detail)
                                }
                                item {
                                    Body(detail)
                                }
                                item {
                                    Footer(
                                        reviewsViewState = state.reviewsViewState,
                                        videosViewState = state.videosViewState,
                                        onMoreReviewClicked = onMoreReviewsClicked,
                                        onVideosRetry = onVideosRetry,
                                        onReviewRetry = onReviewRetry
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@Composable
@Preview(showSystemUi = true)
private fun Preview() {
    MyMovieCatalogueTheme {
        DetailScreenContent(
            state = Dummy.getDetailViewState(),
            onRefresh = {},
            onRetryError = {},
            onBackPressed = {},
            onMoreReviewsClicked = {},
            onVideosRetry = {},
            onReviewRetry = {}
        )
    }
}
