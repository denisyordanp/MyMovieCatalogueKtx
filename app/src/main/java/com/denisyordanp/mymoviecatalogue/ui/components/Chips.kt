package com.denisyordanp.mymoviecatalogue.ui.components

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
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme

@Composable
fun Chips(list: List<ChipItem>) {
    LazyRow(
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(list) {
            Chip(item = it)
        }
    }
}

@Composable
private fun Chip(item: ChipItem) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colors.primary,
        elevation = 2.dp
    ) {
        Text(
            modifier = Modifier
                .padding(
                    vertical = 8.dp,
                    horizontal = 12.dp
                ),
            text = item.name,
            fontWeight = FontWeight.Bold
        )
    }
}

interface ChipItem {
    val name: String
}

@Preview
@Composable
private fun ChipsPreview() {
    MyMovieCatalogueTheme {
        Chips(list = Dummy.genres)
    }
}