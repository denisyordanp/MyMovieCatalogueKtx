package com.denisyordanp.mymoviecatalogue.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.denisyordanp.mymoviecatalogue.schemas.ui.Review

@Composable
fun ReviewItem(review: Review) {
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
            val (icon, name, content, date) = createRefs()
            Icon(
                modifier = Modifier
                    .size(28.dp)
                    .constrainAs(icon) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    },
                imageVector = Icons.Filled.Person,
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .constrainAs(name) {
                        linkTo(start = icon.end, end = parent.end, startMargin = 8.dp)
                        linkTo(top = icon.top, bottom = icon.bottom)
                        width = Dimension.fillToConstraints
                    },
                text = review.author,
                style = MaterialTheme.typography.subtitle1
            )
            Text(
                modifier = Modifier
                    .constrainAs(content) {
                        linkTo(start = parent.start, end = parent.end, startMargin = 12.dp)
                        top.linkTo(icon.bottom, margin = 8.dp)
                        width = Dimension.fillToConstraints
                    },
                text = review.content,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.caption
            )
            Text(
                modifier = Modifier
                    .constrainAs(date) {
                        end.linkTo(parent.end)
                        top.linkTo(content.bottom, margin = 8.dp)
                    },
                text = review.createdAt,
                style = MaterialTheme.typography.overline
            )
        }
    }
}
