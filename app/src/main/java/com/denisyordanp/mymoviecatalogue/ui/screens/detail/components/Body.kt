package com.denisyordanp.mymoviecatalogue.ui.screens.detail.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.schemas.ui.MovieDetail
import com.denisyordanp.mymoviecatalogue.ui.components.RateText
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme

@Composable
fun Body(detail: MovieDetail) {
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
                    linkTo(start = tagline.end, end = parent.end, startMargin = 8.dp)
                },
            rate = detail.voteAverage,
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
@Preview(showSystemUi = true)
private fun Preview() {
    MyMovieCatalogueTheme {
        Body(
            detail = Dummy.getMovieDetail(),
        )
    }
}