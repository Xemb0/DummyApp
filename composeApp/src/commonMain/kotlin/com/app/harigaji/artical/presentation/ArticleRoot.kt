package com.app.harigaji.artical.presentation
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Article
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.harigaji.artical.data.ArticleAction
import com.app.harigaji.artical.data.ArticleRepositoryImpl
import com.app.harigaji.artical.data.ArticleState
import com.app.harigaji.artical.domain.ArticleDetail
import com.app.harigaji.presentation.MyTopBar
import com.app.harigaji.presentation.OptionTab
import com.app.harigaji.presentation.OptionTabs
import com.app.harigaji.theme.MyMaterialTheme
import com.app.harigaji.theme.debugUi
import com.app.harigaji.theme.rememberCornerRadiusMedium
import com.app.harigaji.theme.rememberHorizontalSpacingMedium
import com.app.harigaji.theme.rememberInnerVerticalPaddingSmall
import com.app.harigaji.theme.rememberOuterHorizontalPaddingExtraLarge
import com.app.harigaji.theme.rememberOuterHorizontalPaddingLarge
import com.app.harigaji.theme.rememberOuterVerticalPaddingMedium
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

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

@Composable
fun ArticleRoot(
    paddingValues: PaddingValues,
    viewModel: ArticleViewModel = koinViewModel(),
    onPrevious: () -> Unit = {},
    onArticleClick: (ArticleDetail) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ArticleScreen(
        paddingValues = paddingValues,
        onPrevious = onPrevious,
        onArticleClick = onArticleClick,
        state = state,
        onAction = viewModel::onAction
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    paddingValues: PaddingValues,
    state: ArticleState,
    onAction: (ArticleAction) -> Unit,
    modifier: Modifier = Modifier,
    onPrevious: () -> Unit = {},
    onArticleClick: (ArticleDetail) -> Unit = {},
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { listOfTabOptions.size }
    )

    val scope = rememberCoroutineScope()

    val selectedTabOption by remember {
        derivedStateOf { listOfTabOptions[pagerState.currentPage] }
    }

    // Get current category based on selected tab
    val selectedCategory by remember {
        derivedStateOf {
            when (pagerState.currentPage) {
                0 -> "Trending"
                1 -> "Recent"
                2 -> "Saved"
                else -> "Trending"
            }
        }
    }

    // Filter articles based on current page/category
    val filteredArticles by remember(state.listArticleDetail, selectedCategory) {
        derivedStateOf {
            state.listArticleDetail.filter { it.category == selectedCategory }
        }
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        topBar = {
            MyTopBar(
                paddingValues = paddingValues,
                title = "Article",
                onLeadingClick = onPrevious,
                modifier = Modifier.fillMaxWidth().padding(horizontal = rememberOuterHorizontalPaddingExtraLarge(), vertical = rememberOuterVerticalPaddingMedium())
            )
        },
        bottomBar = {}
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Show loading shimmer when data is loading
            if (state.isLoading) {
                ArticleLoadingShimmer()
            }
            // Show error message if there's an error
            else if (state.error != null) {
                ArticleErrorState(
                    error = state.error,
                    onRetry = { onAction(ArticleAction.LoadArticles) }
                )
            }
            // Show empty state if no articles
            else if (state.listArticleDetail.isEmpty()) {
                ArticleEmptyState()
            }
            // Show content when data is loaded
            else {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .debugUi(),
                    userScrollEnabled = false
                ) { page ->
                    LazyColumn(
                        modifier = Modifier.fillMaxSize().debugUi()
                    ) {
                        // Article row at top - will scroll away
                        item {
                            Column(modifier = Modifier.debugUi()) {
                                ArticleRowContent(
                                    listArticleDetail = state.listArticleDetail,
                                    colors = listOf(
                                        Color(0xFFFFE0B2),
                                        Color(0xFFC8E6C9),
                                        Color(0xFFBBDEFB),
                                        Color(0xFFD1C4E9)
                                    ),
                                    lazyRowState = LazyListState(),
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
                                    .padding(
                                        horizontal = rememberOuterHorizontalPaddingLarge(),
                                        vertical = rememberInnerVerticalPaddingSmall()
                                    )
                                    .debugUi(),
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

                        // Page content - filtered articles
                        if (filteredArticles.isEmpty()) {
                            item {
                                ArticleEmptyCategoryState(category = selectedCategory)
                            }
                        } else {
                            pageArticle(list = filteredArticles, onArticleClick = onArticleClick)
                        }
                    }
                }
            }
        }
    }
}

// Shimmer Loading Effect
@Composable
fun ArticleLoadingShimmer() {
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.5f),
        Color.LightGray.copy(alpha = 0.3f)
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(translateAnim - 1000f, translateAnim - 1000f),
        end = Offset(translateAnim, translateAnim)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Shimmer for horizontal row
        LazyRow(
            contentPadding = PaddingValues(horizontal = rememberOuterHorizontalPaddingLarge()),
            horizontalArrangement = Arrangement.spacedBy(rememberHorizontalSpacingMedium())
        ) {
            items(3) {
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .height(340.dp)
                        .clip(rememberCornerRadiusMedium())
                        .background(brush)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Shimmer for tabs
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .padding(horizontal = rememberOuterHorizontalPaddingLarge())
                .clip(RoundedCornerShape(24.dp))
                .background(brush)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Shimmer for article cards
        repeat(5) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(
                        horizontal = rememberOuterHorizontalPaddingLarge(),
                        vertical = 8.dp
                    )
                    .clip(rememberCornerRadiusMedium())
                    .background(brush)
            )
        }
    }
}

