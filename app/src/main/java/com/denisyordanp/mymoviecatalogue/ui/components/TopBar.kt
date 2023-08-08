package com.denisyordanp.mymoviecatalogue.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    title: String,
    isFavorite: Boolean?,
    onBackPressed: () -> Unit,
    onFavorite: () -> Unit
) {
    var currentFavorite by remember { mutableStateOf(isFavorite) }
    LaunchedEffect(key1 = isFavorite) {
        currentFavorite = isFavorite
    }

    Surface(
        modifier = modifier
            .fillMaxWidth(),
        elevation = 4.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .padding(4.dp),
                onClick = onBackPressed
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = title,
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (currentFavorite != null) {
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(
                    modifier = Modifier
                        .padding(4.dp),
                    onClick = {
                        currentFavorite = !currentFavorite!!
                        onFavorite()
                    }
                ) {
                    Icon(
                        imageVector = if (currentFavorite!!) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
        }
    }
}
