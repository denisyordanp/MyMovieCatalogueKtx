package com.denisyordanp.mymoviecatalogue.ui.screens.detail.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.denisyordanp.mymoviecatalogue.R
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.schemas.ui.Review
import com.denisyordanp.mymoviecatalogue.ui.components.ReviewItem
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme

@Composable
fun ReviewBottomSheet(reviews: List<Review>) {
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
        items(reviews) {
            ReviewItem(review = it, isShortedContent = false)
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