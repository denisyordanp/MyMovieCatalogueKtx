package com.denisyordanp.mymoviecatalogue.ui.screens.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.ui.components.ErrorContent
import com.denisyordanp.mymoviecatalogue.ui.components.TopBar
import com.denisyordanp.mymoviecatalogue.ui.screens.detail.components.Body
import com.denisyordanp.mymoviecatalogue.ui.screens.detail.components.Footer
import com.denisyordanp.mymoviecatalogue.ui.screens.detail.components.Header
import com.denisyordanp.mymoviecatalogue.ui.screens.detail.components.ReviewBottomSheet
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreen(
    movieId: Long,
    viewModel: DetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    LaunchedEffect(key1 = movieId) {
        viewModel.loadMovieDetail(movieId = movieId, isForce = false)
    }
    val scope = rememberCoroutineScope()
    val state = viewModel.viewState.collectAsState()
    val sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetState = sheetState,
        sheetContent = {
            ReviewBottomSheet(reviews = state.value.reviewsViewState.reviews)
        },
        content = {
            DetailScreenContent(
                state = state.value,
                onRefresh = {
                    viewModel.loadMovieDetail(movieId = movieId, isForce = true)
                },
                onRetryError = {
                    viewModel.loadMovieDetail(movieId = movieId, isForce = true)
                },
                onBackPressed = onBackPressed,
                onMoreReviewsClicked = {
                    scope.launch {
                        sheetState.show()
                    }
                },
                onVideosRetry = {
                    viewModel.loadVideos(movieId = movieId, isForce = true)
                },
                onReviewRetry = {
                    viewModel.loadReviews(movieId = movieId, isForce = true)
                }
            )
        }
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
    Scaffold(
        topBar = {
            TopBar(
                title = state.movieDetail?.title ?: "",
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
                val refreshState = rememberSwipeRefreshState(isRefreshing = state.getIsLoading())
                SwipeRefresh(
                    state = refreshState,
                    onRefresh = onRefresh
                ) {
                    if (state.error != null) {
                        ErrorContent(
                            modifier = Modifier.fillMaxSize(),
                            error = state.error,
                            onRetryError = onRetryError
                        )
                    } else {
                        state.movieDetail?.let { detail ->
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
        }
    )
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
