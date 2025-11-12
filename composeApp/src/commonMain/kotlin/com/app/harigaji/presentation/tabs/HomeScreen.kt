package com.app.harigaji.presentation.tabs

import SalaryWithdrawCard
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.core.user.UserDetails
import com.app.harigaji.presentation.DateRow
import com.app.harigaji.presentation.OptionTab
import com.app.harigaji.presentation.OptionTabs
import com.app.harigaji.presentation.SearchBarM3
import com.app.harigaji.presentation.SlideToClockIn
import com.app.harigaji.presentation.WithdrawCard
import com.app.harigaji.theme.MyMaterialTheme
import com.app.harigaji.theme.SpacerHorizontalMedium
import com.app.harigaji.theme.SpacerHorizontalSmall
import com.app.harigaji.theme.SpacerVerticalLarge
import com.app.harigaji.theme.SpacerVerticalMedium
import com.app.harigaji.theme.SpacerVerticalSmall
import com.app.harigaji.theme.SpacerVerticalExtraLarge
import com.app.harigaji.theme.rememberCornerRadiusLarge
import com.app.harigaji.theme.rememberInnerVerticalPaddingSmall

import com.app.harigaji.theme.rememberOuterHorizontalPaddingSmall
import com.app.harigaji.theme.rememberPaddingMedium
import com.app.harigaji.theme.debugUi
import com.app.harigaji.theme.rememberCornerRadiusMedLargeDp
import com.app.harigaji.theme.rememberCornerRadiusMedium
import com.app.harigaji.theme.rememberCornerRadiusMediumDp
import com.app.harigaji.theme.rememberInnerHorizontalPaddingMedium
import com.app.harigaji.theme.rememberOuterHorizontalPaddingExtraLarge
import com.app.harigaji.theme.rememberOuterHorizontalPaddingLarge
import com.app.harigaji.theme.rememberOuterHorizontalPaddingMedium
import com.app.harigaji.theme.rememberOuterVerticalPaddingMedium
import com.app.harigaji.theme.rememberOuterVerticalPaddingLarge
import com.app.harigaji.theme.rememberSizeExtraLarge
import com.app.harigaji.theme.rememberSizeLarge
import com.app.harigaji.theme.rememberSizeSmall
import com.app.harigaji.theme.rememberTextStyleExSmall
import com.app.harigaji.theme.rememberTextStyleLarge
import com.app.harigaji.theme.rememberTextStyleSmall
import com.app.harigaji.theme.rememberTextStyleMedium
import com.app.harigaji.theme.rememberTextStyleTitle
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_calender
import harigaji.composeapp.generated.resources.ic_clock
import harigaji.composeapp.generated.resources.ic_exit
import harigaji.composeapp.generated.resources.ic_message_dot
import harigaji.composeapp.generated.resources.ic_profile
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.abs

private val listOfTabOptions = listOf(
    OptionTab(
        id = "1",
        name = "Salary",
        icon = "ic_launcher_foreground",
        color = "#FF0000"
    ),
    OptionTab(
        id = "2",
        name = "Attendance",
        icon = "ic_launcher_foreground",
        color = "#00FF00"
    ),
)

