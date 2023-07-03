package com.denisyordanp.mymoviecatalogue.ui.screens.detail.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import com.denisyordanp.mymoviecatalogue.schemas.ui.Video
import com.denisyordanp.mymoviecatalogue.ui.components.ErrorContent
import com.denisyordanp.mymoviecatalogue.ui.components.ReviewItem
import com.denisyordanp.mymoviecatalogue.ui.components.VideoItem
import com.denisyordanp.mymoviecatalogue.ui.screens.detail.ReviewsViewState
import com.denisyordanp.mymoviecatalogue.ui.screens.detail.VideosViewState
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme


@Composable
fun Footer(
    reviewsViewState: ReviewsViewState,
    videosViewState: VideosViewState,
    onMoreReviewClicked: () -> Unit,
    onVideosRetry: () -> Unit,
    onReviewRetry: () -> Unit,
    onVideoClicked: (video: Video) -> Unit
) {
    Column {
        Videos(
            videosViewState = videosViewState,
            onRetryError = onVideosRetry,
            onVideoClicked = onVideoClicked
        )
        Reviews(
            reviewsViewState = reviewsViewState,
            onMoreReviewClicked = onMoreReviewClicked,
            onRetryError = onReviewRetry
        )
    }
}

@Composable
private fun Reviews(
    reviewsViewState: ReviewsViewState,
    onMoreReviewClicked: () -> Unit,
    onRetryError: () -> Unit
) {
    val reviewsState = reviewsViewState.reviews.collectAsState(initial = PagingData.empty())
    if (reviewsViewState.error != null || reviewsState.value != PagingData.empty<List<Review>>()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Reviews",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            ReviewContent(
                reviewsViewState = reviewsViewState,
                onMoreReviewClicked = onMoreReviewClicked,
                onRetryError = onRetryError
            )
        }
    }
}

@Composable
private fun ReviewContent(
    reviewsViewState: ReviewsViewState,
    onMoreReviewClicked: () -> Unit,
    onRetryError: () -> Unit
) {
    if (reviewsViewState.error != null) {
        ErrorContent(
            modifier = Modifier.fillMaxWidth(),
            error = reviewsViewState.error,
            onRetryError = onRetryError
        )
    } else {
        val state = reviewsViewState.reviews.collectAsLazyPagingItems()

        if (state.itemCount >= 3) {
            repeat(3) {
                state[it]?.let { review ->
                    ReviewItem(review = review, isShortedContent = true)
                }
            }
        } else {
            repeat(state.itemCount) {
                state[it]?.let { review ->
                    ReviewItem(review = review, isShortedContent = true)
                }
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.BottomEnd
        ) {
            Row(
                modifier = Modifier.clickable { onMoreReviewClicked() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "More detail",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Filled.PlayArrow,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
private fun Videos(
    videosViewState: VideosViewState,
    onRetryError: () -> Unit,
    onVideoClicked: (video: Video) -> Unit
) {
    if (videosViewState.error != null || videosViewState.videos.isNotEmpty()) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = "Videos",
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (videosViewState.error != null) {
                ErrorContent(
                    modifier = Modifier.fillMaxWidth(),
                    error = videosViewState.error,
                    onRetryError = onRetryError
                )
            } else {
                videosViewState.videos.forEach {
                    VideoItem(
                        video = it,
                        onVideoClicked = onVideoClicked
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun Preview() {
    MyMovieCatalogueTheme {
        Footer(
            reviewsViewState = Dummy.getReviewsViewState(),
            videosViewState = Dummy.getVideoViewState(),
            onMoreReviewClicked = {},
            onVideosRetry = {},
            onReviewRetry = {},
            onVideoClicked = {}
        )
    }
}