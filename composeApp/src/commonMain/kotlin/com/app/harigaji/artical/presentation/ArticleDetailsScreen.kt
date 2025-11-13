package com.app.harigaji.artical.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.BookmarkAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.app.harigaji.artical.domain.ArticleDetail
import com.app.harigaji.core.customcomposables.gradients.darkSecondaryGradient
import com.app.harigaji.presentation.MyTopBar
import com.app.harigaji.theme.MyMaterialTheme
import com.app.harigaji.theme.SpacerVerticalLarge
import com.app.harigaji.theme.rememberOuterHorizontalPaddingExtraLarge
import com.app.harigaji.theme.rememberOuterVerticalPaddingMedium
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.abs

@Composable
fun ArticleDetailRoot(
    paddingValues: PaddingValues,
    onPrevious: () -> Unit = {},
    articleID: Int,
    articleViewModel: ArticleViewModel = koinViewModel()
) {
    val article by articleViewModel.state.collectAsStateWithLifecycle()

    ArticleDetailScreen(
        paddingValues = paddingValues,
        article = article.listArticleDetail.find { it.id == articleID },
        onPrevious = onPrevious
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailScreen(
    paddingValues: PaddingValues,
    article: ArticleDetail? = null,
    onPrevious: () -> Unit = {}
) {
    val outerHorizontalPadding = rememberOuterHorizontalPaddingExtraLarge()
    val outerVerticalPadding = rememberOuterVerticalPaddingMedium()

    // Animation states
    var isVisible by remember { mutableStateOf(false) }
    var imageLoaded by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    val density = LocalDensity.current
    val sheetPeekHeight = 620.dp

    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.PartiallyExpanded,
        skipHiddenState = true
    )

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = bottomSheetState
    )

    // Calculate expansion ratio based on sheet state
    val expansionRatio by remember {
        derivedStateOf {
            try {
                val currentOffset = bottomSheetState.requireOffset()
                val peekHeightPx = with(density) { sheetPeekHeight.toPx() }
                val topBarHeightPx = with(density) { 100.dp.toPx() }

                // Calculate how much the sheet has moved up from peek position
                val maxTravel = peekHeightPx - topBarHeightPx
                val currentTravel = (peekHeightPx - currentOffset).coerceAtLeast(0f)

                (currentTravel / maxTravel).coerceIn(0f, 1f)
            } catch (e: Exception) {
                0f
            }
        }
    }

    // Animate title card properties based on expansion
    val titleCardWidthFraction by animateFloatAsState(
        targetValue = 0.6f + (0.40f * expansionRatio),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    val titleCardCornerRadius by animateFloatAsState(
        targetValue = 24f - (6f * expansionRatio),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    val titleCardElevation by animateFloatAsState(
        targetValue = 16f + (12f * expansionRatio),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    // Parallax effect for image
    val parallaxOffset by animateDpAsState(
        targetValue = if (scrollState.value > 0) (-scrollState.value / 3).dp else 0.dp,
        animationSpec = tween(300)
    )

    // Animated gradient colors
    val infiniteTransition = rememberInfiniteTransition()
    val gradientShift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        isVisible = true
    }

    var isBookmarked by remember { mutableStateOf(false) }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {},
        containerColor = Color.Transparent,
        sheetContainerColor = Color.Transparent,
        sheetDragHandle = {},
        sheetShape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        sheetPeekHeight = sheetPeekHeight,
        sheetSwipeEnabled = true,
        sheetContent = {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column {
                    // Space for title card
                    Box(modifier = Modifier.height(65.dp - (2f*titleCardCornerRadius).dp))

                    // Bottom sheet content
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .verticalScroll(scrollState)
                            .padding(horizontal = outerHorizontalPadding)
                            .padding(top = 160.dp, bottom = 32.dp)
                    ) {
                        Text(
                            text = article?.description ?: "",
                            fontSize = 16.sp,
                            color = Color(0xFF4A5568),
                            lineHeight = 26.sp
                        )

                        SpacerVerticalLarge()
                    }
                }

                // Floating Title Card with animated properties
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 0.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(titleCardWidthFraction),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = titleCardElevation.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        shape = RoundedCornerShape(titleCardCornerRadius.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Text(
                                text = article?.title ?: "",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF1A1A1A),
                                lineHeight = 32.sp
                            )

                            Spacer(modifier = Modifier.height(16.dp))

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
                                    // Animated author icon
                                    val iconRotation by infiniteTransition.animateFloat(
                                        initialValue = -5f,
                                        targetValue = 5f,
                                        animationSpec = infiniteRepeatable(
                                            animation = tween(2000, easing = LinearEasing),
                                            repeatMode = RepeatMode.Reverse
                                        )
                                    )

                                    Box(
                                        modifier = Modifier
                                            .size(40.dp)
                                            .clip(CircleShape)
                                            .background(
                                                Brush.linearGradient(
                                                    colors = listOf(
                                                        Color(0xFFFFE4CC),
                                                        Color(0xFFFFD4A3)
                                                    )
                                                )
                                            )
                                            .graphicsLayer {
                                                rotationZ = iconRotation
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Person,
                                            contentDescription = null,
                                            tint = Color(0xFFFF9800),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }

                                    Column {
                                        Text(
                                            text = article?.author ?: "",
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = Color(0xFF1A1A1A)
                                        )
                                        Text(
                                            text = article?.date ?: "",
                                            fontSize = 12.sp,
                                            color = Color(0xFF9E9E9E)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        when (article) {
            null -> {}
            else -> {
                val parallaxPx = with(LocalDensity.current) { parallaxOffset.toPx() }

                // 1) Background image (fills the whole parent)
                article?.imageUrl?.let { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = article.title,
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer {
                                // apply parallax in px
                                translationY = parallaxPx
                            },
                        contentScale = ContentScale.Crop,
                        onSuccess = { imageLoaded = true },
                        onError = { /* you can set a fallback here or log */ }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
//                        .background(darkSecondaryGradient)
                        .padding(innerPadding)
                ) {


                    // Top Bar - stays visible
                    MyTopBar(
                        paddingValues = paddingValues,
                        title = "Article Detail",
                        onLeadingClick = onPrevious,
                        onTrailingClick = {
                            isBookmarked = !isBookmarked
                        },
                        trailingIconTint = if (isBookmarked) Color(0xFFFF6B35) else MaterialTheme.colorScheme.secondary,
                        trailingIconVector = if (isBookmarked) Icons.Outlined.Bookmark else Icons.Outlined.BookmarkAdd,
                        backgroundColor = Color.Transparent,
                        backButtonBackgroundColor = Color.White.copy(alpha = 0.3f),
                        modifier = Modifier.padding(
                            horizontal = outerHorizontalPadding,
                            vertical = outerVerticalPadding
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewArticleDetailScreen() {
    MyMaterialTheme {
        ArticleDetailScreen(
            paddingValues = PaddingValues(),
            article = ArticleDetail(
                id = 1,
                title = "Participate in the Corra Finance Airdrop on CoinMarketCap",
                author = "Johny Doe",
                date = "3 days ago",
                description = "CoinMarketCap is currently hosting an airdrop with Corra.Finance. In this airdrop, there are 600 CORA up for grabs, and there will be 2,000 winners. For more information about crypto airdrops in general.\n\nTo participate in the Corra Finance airdrop, head over to the Corra.Finance coin page on CoinMarketCap.\n\nWhen you are there, scroll down to see the list of instructions for participating in the airdrop. The most important first step is to login to your CoinMarketCap account.",
                imageUrl = "https://picsum.photos/seed/saved7/400/200",
                category = "Trending",
                readTime = "5 min read",
            )
        )
    }
}