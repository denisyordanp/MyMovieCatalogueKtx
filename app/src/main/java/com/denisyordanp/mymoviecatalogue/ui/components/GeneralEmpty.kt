package com.denisyordanp.mymoviecatalogue.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.denisyordanp.mymoviecatalogue.R
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme

@Composable
fun EmptyContent(
    modifier: Modifier = Modifier,
) {
    val title = R.string.favorite_empty_title
    val message = R.string.favorite_empty_desc

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        GeneralEmpty(
            title = stringResource(title),
            desc = stringResource(message),
        )
    }
}

@Composable
private fun GeneralEmpty(
    title: String,
    desc: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = desc,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Preview
@Composable
private fun Preview() {
    MyMovieCatalogueTheme {
        GeneralEmpty(
            title = "Empty",
            desc = "No data yet",
        )
    }
}
