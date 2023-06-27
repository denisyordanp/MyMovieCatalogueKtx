package com.denisyordanp.mymoviecatalogue.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme

@Composable
fun GeneralError(
    title: String,
    desc: String,
    error: Throwable? = null,
    onRetry: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = desc,
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(4.dp))
        error?.message?.let {
            Text(
                text = "message: $it",
                style = MaterialTheme.typography.overline
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onRetry
        ) {
            Text(text = "Retry")
        }
    }
}

@Preview
@Composable
private fun Preview() {
    MyMovieCatalogueTheme {
        GeneralError(
            title = "Error Server",
            desc = "Please retry again",
            error = Exception("Example"),
            onRetry = {}
        )
    }
}