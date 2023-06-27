package com.denisyordanp.mymoviecatalogue.ui.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.denisyordanp.mymoviecatalogue.R
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie
import com.denisyordanp.mymoviecatalogue.tools.DateFormat
import com.denisyordanp.mymoviecatalogue.tools.convertFormat
import com.denisyordanp.mymoviecatalogue.ui.components.Chips
import com.denisyordanp.mymoviecatalogue.ui.components.RateText
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme

@Composable
fun MainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column {
            // Genres
            Chips(list = Dummy.genres)
            Spacer(modifier = Modifier.height(12.dp))

            // Movies
            Movies(list = Dummy.movies)
        }
    }
}

@Composable
private fun Movies(list: List<Movie>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(list) { movie ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 2.dp,
                shape = RoundedCornerShape(12.dp)
            ) {
                ConstraintLayout {
                    val (poster, title, release, overview, rate) = createRefs()

                    // TODO: Add base url image on use case
                    AsyncImage(
                        modifier = Modifier
                            .constrainAs(poster) {
                                linkTo(top = parent.top, bottom = parent.bottom)
                                start.linkTo(parent.start)
                            }
                            .size(width = 100.dp, height = 150.dp),
                        model = "https://image.tmdb.org/t/p/w300${movie.posterPath}",
                        contentDescription = null,
                        placeholder = painterResource(id = R.drawable.baseline_image_24)
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
                        // TODO: Convert on use case
                        text = movie.releaseDate.convertFormat(
                            DateFormat.DEFAULT_FORMAT,
                            DateFormat.DATE_MONTH_YEAR_FORMAT
                        ),
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
    }
}

@Preview
@Composable
private fun ChipsPreview() {
    MyMovieCatalogueTheme {
        MainScreen()
    }
}