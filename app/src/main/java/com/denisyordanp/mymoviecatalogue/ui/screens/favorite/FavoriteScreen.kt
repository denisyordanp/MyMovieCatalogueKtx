package com.denisyordanp.mymoviecatalogue.ui.screens.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.schemas.ui.Movie
import com.denisyordanp.mymoviecatalogue.ui.components.MovieItem
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme

@Composable
fun FavoriteScreen(
    onMovieClicked: (movie: Movie) -> Unit
) {

    FavoriteScreenContent(
        movies = Dummy.getMovies(),
        onMovieClicked = onMovieClicked
    )
}

@Composable
private fun FavoriteScreenContent(
    movies: List<Movie>,
    onMovieClicked: (movie: Movie) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(movies) {
                MovieItem(movie = it, onClickItem = onMovieClicked)
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
private fun Preview() {
    MyMovieCatalogueTheme {
        FavoriteScreenContent(
            movies = Dummy.getMovies(),
            onMovieClicked = {}
        )
    }
}