private val WITHDRAW_COLORS = listOf(
    Color(0xFFF48FB1),
    Color(0xFF81D4FA),
    Color(0xFF4DD0E1)
)

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    userDetails: UserDetails,
    onClockIn: () -> Unit,
    onBalanceCardClick: () -> Unit = {},
    onMessageAdminClick: () -> Unit = {},
) {
    var selectedTabOption by remember { mutableStateOf(listOfTabOptions[0]) }
    val listState = rememberLazyListState()

    // Optimized scroll calculations with snapshotFlow
    val scrollOffset by produceState(0, listState) {
        snapshotFlow { listState.firstVisibleItemScrollOffset }
            .collect { value = it }
    }

    val firstVisibleItem by produceState(0, listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .collect { value = it }
    }

    // Simplified derived states
    val isHeaderStuck = firstVisibleItem >= 4

    val collapseFraction = remember(firstVisibleItem, scrollOffset) {
        when {
            firstVisibleItem > 0 -> 1f
            else -> (scrollOffset / 300f).coerceIn(0f, 1f)
        }
    }

    // Pre-calculate layout values
    val cornerRadius = remember(collapseFraction) {
        (48 - (24 * collapseFraction)).dp.coerceAtLeast(24.dp)
    }

    // Animated values with reduced frequency
    val stickyHeaderElevation by animateDpAsState(
        targetValue = if (isHeaderStuck) 16.dp else 0.dp,
        animationSpec = tween(durationMillis = 200),
        label = "headerElevation"
    )

    val stickyHeaderCornerRadius by animateDpAsState(
        targetValue = if (isHeaderStuck) 24.dp else 0.dp,
        animationSpec = tween(durationMillis = 200),
        label = "headerCornerRadius"
    )

    val stickyHeaderBackgroundColor by animateColorAsState(
        targetValue = if (isHeaderStuck)
            MaterialTheme.colorScheme.surface
        else
            MaterialTheme.colorScheme.background,
        animationSpec = tween(durationMillis = 200),
        label = "headerBackground"
    )

    // Static data - moved outside composition
    val listWithdraw = remember {
        listOf(
            WithdrawData("RM 12,000", "October"),
            WithdrawData("RM 10,000", "September"),
            WithdrawData("RM 8,000", "August"),
            WithdrawData("RM 15,000", "July"),
            WithdrawData("RM 9,500", "June"),
        )
    }

    val lazyRowState = rememberLazyListState()

    // Optimized carousel index with debouncing
    val currentIndex by produceState(0, lazyRowState) {
        snapshotFlow {
            val layoutInfo = lazyRowState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo
            if (visibleItems.isEmpty()) {
                0
            } else {
                val viewportCenter = layoutInfo.viewportStartOffset +
                        layoutInfo.viewportSize.width / 2
                visibleItems.minByOrNull { item ->
                    abs((item.offset + item.size / 2) - viewportCenter)
                }?.index ?: 0
            }
        }.collect { value = it }
    }

    // Stable callbacks
    val onTabSelected = remember {
        { option: Any ->
            selectedTabOption = option as OptionTab
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopBarDate(paddingValues)
        }
    ) { pv ->
        Box(
            modifier = Modifier
                .padding(pv)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .debugUi()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize().debugUi(),
                state = listState,
                horizontalAlignment = Alignment.CenterHorizontally
//                 verticalArrangement = Arrangement.spacedBy(rememberVerticalPaddingSmall())
            ) {
                stickyHeader(key = "user_header") {
                    UserHeaderCard(
                        userDetails = userDetails,
                        collapseFraction = collapseFraction,
                        onActionClick = onMessageAdminClick
                    )
                }

                item(key = "search_section") {
                    SearchSection(
                        cornerRadius = rememberCornerRadiusMedLargeDp(),
                        collapseFraction = collapseFraction
                    )
                }

                item(key = "salary_card") {
                    val paddingLarge = rememberOuterHorizontalPaddingLarge()
                    val paddingMedium = rememberOuterVerticalPaddingMedium()
                    SalaryWithdrawCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = paddingLarge, vertical = paddingMedium),
                        onBalanceCardClick = onBalanceCardClick
                    )
                }


                stickyHeader(key = "tab_header") {
                    TabHeaderSection(
                        stickyHeaderBackgroundColor = stickyHeaderBackgroundColor,
                        stickyHeaderCornerRadius = stickyHeaderCornerRadius,
                        stickyHeaderElevation = stickyHeaderElevation,
                        selectedTabOption = selectedTabOption,
                        onOptionSelected = onTabSelected,
                        listOfTabOptions = listOfTabOptions
                    )
                }
                item {
                    SpacerVerticalLarge()
                }

                when (selectedTabOption.id) {
                    "1" -> {
                        salaryTabContent(
                            listWithdraw = listWithdraw,
                            colors = WITHDRAW_COLORS,
                            lazyRowState = lazyRowState,
                            currentIndex = currentIndex
                        )
                    }
                    "2" -> {
                        attendanceTabContent(
                            listAttendanceContent = listOf(
                                KpiData(
                                    title = "Last Logged out at",
                                    content = "21 Jan 2024"
                                )
                            ),
                            onClockIn = onClockIn
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBarDate(paddingValues: PaddingValues) {
    Surface(
        modifier = Modifier.fillMaxWidth().debugUi(),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(horizontal = rememberOuterHorizontalPaddingLarge())
                .debugUi()
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_calender),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(16.dp)
            )
            SpacerHorizontalSmall()
            DateRow()
        }
    }
}

@Composable
private fun UserHeaderCard(
    userDetails: UserDetails,
    collapseFraction: Float,
    onActionClick: (() -> Unit)? = null
) {
      // Use Surface instead of Card for better performance
      Surface(
         modifier = Modifier.fillMaxWidth().debugUi(),
          color = MaterialTheme.colorScheme.surface
      ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = rememberOuterHorizontalPaddingLarge(), vertical = rememberInnerVerticalPaddingSmall())
                    .debugUi(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(rememberSizeExtraLarge())
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .debugUi()
                ) {
                    Image(
                        painter = painterResource(Res.drawable.ic_profile),
                        contentDescription = null,
                        modifier = Modifier
                            .size(size = rememberSizeExtraLarge())
                            .align(Alignment.Center)
                    )
                }
                SpacerHorizontalMedium()
                Column(
                    modifier = Modifier.weight(1f).debugUi(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    BasicText(
                        text = "Hi, ${userDetails.name ?: ""} !",
                        style = rememberTextStyleLarge(fontWeight = FontWeight.Bold).copy(color = MaterialTheme.colorScheme.onBackground),
                        maxLines = 1,
                        modifier = Modifier.basicMarquee()
                    )
                    Row(
                        modifier = Modifier
                            .background(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = .15f)
                            )
                            .padding(horizontal = rememberInnerHorizontalPaddingMedium(), vertical = rememberInnerVerticalPaddingSmall())
                            .debugUi(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(rememberSizeSmall())
                        )
                        SpacerHorizontalSmall()
                        BasicText(
                            text = "Pro Member",
                            style = rememberTextStyleExSmall(fontWeight = FontWeight.Medium).copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }
                }
                onActionClick?.let { action ->
                    OutlinedIconButton(
                        onClick = { action() },
                        modifier = Modifier
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_message_dot),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(rememberSizeSmall())
                        )
                    }
                }
            }

    }
}

