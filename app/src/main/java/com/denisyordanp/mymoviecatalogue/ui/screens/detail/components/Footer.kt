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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import com.denisyordanp.mymoviecatalogue.schemas.ui.Video
import com.denisyordanp.mymoviecatalogue.ui.components.ErrorContent
import com.denisyordanp.mymoviecatalogue.ui.components.ReviewItem
import com.denisyordanp.mymoviecatalogue.ui.components.VideoItem
import com.denisyordanp.mymoviecatalogue.ui.screens.detail.VideosViewState
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme
import kotlinx.coroutines.flow.Flow


@Composable
fun Footer(
    reviews: Flow<PagingData<Review>>,
    videosViewState: VideosViewState,
    onMoreReviewClicked: () -> Unit,
    onVideosRetry: () -> Unit,
    onVideoClicked: (video: Video) -> Unit
) {
    Column {
        Videos(
            videosViewState = videosViewState,
            onRetryError = onVideosRetry,
            onVideoClicked = onVideoClicked
        )
        Reviews(
            reviews = reviews,
            onMoreReviewClicked = onMoreReviewClicked,
        )
    }
}

@Composable
private fun Reviews(
    reviews: Flow<PagingData<Review>>,
    onMoreReviewClicked: () -> Unit,
) {
    val reviewsPaging = reviews.collectAsLazyPagingItems()
    val loadState = reviewsPaging.loadState.mediator
    val isError = loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error
    val error = if (loadState?.append is LoadState.Error)
        (loadState.append as? LoadState.Error)?.error
    else
        (loadState?.refresh as? LoadState.Error)?.error

    if (isError || reviewsPaging.itemCount >= 1) {
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
                reviewsPaging = reviewsPaging,
                error = error,
                onMoreReviewClicked = onMoreReviewClicked,
            )
        }
    }
}

@Composable
private fun ReviewContent(
    reviewsPaging: LazyPagingItems<Review>,
    error: Throwable?,
    onMoreReviewClicked: () -> Unit,
) {
    if (error != null) {
        ErrorContent(
            modifier = Modifier.fillMaxWidth(),
            error = error,
            onRetryError = {
                reviewsPaging.refresh()
            }
        )
    } else {
        if (reviewsPaging.itemCount >= 3) {
            repeat(3) {
                reviewsPaging[it]?.let { review ->
                    ReviewItem(review = review, isShortedContent = true)
                }
            }
        } else {
            repeat(reviewsPaging.itemCount) {
                reviewsPaging[it]?.let { review ->
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
            reviews = Dummy.getReviews(),
            videosViewState = Dummy.getVideoViewState(),
            onMoreReviewClicked = {},
            onVideosRetry = {},
            onVideoClicked = {}
        )
    }
}