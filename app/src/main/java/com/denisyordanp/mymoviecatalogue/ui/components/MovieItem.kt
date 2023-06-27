package com.denisyordanp.mymoviecatalogue.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.denisyordanp.mymoviecatalogue.R
import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie

@Composable
fun MovieItem(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = 2.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        ConstraintLayout {
            val (poster, title, release, overview, rate) = createRefs()
            AsyncImage(
                modifier = Modifier
                    .constrainAs(poster) {
                        linkTo(top = parent.top, bottom = parent.bottom)
                        start.linkTo(parent.start)
                    }
                    .size(width = 100.dp, height = 150.dp),
                model = movie.posterPath,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.baseline_image_24),
                error = painterResource(id = R.drawable.baseline_broken_image_24)
            )
            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top, margin = 8.dp)
                        linkTo(
                            start = poster.end,
                            end = parent.end,
                            startMargin = 16.dp,
                            endMargin = 16.dp
                        )
                        width = Dimension.fillToConstraints
                    },
                text = movie.title,
                style = MaterialTheme.typography.h6,
            )
            Text(
                modifier = Modifier
                    .constrainAs(release) {
                        top.linkTo(title.bottom, margin = 4.dp)
                        linkTo(
                            start = poster.end,
                            end = parent.end,
                            startMargin = 16.dp,
                            endMargin = 16.dp
                        )
                        width = Dimension.fillToConstraints
                    },
                text = movie.releaseDate,
                style = MaterialTheme.typography.subtitle2.copy(
                    fontStyle = FontStyle.Italic
                ),
            )
            Text(
                modifier = Modifier
                    .constrainAs(overview) {
                        top.linkTo(release.bottom, margin = 4.dp)
                        linkTo(
                            start = poster.end,
                            end = parent.end,
                            startMargin = 16.dp,
                            endMargin = 16.dp
                        )
                        width = Dimension.fillToConstraints
                    },
                text = movie.overview,
                style = MaterialTheme.typography.subtitle2,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            RateText(
                modifier = Modifier
                    .constrainAs(rate) {
                        top.linkTo(overview.bottom, margin = 12.dp)
                        linkTo(
                            start = poster.end,
                            end = parent.end,
                            startMargin = 16.dp,
                            endMargin = 16.dp
                        )
                        width = Dimension.fillToConstraints
                    },
                rate = movie.voteAverage.toString(),
                from = movie.voteCount.toString()
            )
        }
    }
}