@Composable
private fun SearchSection(
    cornerRadius: Dp,
    collapseFraction: Float
) {
    // Optimize shadow rendering with drawWithCache
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .debugUi()
            .graphicsLayer {
                shadowElevation = if (collapseFraction < 0.5f) 16f else 0f
                shape = RoundedCornerShape(bottomEnd = cornerRadius, bottomStart = cornerRadius)
                clip = true
            },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(
            bottomStart = cornerRadius,
            bottomEnd = cornerRadius
        )
    ) {
        AnimatedVisibility(
            visible = collapseFraction < 0.5f,
            enter = fadeIn(tween(150)) + expandVertically(tween(150)),
            exit = fadeOut(tween(150)) + shrinkVertically(tween(150))
        ) {
            SearchBarM3(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = rememberOuterHorizontalPaddingMedium())
                    .graphicsLayer {
                        alpha = 1f - (collapseFraction * 2).coerceIn(0f, 1f)
                    },
                onSearch = {},
                onItemClick = {},
                searchHeading = "Search for anything",
            )
        }
        SpacerVerticalSmall()
    }
}

@Composable
private fun TabHeaderSection(
    stickyHeaderBackgroundColor: Color,
    stickyHeaderCornerRadius: Dp,
    stickyHeaderElevation: Dp,
    selectedTabOption: OptionTab,
    onOptionSelected: (Any) -> Unit,
    listOfTabOptions: List<OptionTab>
) {
    // Remove LaunchedEffect logger for better performance
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .debugUi()
            .graphicsLayer {
                shadowElevation = stickyHeaderElevation.toPx()
                shape = RoundedCornerShape(
                    bottomStart = stickyHeaderCornerRadius,
                    bottomEnd = stickyHeaderCornerRadius
                )
                clip = true
            },
        color = stickyHeaderBackgroundColor,
        shape = RoundedCornerShape(
            bottomStart = stickyHeaderCornerRadius,
            bottomEnd = stickyHeaderCornerRadius
        )
    ) {
        OptionTabs(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = rememberOuterHorizontalPaddingMedium(), vertical = rememberOuterHorizontalPaddingMedium()),
            bgColor = Color.Gray,
            ascentColor = Color.White,
            selectedOption = selectedTabOption,
            onOptionSelected = onOptionSelected,
            options = listOfTabOptions,
            selectedOptionTextColor = MaterialTheme.colorScheme.onSurface
        )
    }
}

