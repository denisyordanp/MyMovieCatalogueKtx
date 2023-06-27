package com.denisyordanp.mymoviecatalogue.ui.screens.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.schemas.ui.MovieDetail
import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import com.denisyordanp.mymoviecatalogue.schemas.ui.Video
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
    movieId: Int = 0
) {

}

@Composable
private fun DetailScreenContent(
    detail: MovieDetail,
    reviews: List<Review>,
    videos: List<Video>,
    onRefresh: () -> Unit,
    onRetryError: () -> Unit,
    onBackPressed: () -> Unit,
    onMoreReviewsClicked: () -> Unit
) {
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
                    if (false) {
                        ErrorContent(
                            error = Throwable(),
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
                                    reviews = reviews,
                                    videos = videos,
                                    onMoreReviewClicked = onMoreReviewsClicked
                                )
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
            detail = Dummy.getMovieDetail(),
            reviews = Dummy.getReviews(),
            videos = Dummy.getVideos(),
            onRefresh = {},
            onRetryError = {},
            onBackPressed = {},
            onMoreReviewsClicked = {}
        )
    }
}
