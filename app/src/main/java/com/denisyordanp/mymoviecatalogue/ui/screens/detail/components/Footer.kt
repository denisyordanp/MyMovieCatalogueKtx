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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.denisyordanp.mymoviecatalogue.R
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import com.denisyordanp.mymoviecatalogue.schemas.ui.Video
import com.denisyordanp.mymoviecatalogue.ui.components.ReviewItem
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme


@Composable
fun Footer(
    reviews: List<Review>,
    videos: List<Video>,
    onMoreReviewClicked: () -> Unit
) {
    Column {
        if (videos.isNotEmpty()) {
            Videos(videos = videos)
        }
        if (reviews.isNotEmpty()) {
            Reviews(
                reviews = reviews,
                onMoreReviewClicked = onMoreReviewClicked
            )
        }
    }
}

@Composable
private fun Reviews(
    reviews: List<Review>,
    onMoreReviewClicked: () -> Unit
) {
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
        if (reviews.size >= 3) {
            repeat(3) {
                ReviewItem(review = reviews[it])
            }
        } else {
            reviews.forEach {
                ReviewItem(review = it)
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
    videos: List<Video>
) {
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
        videos.forEach {
            VideoItem(video = it)
        }
    }
}

@Composable
private fun VideoItem(video: Video) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 3.dp),
        elevation = 1.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(12.dp)
        ) {
            val (thumbnail, title, published) = createRefs()
            AsyncImage(
                modifier = Modifier
                    .height(80.dp)
                    .constrainAs(thumbnail) {
                        linkTo(top = parent.top, bottom = parent.bottom)
                        start.linkTo(parent.start)
                    },
                // TODO: Replace youtube thumbnail in use case
                model = video.key,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.baseline_image_24),
                error = painterResource(id = R.drawable.baseline_broken_image_24)
            )
            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        linkTo(start = thumbnail.end, end = parent.end, startMargin = 8.dp)
                        top.linkTo(parent.top, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    },
                text = video.name,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .constrainAs(published) {
                        end.linkTo(parent.end)
                        linkTo(
                            top = title.bottom,
                            topMargin = 8.dp,
                            bottom = parent.bottom,
                            bottomMargin = 8.dp,
                            bias = 1f
                        )
                    },
                text = video.publishedAt,
                style = MaterialTheme.typography.overline
            )
        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun Preview() {
    MyMovieCatalogueTheme {
        Footer(
            reviews = Dummy.getReviews(),
            videos = Dummy.getVideos(),
            onMoreReviewClicked = {}
        )
    }
}