fun LazyListScope.salaryTabContent(
    listWithdraw: List<WithdrawData>,
    colors: List<Color>,
    lazyRowState: LazyListState,
    currentIndex: Int
) {
    val listSalaryEarned = listOf(
        SalaryEarnedData("16 Oct 2025", "2,500"),
        SalaryEarnedData("15 Oct 2025", "3,200"),
        SalaryEarnedData("14 Oct 2025", "1,800"),
        SalaryEarnedData("13 Oct 2025", "2,500"),
        SalaryEarnedData("12 Oct 2025", "3,200"),
        SalaryEarnedData("11 Oct 2025", "1,800"),
        SalaryEarnedData("10 Oct 2025", "2,700"),
        SalaryEarnedData("09 Oct 2025", "3,000"),
        SalaryEarnedData("08 Oct 2025", "2,400"),
        SalaryEarnedData("07 Oct 2025", "1,900"),
        SalaryEarnedData("06 Oct 2025", "2,800"),
        SalaryEarnedData("05 Oct 2025", "3,100"),
        SalaryEarnedData("04 Oct 2025", "2,200"),
        SalaryEarnedData("03 Oct 2025", "2,600"),
        SalaryEarnedData("02 Oct 2025", "1,700"),
        SalaryEarnedData("01 Oct 2025", "3,000"),
        SalaryEarnedData("30 Sep 2025", "2,900"),
        SalaryEarnedData("29 Sep 2025", "2,400"),
        SalaryEarnedData("28 Sep 2025", "1,800"),
        SalaryEarnedData("27 Sep 2025", "3,200"),
        SalaryEarnedData("26 Sep 2025", "2,600"),

    )

    item(key = "withdraw_title") {
        TitleWithMoreRow(
            title = "My Withdraw",
            onSeeAllClick = {},
            titleMore = "See All",
            modifier = Modifier.padding(horizontal = rememberOuterHorizontalPaddingLarge())
        )
    }

    item {
        SpacerVerticalSmall()
    }

    item(key = "withdraw_carousel") {
        WithdrawCarousel(
            listWithdraw = listWithdraw,
            colors = colors,
            lazyRowState = lazyRowState,
            currentIndex = currentIndex,
            modifier = Modifier
        )
    }

    item {
        SpacerVerticalLarge()
    }

    item(key = "salary_earned_header") {
        TitleWithMoreRow(
            title = "Salary Earned",
            onSeeAllClick = {},
            modifier = Modifier.padding(horizontal = rememberOuterHorizontalPaddingLarge())
        )
    }

    item {
        SpacerVerticalMedium()
    }

    items(
        items = listSalaryEarned,
        key = { it.date },
    ) { item ->
        InfoCard(
            label = "Salary earned",
            value = item.date,
            tag = item.salary,
            icon = Res.drawable.ic_clock,
            modifier = Modifier.padding(horizontal = rememberOuterHorizontalPaddingLarge()),
            color = MaterialTheme.colorScheme.secondary,
            elevation = 0.dp,
//            borderStroke = 1.dp


        )
        SpacerVerticalMedium()
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
private fun WithdrawCarousel(
    listWithdraw: List<WithdrawData>,
    colors: List<Color>,
    lazyRowState: LazyListState,
    currentIndex: Int,
    modifier: Modifier,
) {
    Column(modifier = modifier.debugUi()) {
        LazyRow(
            modifier = Modifier.fillMaxWidth().debugUi(),
            state = lazyRowState,
            contentPadding = PaddingValues(horizontal = rememberOuterHorizontalPaddingLarge(), vertical = rememberOuterVerticalPaddingMedium()),
            horizontalArrangement = Arrangement.spacedBy(rememberOuterHorizontalPaddingLarge())
        ) {
            itemsIndexed(
                items = listWithdraw,
                key = { _, item -> item.month }
            ) { index, item ->
                val color = colors[index % colors.size]
                WithdrawCard(
                    amount = item.amount,
                    month = item.month,
                    color = color,
                    modifier = Modifier
                        .wrapContentWidth()
                        .height(IntrinsicSize.Max)
                )
            }
        }

        SpacerVerticalMedium()

        if (listWithdraw.size > 1) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .debugUi(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(listWithdraw.size) { iteration ->
                    val color = if (currentIndex == iteration) {
                        MaterialTheme.colorScheme.secondary
                    } else {
                        Color.Gray.copy(alpha = 0.5f)
                    }
                    Box(
                        modifier = Modifier
                            .padding(horizontal = rememberOuterHorizontalPaddingSmall())
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(color)
                            .debugUi()
                    )
                }
            }
        }
    }
}

data class SalaryEarnedData(
    val date: String,
    val salary: String,
)

fun LazyListScope.attendanceTabContent(
    listAttendanceContent: List<KpiData> = emptyList(),
    onClockIn: () -> Unit = {}
) {
        item {
            TitleWithMoreRow(
                title = "Logout History",
                onSeeAllClick = {},
                modifier = Modifier.padding(horizontal = rememberOuterHorizontalPaddingLarge())
            )
        }

    item {
        SpacerVerticalLarge()
    }

    item{
        InfoCard(
            label = "Last Logged out at",
            value = "21 Sept 2025",
            icon = Res.drawable.ic_exit,
            modifier = Modifier.padding(horizontal = rememberOuterHorizontalPaddingLarge()),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        SpacerVerticalMedium()
    }
    item {
        BasicText(
            text = "Note: if your login hours are fewer than 8, Your salary will not be updated for withdrawal",
            style = rememberTextStyleSmall(fontWeight = FontWeight.Medium).copy(
                color = Color.Gray,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .padding(horizontal = rememberOuterHorizontalPaddingExtraLarge(), vertical = rememberOuterVerticalPaddingMedium())
                .fillMaxWidth()
        )

    }
    item {

            SlideToClockIn(
                onClockIn = onClockIn
            )
    }
        item {
            SpacerVerticalMedium()
        }


        item(key = "attendance_spacer") {
            SpacerVerticalExtraLarge()
            SpacerVerticalExtraLarge()
            SpacerVerticalExtraLarge()
            SpacerVerticalExtraLarge()
            SpacerVerticalExtraLarge()
            SpacerVerticalExtraLarge()
            SpacerVerticalExtraLarge()
            SpacerVerticalExtraLarge()
            SpacerVerticalExtraLarge()
            SpacerVerticalExtraLarge()
        }
}

@Composable
fun TitleWithMoreRow(
    modifier: Modifier = Modifier,
    title: String,
    titleMore: String? = null,
    onSeeAllClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .debugUi(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicText(
            text = title,
            modifier = Modifier
                .padding(horizontal = rememberPaddingMedium()),
            style = rememberTextStyleLarge(fontWeight = FontWeight.Bold).copy(color = MaterialTheme.colorScheme.onSurface)
        )
        titleMore?.let {
            BasicText(
                text = it,
                style = rememberTextStyleMedium(fontWeight = FontWeight.Bold).copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onSeeAllClick() }
                    .padding(horizontal = rememberPaddingMedium())
            )
        } ?: run {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onSeeAllClick() }
            )
        }
    }
}

