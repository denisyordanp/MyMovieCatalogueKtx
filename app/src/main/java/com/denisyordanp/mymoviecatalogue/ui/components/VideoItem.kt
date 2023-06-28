package com.denisyordanp.mymoviecatalogue.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.denisyordanp.mymoviecatalogue.R
import com.denisyordanp.mymoviecatalogue.schemas.ui.Video

@Composable
fun VideoItem(video: Video) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 3.dp),
        elevation = 1.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.padding(12.dp)
        ) {
            val (thumbnail, title, published) = createRefs()
            AsyncImage(
                modifier = Modifier
                    .height(80.dp)
                    .constrainAs(thumbnail) {
                        linkTo(top = parent.top, bottom = parent.bottom)
                        start.linkTo(parent.start)
                    },
                // TODO: Replace youtube thumbnail in use case
                model = video.key,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.baseline_image_24),
                error = painterResource(id = R.drawable.baseline_broken_image_24)
            )
            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        linkTo(start = thumbnail.end, end = parent.end, startMargin = 8.dp)
                        top.linkTo(parent.top, margin = 16.dp)
                        width = Dimension.fillToConstraints
                    },
                text = video.name,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .constrainAs(published) {
                        end.linkTo(parent.end)
                        linkTo(
                            top = title.bottom,
                            topMargin = 8.dp,
                            bottom = parent.bottom,
                            bottomMargin = 8.dp,
                            bias = 1f
                        )
                    },
                text = video.publishedAt,
                style = MaterialTheme.typography.overline
            )
        }
    }
}