// Error State
@Composable
fun ArticleErrorState(
    error: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Article,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 16.dp),
                tint = MaterialTheme.colorScheme.error.copy(alpha = 0.6f)
            )

            Text(
                text = "Oops! Something went wrong",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Button(
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text("Retry")
            }
        }
    }
}

// Empty State - No articles at all
@Composable
fun ArticleEmptyState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Article,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .padding(bottom = 16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
            )

            Text(
                text = "No Articles Yet",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Check back later for new articles",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Empty Category State - No articles in specific category
@Composable
fun ArticleEmptyCategoryState(category: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 80.dp, horizontal = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Outlined.Article,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .padding(bottom = 12.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
            )

            Text(
                text = "No $category Articles",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Text(
                text = "Try checking another category",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

// Preview Composables
@Preview
@Composable
private fun PreviewArticleScreen() {
    val dummyRepo = ArticleRepositoryImpl()
    val dummyState = ArticleState(
        listArticleDetail = dummyRepo.dummyArticleDetails,
        isLoading = false,
        error = null
    )

    MyMaterialTheme {
        ArticleScreen(
            paddingValues = PaddingValues(0.dp),
            state = dummyState,
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun PreviewArticleScreenLoading() {
    val dummyState = ArticleState(
        listArticleDetail = emptyList(),
        isLoading = true,
        error = null
    )

    MyMaterialTheme {
        ArticleScreen(
            paddingValues = PaddingValues(0.dp),
            state = dummyState,
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun PreviewArticleScreenError() {
    val dummyState = ArticleState(
        listArticleDetail = emptyList(),
        isLoading = false,
        error = "Failed to load articles. Please check your internet connection."
    )

    MyMaterialTheme {
        ArticleScreen(
            paddingValues = PaddingValues(0.dp),
            state = dummyState,
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun PreviewArticleScreenEmpty() {
    val dummyState = ArticleState(
        listArticleDetail = emptyList(),
        isLoading = false,
        error = null
    )

    MyMaterialTheme {
        ArticleScreen(
            paddingValues = PaddingValues(0.dp),
            state = dummyState,
            onAction = {}
        )
    }
}