data class KpiData(
    val title: String,
    val content: String,
)

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    icon: DrawableResource? = null,
    tag: String? = null,
    color: Color,
    elevation: Dp = 0.dp,
    borderStroke: Dp = 0.dp,
    borderColor: Color = Color.Transparent,
    backgroundColor: Color = Color.Transparent,
    ) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .debugUi(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = rememberCornerRadiusMedium(),
        border = CardDefaults.outlinedCardBorder(
            enabled = borderStroke> 0.dp,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = elevation)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .debugUi(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f).debugUi()
            ) {

                icon?.let { drawableResource ->
                    Box(
                        modifier = Modifier
                            .padding(rememberPaddingMedium())
                            .size(rememberSizeLarge())
                            .clip(RoundedCornerShape(16.dp))
                            .background(
                                color.copy(alpha = .15f)
                            )
                            .innerShadow(
                                shape = CircleShape,
                                shadow = Shadow(
                                    offset = DpOffset(0.dp, 0.dp),
                                    radius = 4.dp,
                                    spread = 2.dp,
                                    color = Color(0x33000000)
                                )
                            )
                            .debugUi(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(drawableResource),
                            contentDescription = null,
                            tint = color,
                            modifier = Modifier.size(rememberSizeSmall())
                        )
                    }

                    SpacerHorizontalMedium()
                }

                Column(
                    modifier = Modifier.debugUi(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    BasicText(
                        text = label,
                        style = rememberTextStyleMedium(fontWeight = FontWeight.Bold)
                            .copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = .8f))
                    )
                    BasicText(
                        text = value,
                        style = rememberTextStyleSmall(fontWeight = FontWeight.Medium)
                            .copy(color = Color.Gray)
                    )
                }
            }
            tag?.let { text ->

            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.secondary, shape = RoundedCornerShape(
                        topStart = rememberCornerRadiusMediumDp(),
                        bottomStart = rememberCornerRadiusMediumDp()
                    ))
                    .clip(
                        RoundedCornerShape(
                            topStart = rememberCornerRadiusMediumDp(),
                            bottomStart = rememberCornerRadiusMediumDp()
                        )
                    )
                    .innerShadow(
                        shape = RoundedCornerShape(
                            topStart = rememberCornerRadiusMediumDp(),
                            bottomStart = rememberCornerRadiusMediumDp()
                        ),
                        shadow = Shadow(
                            offset = DpOffset(0.dp, 0.dp),
                            radius = 4.dp,
                            spread = 2.dp,
                            color = Color(0x33000000)
                        )
                    )
                    .debugUi()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = rememberOuterHorizontalPaddingLarge()),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "RM $text",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.surface,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            }
        }
    }
}
@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    icon: DrawableResource,
    date: String,
    label: String,
) {
    Card(
        modifier = modifier.fillMaxWidth().debugUi(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = rememberCornerRadiusLarge(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = rememberOuterHorizontalPaddingLarge(),
                    vertical = rememberOuterVerticalPaddingLarge()
                )
                .debugUi(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.debugUi()) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            MaterialTheme.colorScheme.secondary.copy(alpha = .15f)
                        )
                        .debugUi(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                SpacerHorizontalMedium()
                Column(
                    modifier = Modifier.debugUi(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    BasicText(
                        text = label,
                        style = rememberTextStyleLarge(fontWeight = FontWeight.Bold).copy(color = MaterialTheme.colorScheme.onSurface)
                    )
                    BasicText(
                        text = date,
                        style = rememberTextStyleSmall(fontWeight = FontWeight.Medium).copy(color = Color.Gray)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {

    MyMaterialTheme {

    HomeScreen(
        PaddingValues(0.dp),
        UserDetails(
            name = "John Doe",
            email = ""
        ),
        onClockIn = {}
    )
    }
}

data class WithdrawData(
    val amount: String,
    val month: String,
)