package com.app.harigaji.artical.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.artical.domain.ArticleDetail
import com.app.harigaji.presentation.MyTopBar
import com.app.harigaji.theme.MyMaterialTheme
import com.app.harigaji.theme.SpacerVerticalMedium
import com.app.harigaji.theme.rememberCornerRadiusLarge
import com.app.harigaji.theme.rememberOuterHorizontalPaddingExtraLarge
import com.app.harigaji.theme.rememberOuterVerticalPaddingMedium
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleSearchScreen(
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onArticleClick: (ArticleDetail) -> Unit = {},
    onBackClick: () -> Unit = {},
    colors:List<Color>,
    articleDetails:List<ArticleDetail>
) {
    val searchQuery = remember { mutableStateOf("") }
    


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MyTopBar(
                paddingValues = paddingValues,
                title = "Article Search",
                onLeadingClick = {},
                modifier = Modifier.padding(horizontal = rememberOuterHorizontalPaddingExtraLarge(), vertical = rememberOuterVerticalPaddingMedium())

            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF5F5F5))
                .padding(paddingValues)
                .padding(horizontal = rememberOuterHorizontalPaddingExtraLarge())
        ) {


            // Search Bar
            OutlinedTextField(
                value = searchQuery.value,
                onValueChange = { searchQuery.value = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(rememberCornerRadiusLarge()),
                placeholder = {
                    Text(
                        text = "Search anything...",
                        color = Color(0xFF9E9E9E),
                        fontSize = 16.sp
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color(0xFF006B5C),
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedBorderColor = Color(0xFF006B5C),
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = rememberCornerRadiusLarge()
            )


            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                item {
                    SpacerVerticalMedium()
                }
                itemsIndexed(articleDetails) { index, article, ->

                val color =  colors[index % colors.size]


                    ArticleCard(
                        articleDetail = article,
                        onClick = { onArticleClick(article) },
                        backgroundColor = color,
                        modifier = Modifier
                    )
                    SpacerVerticalMedium()
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewArticleSearchScreen() {

    MyMaterialTheme {
    ArticleSearchScreen(
        articleDetails = listOf(
            ArticleDetail(
                id = 1,
                title = "Participate in the Corra Finance Airdrop & Earn Rewards 🎉",
                description = "Join the Corra community and earn up to $50 worth of tokens by completing simple tasks.",
                imageUrl = null,
                date = "2024-06-15 09:45 AM",
                category = "Trending"
            ),
            ArticleDetail(
                id = 2,
                title = "Web3 Trends: What’s Next for 2025 🚀",
                description = "Explore the next wave of blockchain innovation shaping the financial world.",
                imageUrl = null,
                date = "2024-06-10 10:15 AM",
                category = "Trending"
            ),
            ArticleDetail(
                id = 3,
                title = "DeFi 3.0: The Future of Decentralized Finance 💎",
                description = "A deep dive into next-gen protocols revolutionizing global finance.",
                imageUrl = null,
                date = "2024-06-08 02:30 PM",
                category = "Trending"
            ),
        ),
        colors = listOf(
            Color(0xFFFFE0B2),
            Color(0xFFC8E6C9),
            Color(0xFFBBDEFB),
            Color(0xFFD1C4E9)
        ),
        onArticleClick = {},
        onBackClick = {},
        paddingValues = PaddingValues(0.dp)
    )
    }
}
