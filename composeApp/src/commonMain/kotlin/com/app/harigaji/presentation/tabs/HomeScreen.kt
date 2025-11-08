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
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.core.user.UserDetails
import com.app.harigaji.presentation.DateRow
import com.app.harigaji.presentation.OptionTab
import com.app.harigaji.presentation.OptionTabs
import com.app.harigaji.presentation.SearchBarM3
import com.app.harigaji.presentation.SlideToClockIn
import com.app.harigaji.presentation.WithdrawCard
import com.app.harigaji.theme.SpacerHorizontalMedium
import com.app.harigaji.theme.SpacerHorizontalSmall
import com.app.harigaji.theme.rememberTextSizeLarge
import com.app.harigaji.theme.rememberHorizontalPaddingMedium
import com.app.harigaji.theme.rememberHorizontalPaddingLarge
import com.app.harigaji.theme.rememberCornerRadiusLarge
import com.app.harigaji.theme.rememberHorizontalPaddingSmall
import com.app.harigaji.theme.rememberInnerVerticalPaddingSmall

import com.app.harigaji.theme.rememberOuterHorizontalPaddingSmall
import com.app.harigaji.theme.rememberOuterVerticalPaddingSmall
import com.app.harigaji.theme.rememberPaddingLarge
import com.app.harigaji.theme.rememberPaddingMedium
import com.app.harigaji.theme.rememberVerticalPaddingSmall
import com.app.harigaji.theme.debugUi
import com.app.harigaji.theme.rememberOuterHorizontalPaddingLarge
import com.app.harigaji.theme.rememberOuterHorizontalPaddingMedium
import com.app.harigaji.theme.rememberSizeExtraLarge
import com.app.harigaji.theme.rememberSizeLarge
import com.app.harigaji.theme.rememberSizeMedium
import com.app.harigaji.theme.rememberTextSizeExtraLarge
import com.app.harigaji.theme.rememberTextSizeMedium
import com.app.harigaji.theme.rememberTextSizeSmall
import com.app.harigaji.theme.rememberTextSizeTitle
import com.app.harigaji.theme.rememberTextStyleLarge
import com.app.harigaji.theme.rememberTextStyleSmall
import com.app.harigaji.theme.rememberTextStyleMedium
import com.app.harigaji.theme.rememberTextStyleExtraLarge
import com.app.harigaji.theme.rememberTextStyleTitle
import com.app.harigaji.theme.rememberVerticalSpacingSmall
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
//                 verticalArrangement = Arrangement.spacedBy(rememberVerticalPaddingSmall())
            ) {
                stickyHeader(key = "user_header") {
                    UserHeaderCard(
                        userDetails = userDetails,
                        collapseFraction = collapseFraction
                    )
                }

                item(key = "search_section") {
                    SearchSection(
                        cornerRadius = cornerRadius,
                        collapseFraction = collapseFraction
                    )
                }

                item(key = "salary_card") {
                    val paddingLarge = rememberHorizontalPaddingLarge()
                    val paddingMedium = rememberPaddingMedium()
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
                    val paddingLarge = rememberHorizontalPaddingLarge()
                    Spacer(modifier = Modifier.height(paddingLarge))
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
                                    label = "Last Logged out at",
                                    value = "21 Jan 2024"
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
                .padding(horizontal = rememberOuterHorizontalPaddingSmall())
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
    collapseFraction: Float
) {
      // Use Surface instead of Card for better performance
      Surface(
         modifier = Modifier.fillMaxWidth().debugUi(),
          color = MaterialTheme.colorScheme.surface
      ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = rememberOuterHorizontalPaddingLarge())
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
                        style = rememberTextStyleLarge(fontWeight = FontWeight.Bold).copy(color = Color.Black),
                        maxLines = 1,
                        modifier = Modifier.basicMarquee()
                    )
                    Row(
                        modifier = Modifier
                            .background(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.secondary.copy(alpha = .15f)
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                            .debugUi(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        BasicText(
                            text = "Pro Member",
                            style = rememberTextStyleSmall(fontWeight = FontWeight.Medium).copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
                        )
                    }
                }
                OutlinedIconButton(
                    onClick = {},
                    modifier = Modifier.padding(0.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_message_dot),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(26.dp).padding(4.dp)
                    )
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
                    .padding(horizontal = rememberPaddingLarge())
                    .padding(bottom = 8.dp)
                    .graphicsLayer {
                        alpha = 1f - (collapseFraction * 2).coerceIn(0f, 1f)
                    },
                onSearch = {},
                onItemClick = {},
                searchHeading = "Search for anything",
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
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
                .padding(4.dp),
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
    )

    item(key = "withdraw_title") {
        TitleWithMoreRow(
            title = "My Withdraw",
            onSeeAllClick = {},
            titleMore = "See All"
        )
    }

    item(key = "withdraw_carousel") {
        WithdrawCarousel(
            listWithdraw = listWithdraw,
            colors = colors,
            lazyRowState = lazyRowState,
            currentIndex = currentIndex
        )
    }

    item(key = "salary_earned_header") {
        Spacer(modifier = Modifier.height(8.dp))
        TitleWithMoreRow(
            title = "Salary Earned",
            onSeeAllClick = {}
        )

        Icon(
            painter = painterResource(Res.drawable.ic_message_dot),
            contentDescription = "message salary",
            tint = Color.Gray,
            modifier = Modifier
                .padding(start = 16.dp, bottom = 8.dp)
                .size(20.dp)
        )
    }

    items(
        items = listSalaryEarned,
        key = { it.date }
    ) { item ->
        SalaryEarnedCard(date = item.date, salary = item.salary)
        Spacer(modifier = Modifier.height(8.dp))
    }

    item(key = "bottom_spacer") {
        Spacer(modifier = Modifier.height(100.dp))
    }
}

