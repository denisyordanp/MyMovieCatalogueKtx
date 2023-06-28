package com.denisyordanp.mymoviecatalogue.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.denisyordanp.mymoviecatalogue.R
import com.denisyordanp.mymoviecatalogue.schemas.ui.Dummy
import com.denisyordanp.mymoviecatalogue.schemas.ui.Video
import com.denisyordanp.mymoviecatalogue.ui.theme.MyMovieCatalogueTheme

@Composable
fun VideoItem(
    video: Video,
    onVideoClicked: (video: Video) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .clickable { onVideoClicked(video) }
            .padding(bottom = 3.dp),
        elevation = 1.dp,
        shape = RoundedCornerShape(16.dp)
    ) {
        ConstraintLayout {
            val (thumbnail, title, published) = createRefs()
            AsyncImage(
                modifier = Modifier
                    .constrainAs(thumbnail) {
                        linkTo(top = parent.top, bottom = parent.bottom)
                        start.linkTo(parent.start)
                        height = Dimension.fillToConstraints
                    }
                    .width(100.dp),
                model = video.thumbnailPath,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.baseline_image_24),
                error = painterResource(id = R.drawable.baseline_broken_image_24),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .constrainAs(title) {
                        linkTo(
                            start = thumbnail.end,
                            end = parent.end,
                            startMargin = 8.dp,
                            endMargin = 16.dp
                        )
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
                        end.linkTo(parent.end, margin = 16.dp)
                        linkTo(
                            top = title.bottom,
                            topMargin = 8.dp,
                            bottom = parent.bottom,
                            bottomMargin = 16.dp,
                            bias = 1f
                        )
                    },
                text = video.publishedAt,
                style = MaterialTheme.typography.overline
            )
        }
    }
}

@Composable
@Preview
private fun Preview() {
    MyMovieCatalogueTheme {
        VideoItem(
            video = Dummy.getVideos()[1],
            onVideoClicked = {}
        )
    }
}