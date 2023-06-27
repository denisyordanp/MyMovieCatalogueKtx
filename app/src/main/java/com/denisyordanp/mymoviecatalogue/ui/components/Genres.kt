package com.denisyordanp.mymoviecatalogue.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.schemas.ui.Genre
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme

@Composable
fun Genres(
    list: List<Genre>,
    isAllSelected: Boolean = false,
    selectedGenre: Genre? = null,
    onItemClicked: ((genre: Genre) -> Unit)? = null
) {
    LazyRow(
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(list) {
            Chip(
                text = it.name,
                isSelected = it == selectedGenre || isAllSelected,
                onClicked = {
                    onItemClicked?.invoke(it)
                }
            )
        }
    }
}

@Composable
private fun Chip(
    text: String,
    isSelected: Boolean,
    onClicked: () -> Unit
) {
    val color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.secondary
    Surface(
        modifier = Modifier
            .clickable {
                onClicked()
            },
        shape = RoundedCornerShape(16.dp),
        color = color,
        elevation = 2.dp
    ) {
        Text(
            modifier = Modifier
                .padding(
                    vertical = 8.dp,
                    horizontal = 12.dp
                ),
            text = text,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MyMovieCatalogueTheme {
        Genres(
            list = Dummy.genres,
            selectedGenre = Dummy.genres[3],
            onItemClicked = {}
        )
    }
}