@Composable
private fun WithdrawCarousel(
    listWithdraw: List<WithdrawData>,
    colors: List<Color>,
    lazyRowState: LazyListState,
    currentIndex: Int
) {
    Column(modifier = Modifier.debugUi()) {
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth().debugUi(),
            state = lazyRowState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
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
                        .width(200.dp)
                        .height(IntrinsicSize.Max)
                )
            }
        }

        if (listWithdraw.size > 1) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp)
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
                            .padding(horizontal = 4.dp)
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
    item(key = "attendance_content") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .debugUi(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            if (listAttendanceContent.isEmpty()) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(16.dp))
                BasicText(
                    text = "Attendance",
                    style = rememberTextStyleTitle(fontWeight = FontWeight.Bold)
                )
                Spacer(modifier = Modifier.height(8.dp))
                BasicText(
                    text = "Attendance tracking content goes here",
                    style = rememberTextStyleLarge().copy(color = Color.Gray)
                )
            } else {
                TitleWithMoreRow(
                    title = "Logout History",
                    onSeeAllClick = {}
                )

                listAttendanceContent.forEachIndexed { index, kpiData ->
                    InfoCard(
                        icon = when (index) {
                            0 -> Res.drawable.ic_exit
                            else -> Res.drawable.ic_calender
                        },
                        date = kpiData.value,
                        label = kpiData.label,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                BasicText(
                    text = "Note: if your login hours are fewer than 8, Your salary will not be updated for withdrawal",
                    style = rememberTextStyleMedium(fontWeight = FontWeight.Medium).copy(
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .fillMaxWidth()
                )
                SlideToClockIn(
                    onClockIn = onClockIn
                )
            }
        }
    }

    item(key = "attendance_spacer") {
        Spacer(modifier = Modifier.height(400.dp))
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
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .debugUi(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicText(
            text = title,
            style = rememberTextStyleExtraLarge(fontWeight = FontWeight.Bold).copy(color = MaterialTheme.colorScheme.onSurface)
        )
        titleMore?.let {
            BasicText(
                text = it,
                style = rememberTextStyleSmall(fontWeight = FontWeight.Medium).copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clip(CircleShape)
                    .clickable { onSeeAllClick() }
                    .padding(horizontal = rememberPaddingLarge())
            )
        } ?: run {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .clip(CircleShape)
                    .clickable { onSeeAllClick() }
            )
        }
    }
}

data class KpiData(
    val label: String,
    val value: String,
)

@Composable
fun SalaryEarnedCard(
    modifier: Modifier = Modifier,
    date: String,
    salary: String,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .debugUi(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = rememberCornerRadiusLarge(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
                        painter = painterResource(Res.drawable.ic_clock),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier.debugUi(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    BasicText(
                        text = "Salary earned",
                        style = rememberTextStyleLarge(fontWeight = FontWeight.Bold).copy(color = MaterialTheme.colorScheme.onSurface)
                    )
                    BasicText(
                        text = date,
                        style = rememberTextStyleSmall(fontWeight = FontWeight.Medium).copy(color = Color.Gray)
                    )
                }
            }
            BasicText(
                text = "RM $salary",
                style = rememberTextStyleExtraLarge(fontWeight = FontWeight.Bold).copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = .8f))
            )
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
                .padding(16.dp)
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
                Spacer(modifier = Modifier.width(12.dp))
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
    HomeScreen(
        PaddingValues(0.dp),
        UserDetails(
            name = "John Doe",
            email = ""
        ),
        onClockIn = {}
    )
}

data class WithdrawData(
    val amount: String,
    val month: String,
)