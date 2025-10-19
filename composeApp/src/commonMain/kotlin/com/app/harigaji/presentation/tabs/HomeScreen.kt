package com.app.harigaji.presentation.tabs

import SalaryWithdrawCard
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.FloatingActionButtonDefaults.elevation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.core.user.UserDetails
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_calender
import harigaji.composeapp.generated.resources.ic_clock
import harigaji.composeapp.generated.resources.ic_message_dot
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

private val listOfTabOptions = listOf(
    _root_ide_package_.com.app.harigaji.presentation.OptionTab(
        id = "1",
        name = "Salary",
        icon = "ic_launcher_foreground",
        color = "#FF0000"
    ),
    _root_ide_package_.com.app.harigaji.presentation.OptionTab(
        id = "2",
        name = "Attendance",
        icon = "ic_launcher_foreground",
        color = "#00FF00"
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    userDetails: UserDetails,
    onClockIn: () -> Unit,
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

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState()
    )

    val cornerRadius by remember {
        derivedStateOf {
            val fraction = scrollBehavior.state.collapsedFraction
            // Interpolate from 48.dp to 12.dp based on scroll
            (48 - (36 * fraction)).dp.coerceAtLeast(24.dp)
        }
    }

    val listWithdraw = listOf(
        WithdrawData("RM 12,000", "October"),
        WithdrawData("RM 10,000", "September"),
        WithdrawData("RM 8,000", "August"),
        WithdrawData("RM 15,000", "July"),
        WithdrawData("RM 9,500", "June"),
    )

    val colors = listOf(
        Color(0xFFF48FB1),
        Color(0xFF81D4FA),
        Color(0xFF4DD0E1)
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(
                    defaultElevation = 8.dp,
                ),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(bottomStart = cornerRadius, bottomEnd = cornerRadius)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TopAppBar(
                        scrollBehavior = scrollBehavior,
                        windowInsets = WindowInsets
                            .systemBars
                            .only(WindowInsetsSides.Horizontal + WindowInsetsSides.Top),
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color.Transparent,
                            scrolledContainerColor = Color.Transparent,
                        ),
                        title = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Start,
                                modifier = Modifier.fillMaxWidth().padding(end = 16.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Icon(
                                        painter = painterResource(Res.drawable.ic_calender),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    _root_ide_package_.com.app.harigaji.presentation.DateRow()
                                }

                                OutlinedIconButton(
                                    onClick = {},
                                    modifier = Modifier.padding(4.dp)
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
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = if (scrollBehavior.state.collapsedFraction == 0f) {
                                    4.dp
                                } else 16.dp,
                                bottom = 8.dp,
                            ),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(48.dp)
                                    .align(Alignment.Center)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = "Hi, ${userDetails.name ?: ""} !",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier
                                    .background(
                                        shape = CircleShape,
                                        color = MaterialTheme.colorScheme.secondary.copy(
                                            alpha = .15f
                                        )
                                    )
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Pro Member",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    // Search bar with smooth fade and slide animation
                    AnimatedVisibility(
                        visible = scrollBehavior.state.collapsedFraction < 0.5f,
                        enter = fadeIn() + expandVertically(),
                        exit = fadeOut() + shrinkVertically()
                    ) {
                        _root_ide_package_.com.app.harigaji.presentation.SearchBarM3(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 8.dp)
                                .graphicsLayer {
                                    alpha =
                                        1f - (scrollBehavior.state.collapsedFraction * 2).coerceIn(
                                            0f,
                                            1f
                                        )
                                },
                            onSearch = {},
                            onItemClick = {},
                            searchHeading = "Search for anything",
                        )
                    }
                }
            }
        }
    ) { pv ->
        val lazyRowState = rememberLazyListState()
        val currentIndex by remember {
            derivedStateOf {
                val layoutInfo = lazyRowState.layoutInfo
                val visibleItems = layoutInfo.visibleItemsInfo
                if (visibleItems.isEmpty()) 0
                else {
                    val viewportCenter = layoutInfo.viewportStartOffset + layoutInfo.viewportSize.width / 2
                    visibleItems.minByOrNull { item ->
                        kotlin.math.abs((item.offset + item.size / 2) - viewportCenter)
                    }?.index ?: 0
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .padding(pv)
                .background(MaterialTheme.colorScheme.background)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            item {
                SalaryWithdrawCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                _root_ide_package_.com.app.harigaji.presentation.OptionTabs(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
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
                    selectedOptionTextColor = MaterialTheme.colorScheme.onSurface
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }

            // HorizontalPager for tab content
            item {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(700.dp)
                ) { page ->
                    when (page) {
                        0 -> {
                            // Salary Tab Content
                            SalaryTabContent(
                                listWithdraw = listWithdraw,
                                colors = colors,
                                lazyRowState = lazyRowState,
                                currentIndex = currentIndex
                            )
                        }
                        1 -> {
                            // Attendance Tab Content
                            AttendanceTabContent(
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

            item {
                Spacer(modifier = Modifier.height(150.dp))
            }
        }
    }
}

@Composable
fun SalaryTabContent(
    listWithdraw: List<WithdrawData>,
    colors: List<Color>,
    lazyRowState: androidx.compose.foundation.lazy.LazyListState,
    currentIndex: Int
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        TitleWithMoreRow(
            title = "My Withdraw",
            onSeeAllClick = {

            },
            titleMore = "See All"
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Withdraw Cards
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = lazyRowState,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            itemsIndexed(listWithdraw) { index, item ->
                val color = colors[index % colors.size]
                _root_ide_package_.com.app.harigaji.presentation.WithdrawCard(
                    amount = item.amount,
                    month = item.month,
                    color = color,
                    modifier = Modifier
                        .width(250.dp)
                        .height(IntrinsicSize.Max)
                )
            }
        }

        // Indicators
        if (listWithdraw.size > 1) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp),
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
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TitleWithMoreRow(
            title = "Salary Earned",
            onSeeAllClick = {

            }
        )

        Icon(
            painter = painterResource(Res.drawable.ic_message_dot),
            contentDescription = "message salary",
            tint = Color.Gray,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .size(20.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Salary Earned Cards
        Column(modifier = Modifier.fillMaxWidth()) {
            SalaryEarnedCard(date = "16 Oct 2025", salary = "2,500")
            Spacer(modifier = Modifier.height(8.dp))
            SalaryEarnedCard(date = "15 Oct 2025", salary = "3,200")
            Spacer(modifier = Modifier.height(8.dp))
            SalaryEarnedCard(date = "14 Oct 2025", salary = "1,800")
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun AttendanceTabContent(
    listAttendanceContent:List<KpiData> = emptyList(),
    onClockIn: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {if(listAttendanceContent.isEmpty()){
        Icon(
            imageVector = Icons.Default.DateRange,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Attendance",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Attendance tracking content goes here",
            fontSize = 16.sp,
            color = Color.Gray
        )
    }else{

        TitleWithMoreRow(
            title = "Logout History",
            onSeeAllClick = {

            }
        )

        listAttendanceContent.forEachIndexed {
            index, kpiData ->
            InfoCard(
                icon = when(index){
                    0-> Res.drawable.ic_clock
                    else-> Res.drawable.ic_calender
                },
                date = kpiData.value,
                label = kpiData.label,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        Text(
            text = "Note: if your ogin hours are fewer than 8, Your salary will not be updated for withdrawal",
            fontSize = 14.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).fillMaxWidth()
        )
        _root_ide_package_.com.app.harigaji.presentation.SlideToClockIn(
            onClockIn = onClockIn
        )

    }
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
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        titleMore?.let {
            Text(
                text = it,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { onSeeAllClick() }
            )
        }?:run{
        Icon(Icons.Default.MoreVert, contentDescription = null, modifier = Modifier
            .padding(4.dp)
            .clickable { onSeeAllClick() }
        )
        }
    }
}


data class KpiData(
    val label:String,
    val value:String,
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
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            MaterialTheme.colorScheme.secondary.copy(alpha = .15f)
                        ),
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
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = "Salary earned",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = date,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                }
            }
            Text(
                text = "RM $salary",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = .8f)
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
        modifier = modifier
            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
        ,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
            ,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            MaterialTheme.colorScheme.secondary.copy(alpha = .15f)
                        ),
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
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = label,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = date,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
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