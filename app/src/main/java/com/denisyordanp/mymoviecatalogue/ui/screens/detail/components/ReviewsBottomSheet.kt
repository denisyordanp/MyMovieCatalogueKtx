package com.denisyordanp.mymoviecatalogue.ui.screens.detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.denisyordanp.mymoviecatalogue.R
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import com.denisyordanp.mymoviecatalogue.ui.components.ErrorContent
import com.denisyordanp.mymoviecatalogue.ui.components.ReviewItem
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme
import kotlinx.coroutines.flow.Flow

@Composable
fun ReviewBottomSheet(reviews: Flow<PagingData<Review>>) {
    val reviewPaging = reviews.collectAsLazyPagingItems()
    LazyColumn {
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_menu_24),
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(reviewPaging.itemCount) {
            reviewPaging[it]?.let { review ->
                ReviewItem(review = review, isShortedContent = false)
            }
        }

        val loadState = reviewPaging.loadState.mediator
        item {
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
                val error = if (loadState.append is LoadState.Error)
                    (loadState.append as LoadState.Error).error
                else
                    (loadState.refresh as LoadState.Error).error

                ErrorContent(
                    modifier = Modifier.fillMaxWidth(),
                    error = error,
                    onRetryError = {
                        reviewPaging.refresh()
                    }
                )
            }
        }
    }
}

@Composable
@Preview
private fun PReview() {
    MyMovieCatalogueTheme {
        ReviewBottomSheet(reviews = Dummy.getReviews())
    }
}