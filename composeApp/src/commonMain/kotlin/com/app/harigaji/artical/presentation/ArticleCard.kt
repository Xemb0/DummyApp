package com.app.harigaji.artical.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.app.harigaji.artical.domain.ArticleDetail
import com.app.harigaji.theme.SpacerVerticalSmall
import com.app.harigaji.theme.debugUi
import com.app.harigaji.theme.rememberCornerRadiusMedium
import com.app.harigaji.theme.rememberCornerRadiusSmall
import com.app.harigaji.theme.rememberInnerHorizontalPaddingMedium
import com.app.harigaji.theme.rememberSizeBig

@Composable
fun ArticleCard(backgroundColor: Color, articleDetail: ArticleDetail, modifier: Modifier, onClick: (()-> Unit)? = null) {
    Card(
        onClick = {onClick?.invoke()},
        colors = CardDefaults.cardColors(
            MaterialTheme.colorScheme.surface
        ),
        shape = rememberCornerRadiusMedium(),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(rememberSizeBig())
                .padding(
                    horizontal = rememberInnerHorizontalPaddingMedium(),
                    vertical = rememberInnerHorizontalPaddingMedium()
                )
                .debugUi(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            // Image or placeholder
            articleDetail.imageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Article Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(rememberSizeBig())
                        .fillMaxHeight()
                        .clip(rememberCornerRadiusSmall())
                        .debugUi()
                )
            } ?: Box(
                modifier = Modifier
                    .width(rememberSizeBig())
                    .fillMaxHeight()
                    .clip(rememberCornerRadiusSmall())
                    .background(backgroundColor)
                    .debugUi()
            )

            // Content column
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(horizontal = rememberInnerHorizontalPaddingMedium())
                    .debugUi(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Title and Description
                Column(
                    modifier = Modifier.weight(1f, fill = true)
                ) {
                    Text(
                        text = articleDetail.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )


                    Text(
                        text = articleDetail.description,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 2,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f),
                        overflow = TextOverflow.Ellipsis
                    )
                }
                SpacerVerticalSmall()


                // Read time at bottom
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Read Time",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )

                    Text(
                        text = articleDetail.readTime,
                        style = MaterialTheme.typography.bodySmall,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}