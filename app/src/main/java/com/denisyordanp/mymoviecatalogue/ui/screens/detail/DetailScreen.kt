package com.denisyordanp.mymoviecatalogue.ui.screens.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.denisyordanp.mymoviecatalogue.R
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.schemas.ui.MovieDetail
import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import com.denisyordanp.mymoviecatalogue.ui.components.ErrorContent
import com.denisyordanp.mymoviecatalogue.ui.components.Genres
import com.denisyordanp.mymoviecatalogue.ui.components.RateText
import com.denisyordanp.mymoviecatalogue.ui.components.ReviewItem
import com.denisyordanp.mymoviecatalogue.ui.components.TopBar
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
                                    onMoreClicked = onMoreReviewsClicked
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
private fun Header(detail: MovieDetail) {
    Column {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 300.dp),
            model = detail.backdropPath,
            contentDescription = null,
            placeholder = painterResource(id = R.drawable.baseline_image_24),
            error = painterResource(id = R.drawable.baseline_broken_image_24)
        )
        Spacer(modifier = Modifier.heightIn(16.dp))
        Genres(list = detail.genres, isAllSelected = true)
    }
}

@Composable
private fun Body(detail: MovieDetail) {
    ConstraintLayout(
        modifier = Modifier.padding(16.dp)
    ) {
        val (tagline, overView, budget, revenue, rate, release) = createRefs()
        Text(
            modifier = Modifier
                .constrainAs(tagline) {
                    linkTo(start = parent.start, end = rate.start)
                    top.linkTo(parent.top)
                    width = Dimension.fillToConstraints
                },
            text = detail.tagline,
            style = MaterialTheme.typography.h6,
        )
        RateText(
            modifier = Modifier
                .constrainAs(rate) {
                    top.linkTo(parent.top)
                    linkTo(start = tagline.end, end = parent.end)
                },
            rate = detail.voteAverage.toString(),
            from = detail.voteCount.toString()
        )
        Text(
            modifier = Modifier
                .constrainAs(release) {
                    top.linkTo(tagline.bottom, margin = 12.dp)
                    start.linkTo(parent.start)
                },
            text = "Released ${detail.releaseDate}",
            style = MaterialTheme.typography.body1.copy(
                fontStyle = FontStyle.Italic
            ),
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier
                .constrainAs(overView) {
                    linkTo(start = parent.start, end = parent.end)
                    top.linkTo(release.bottom, margin = 4.dp)
                },
            text = detail.overview,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Justify
        )
        Text(
            modifier = Modifier
                .constrainAs(budget) {
                    linkTo(start = parent.start, end = revenue.start)
                    top.linkTo(overView.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                },
            text = "Budget ${detail.budget}",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .constrainAs(revenue) {
                    linkTo(start = budget.end, end = parent.end)
                    top.linkTo(overView.bottom, margin = 8.dp)
                    width = Dimension.fillToConstraints
                },
            text = "Revenue ${detail.revenue}",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun Footer(
    reviews: List<Review>,
    onMoreClicked: () -> Unit
) {
    if (reviews.isNotEmpty()) {
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
                    modifier = Modifier.clickable { onMoreClicked() },
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
}

@Composable
@Preview(showSystemUi = true)
private fun Preview() {
    MyMovieCatalogueTheme {
        DetailScreenContent(
            detail = Dummy.movieDetail,
            reviews = Dummy.reviews,
            onRefresh = {},
            onRetryError = {},
            onBackPressed = {},
            onMoreReviewsClicked = {}
        )
    }
}
