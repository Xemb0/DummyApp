package com.app.harigaji.artical.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.app.harigaji.artical.domain.ArticleDetail
import com.app.harigaji.theme.rememberOuterHorizontalPaddingLarge
import com.app.harigaji.theme.rememberInnerHorizontalPaddingSmall
import com.app.harigaji.theme.rememberInnerHorizontalPaddingMedium
import com.app.harigaji.theme.rememberInnerVerticalPaddingSmall
import com.app.harigaji.theme.rememberHorizontalSpacingMedium
import com.app.harigaji.theme.SpacerVerticalSmall
import com.app.harigaji.theme.SpacerVerticalLarge
import com.app.harigaji.theme.SpacerVerticalExtraLarge
import com.app.harigaji.theme.SpacerHorizontalSmall
import com.app.harigaji.theme.SpacerVerticalMedium
import com.app.harigaji.theme.debugUi
import com.app.harigaji.theme.rememberCornerRadiusMedium


fun LazyListScope.pageArticle(
    modifier: Modifier = Modifier,
    list: List<ArticleDetail>,
    onArticleClick: (ArticleDetail) -> Unit
) {
    val colors = listOf(
        Color(0xFFFFE0B2),
        Color(0xFFC8E6C9),
        Color(0xFFBBDEFB),
        Color(0xFFD1C4E9)
    )
    itemsIndexed(list) { index, article ->
        SpacerVerticalMedium()
        ArticleCard(
            backgroundColor = colors[index % colors.size],
            articleDetail = article,
            modifier = Modifier.padding(horizontal = rememberOuterHorizontalPaddingLarge()),
            onClick = { onArticleClick(article) }
        )
    }
    item {
        SpacerVerticalExtraLarge()
        SpacerVerticalExtraLarge()
        SpacerVerticalExtraLarge()
        SpacerVerticalExtraLarge()
        SpacerVerticalExtraLarge()
    }
}



@Composable
fun ArticleRowContent(
    listArticleDetail: List<ArticleDetail>,
    colors: List<Color>,
    lazyRowState: LazyListState,
) {
    Column(modifier = Modifier.fillMaxWidth().debugUi()) {
        SpacerVerticalSmall()

        // Withdraw Cards
        LazyRow(
            modifier = Modifier.fillMaxWidth().debugUi(),
            state = lazyRowState,
            contentPadding = PaddingValues(horizontal = rememberOuterHorizontalPaddingLarge()),
            horizontalArrangement = Arrangement.spacedBy(rememberHorizontalSpacingMedium())
        ) {
            itemsIndexed(
                items = listArticleDetail,
                key = { index, _ -> index } // Add key for better performance
            ) { index, item ->
                // Safe color selection with proper bounds checking
                val color = if (colors.isNotEmpty()) {
                    colors[index % colors.size]
                } else {
                    Color.Gray // Fallback color
                }

                CardArticles(
                    articleDetail = item,
                    backgroundColor = color,
                    modifier = Modifier.debugUi(),
                    onClick = { }
                )
            }
        }
        SpacerVerticalLarge()
    }
}
@Composable
fun CardArticles(
    articleDetail: ArticleDetail,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .width(300.dp)
            .height(340.dp) // Add fixed height here
            .clip(rememberCornerRadiusMedium())
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .3f),
                        MaterialTheme.colorScheme.onSurface.copy(alpha = .1f)
                    )
                ),
                shape = rememberCornerRadiusMedium()
            )
            .innerShadow(
                shape = rememberCornerRadiusMedium(),
                shadow = Shadow(
                    offset = DpOffset(0.dp, 0.dp),
                    radius = 4.dp,
                    spread = 2.dp,
                    color = Color(0x33000000)
                )
            )
            .clickable { onClick() }
            .debugUi(),
        horizontalAlignment = Alignment.Start
    ) {
        Card(
            modifier = Modifier
                .height(220.dp)
                .debugUi(),
            shape = rememberCornerRadiusMedium(),
            onClick = onClick,
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .debugUi()
            ) {

                articleDetail.imageUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(rememberCornerRadiusMedium())
                            .debugUi()
                    )
                } ?: Box(
                    modifier = Modifier.fillMaxSize()
                        .background(backgroundColor)
                        .debugUi()
                )
                Box(
                    modifier = Modifier
                        .padding(rememberInnerHorizontalPaddingMedium())
                        .clip(rememberCornerRadiusMedium())
                        .background(Color.White.copy(alpha = .6f))
                        .align(Alignment.TopStart)
                        .debugUi()
                ) {
                    Text(
                        text = articleDetail.date,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = .7f),
                        modifier = Modifier
                            .padding(horizontal = rememberInnerHorizontalPaddingMedium(), vertical = rememberInnerVerticalPaddingSmall())
                            .debugUi()
                    )
                }
            }
        }
        SpacerVerticalSmall()
        Text(
            text = articleDetail.title,
            fontSize = 16.sp,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis, // Add this to handle long titles
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(
                    start = rememberInnerHorizontalPaddingMedium(),
                    top = rememberInnerVerticalPaddingSmall(),
                    bottom = rememberInnerHorizontalPaddingMedium(),
                    end = rememberInnerHorizontalPaddingMedium()
                )
                .debugUi()
        )

        Row(modifier = Modifier.padding(horizontal = rememberInnerHorizontalPaddingMedium()).debugUi()) {
            Icon(
                imageVector = Icons.Default.Schedule,
                contentDescription = null,
                modifier = Modifier.alpha(.7f).debugUi()
            )
            SpacerHorizontalSmall()
            Text(
                text = articleDetail.readTime,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(start = rememberInnerHorizontalPaddingSmall())
                    .debugUi()
            )
        }
        SpacerVerticalLarge()
    }
}
