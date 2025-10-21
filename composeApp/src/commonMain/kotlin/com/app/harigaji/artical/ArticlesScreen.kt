package com.app.harigaji.artical

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.app.harigaji.presentation.OptionTab
import com.app.harigaji.presentation.OptionTabs
import com.app.harigaji.presentation.ScreenHeader
import kotlinx.coroutines.launch

private val listOfTabOptions = listOf(
    OptionTab(
        id = "1",
        name = "Trending",
        icon = "ic_launcher_foreground",
        color = "#FF0000"
    ),
    OptionTab(
        id = "2",
        name = "Recent",
        icon = "ic_launcher_foreground",
        color = "#00FF00"
    ),
    OptionTab(
        id = "3",
        name = "Saved",
        icon = "ic_launcher_foreground",
        color = "#0000FF"
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticlesScreen(
    viewModel: ArticlesViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onPrevious: () -> Unit,
    paddingValues: PaddingValues
) {
    val articles by viewModel.articles.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    val filteredArticles = remember(articles, selectedCategory) {
        articles.filter { it.category == selectedCategory }
    }

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { listOfTabOptions.size }
    )

    val scope = rememberCoroutineScope()

    val selectedTabOption by remember {
        derivedStateOf { listOfTabOptions[pagerState.currentPage] }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                windowInsets = WindowInsets
                    .systemBars
                    .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent,
                ),
                title = {
                    ScreenHeader(
                        title = "Articles",
                        onBackClick = onPrevious,
                        trailingIcon = {
                            OutlinedIconButton(
                                onClick = {},
                                modifier = Modifier.padding(0.dp)
                                    .size(56.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                    )
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { pv ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(pv),
            userScrollEnabled = false
        ) { page ->
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                // Article row at top - will scroll away
                item {
                    Column {
                        Spacer(modifier = Modifier.height(16.dp))
                        ArticleRowContent(
                            listArticle = articles,
                            colors = listOf(
                                Color(0xFFFFE0B2),
                                Color(0xFFC8E6C9),
                                Color(0xFFBBDEFB),
                                Color(0xFFD1C4E9)
                            ),
                            lazyRowState = androidx.compose.foundation.lazy.LazyListState(),
                        )
                    }
                }

                // Sticky tabs
                stickyHeader {
                    OptionTabs(
                        height = 48.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        bgColor = Color.Gray,
                        ascentColor = Color.White,
                        selectedOption = selectedTabOption,
                        onOptionSelected = { option ->
                            scope.launch {
                                pagerState.scrollToPage(
                                    page = listOfTabOptions.indexOf(option)
                                )
                            }
                        },
                        options = listOfTabOptions,
                        selectedOptionTextColor = when (pagerState.currentPage) {
                            0 -> MaterialTheme.colorScheme.onBackground
                            1 -> Color(0xFFFFA500)
                            2 -> MaterialTheme.colorScheme.secondary
                            else -> Color.Blue
                        }
                    )
                }

                // Page content
                when (page) {
                    0 -> {
                        pageArticle(
                            list = articles.filter { it.category == "Trending" }
                        )
                    }

                    1 -> {
                        pageArticle(
                            list = articles.filter { it.category == "Recent" }
                        )
                    }

                    2 -> {
                        pageArticle(
                            list = articles.filter { it.category == "Saved" }
                        )
                    }
                }
            }
        }
    }
}

fun LazyListScope.pageArticle(
    modifier: Modifier = Modifier,
    list: List<Article>
) {
    val colors = listOf(
        Color(0xFFFFE0B2),
        Color(0xFFC8E6C9),
        Color(0xFFBBDEFB),
        Color(0xFFD1C4E9)
    )
    itemsIndexed(list) { index, article ->
        ArticleCard(
            backgroundColor = colors[index % list.size],
            article = article,
        )
    }
    item {
        Spacer(modifier = Modifier.height(180.dp))
    }
}



@Composable
fun ArticleRowContent(
    listArticle: List<Article>,
    colors: List<Color>,
    lazyRowState: androidx.compose.foundation.lazy.LazyListState,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.height(8.dp))

        // Withdraw Cards
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = lazyRowState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(listArticle) { index, item ->
                val color = colors[index % colors.size]
                CardArticles(
                    article = item,
                    backgroundColor = color,
                    modifier = Modifier,
                    onClick = { }
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CardArticles(
    article: Article,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .width(300.dp)
            .wrapContentHeight()
            .clickable { onClick() },
        horizontalAlignment = Alignment.Start
    ) {
        Card(
            modifier = modifier
                .height(220.dp),
            shape = RoundedCornerShape(28.dp),
            onClick = onClick,
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(CircleShape)
                        .fillMaxWidth(.7f)
                        .background(Color.White.copy(alpha = .4f))
                        .align(Alignment.TopStart)
                ) {
                    Text(
                        text = article.date,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
                article.imageUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(28.dp))
                    )
                } ?: Box(
                    modifier = Modifier.fillMaxSize()
                        .background(backgroundColor)
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = article.title,
            fontSize = 20.sp,
            maxLines = 3,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp, bottom = 16.dp)
        )

        Row {
            Icon(
                imageVector = Icons.Default.Schedule,
                contentDescription = null,
                modifier = Modifier.alpha(.7f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = article.readTime,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
    }
}