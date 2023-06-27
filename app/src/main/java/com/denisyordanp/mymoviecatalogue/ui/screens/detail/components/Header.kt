package com.denisyordanp.mymoviecatalogue.ui.screens.detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.denisyordanp.mymoviecatalogue.R
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.schemas.ui.MovieDetail
import com.denisyordanp.mymoviecatalogue.ui.components.Genres
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme

@Composable
fun Header(detail: MovieDetail) {
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
@Preview(showSystemUi = true)
private fun Preview() {
    MyMovieCatalogueTheme {
        Header(
            detail = Dummy.getMovieDetail(),
        )
    }
}
