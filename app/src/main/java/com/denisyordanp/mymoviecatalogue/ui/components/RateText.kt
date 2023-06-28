package com.denisyordanp.mymoviecatalogue.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme

@Composable
fun RateText(
    modifier: Modifier = Modifier,
    rate: String,
    from: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = rate,
            style = MaterialTheme.typography.subtitle1,
        )
        Spacer(modifier = Modifier.width(2.dp))
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = null,
            tint = Color.Yellow
        )
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = "($from)",
            style = MaterialTheme.typography.caption,
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MyMovieCatalogueTheme {
        RateText(
            rate = "4.5",
            from = "1000"
        )
    }
}