package com.app.harigaji.artical.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.harigaji.artical.domain.ArticleDetail
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel



@Composable
fun ArticleDetailRoot(
    paddingValues: PaddingValues,
    onPrevious: () -> Unit = {},
    articleID:Int,
    articleViewModel: ArticleViewModel = koinViewModel()
) {

    val article by articleViewModel.state.collectAsStateWithLifecycle()


    ArticleDetailScreen(
        paddingValues = paddingValues,
        article = article.listArticleDetail.find { it.id == articleID },
        onPrevious = onPrevious
    )
}

// Article Detail Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    paddingValues: PaddingValues,
    article: ArticleDetail? = null,
    onPrevious: () -> Unit = {}
) {
    Scaffold { innerPadding ->


        when(article){
            null -> {}
            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF4ADE80),
                                    Color(0xFF14B8A6)
                                )
                            )
                        )
                        .padding(innerPadding)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        // Header with Back and Bookmark
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(
                                onClick = onPrevious,
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(Color.Transparent)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Back",
                                    tint = Color(0xFF065F46),
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            IconButton(
                                onClick = { /* Handle bookmark */ },
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(CircleShape)
                                    .background(Color.Transparent)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = "Bookmark",
                                    tint = Color(0xFF065F46),
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(80.dp))

                        // Content Card
                        Card(
                            modifier = Modifier
                                .fillMaxSize(),
                            shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                                    .padding(32.dp)
                            ) {
                                // Title
                                Text(
                                    text = article.title,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1A1A1A),
                                    lineHeight = 36.sp
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                // Author Info
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(48.dp)
                                                .clip(CircleShape)
                                                .background(Color(0xFFFFE4CC)),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.Person,
                                                contentDescription = null,
                                                tint = Color(0xFFFF9800),
                                                modifier = Modifier.size(24.dp)
                                            )
                                        }

                                        Text(
                                            text = article.author,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF1A1A1A)
                                        )
                                    }

                                    Text(
                                        text = article.date,
                                        fontSize = 14.sp,
                                        color = Color(0xFF9E9E9E)
                                    )
                                }

                                Spacer(modifier = Modifier.height(32.dp))

                                // Content
                                Text(
                                    text = article.description,
                                    fontSize = 16.sp,
                                    color = Color(0xFF4A5568),
                                    lineHeight = 26.sp
                                )

                                Spacer(modifier = Modifier.height(48.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

// Preview for Detail Screen
@Preview
@Composable
fun PreviewArticleDetailScreen() {
    ArticleDetailScreen(
        paddingValues = PaddingValues(),
        article = ArticleDetail(
            id = 1,
            title = "Sample Article",
            author = "John Doe",
            date = "July 15, 2023",
            description = "This is a sample article description with some content and images to showcase the detail screen functionality in Jetpack Compose and its capabilities in Android development environment .",
            imageUrl = "https://example.com/image.jpg",
            category = "Trending",
            readTime = "1023-2343",
        )
    )
}