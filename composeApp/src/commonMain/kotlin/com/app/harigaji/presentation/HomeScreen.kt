package com.app.harigaji.presentation

import SalaryWithdrawCard
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.OutlinedTextFieldDefaults.contentPadding
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.core.user.UserDetails
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_calender
import harigaji.composeapp.generated.resources.ic_clock
import harigaji.composeapp.generated.resources.ic_message
import harigaji.composeapp.generated.resources.ic_message_dot
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

val listOfTabOptions = listOf(
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
// Home Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    userDetails: UserDetails,
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


    Scaffold(
        topBar = {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp, vertical = 0 .dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_calender),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        DateRow()
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(Color.Gray)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .size(24.dp)
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
                                text = "Hi, ${userDetails.name ?: ""}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(
                                modifier = Modifier.background(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .15f)
                                ).padding(horizontal = 4.dp),
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
                    SearchBarM3(
                        onSearch = {},
                        onItemClick = {},
                        searchHeading = "Search for anything",
                    )

                }
            }

        }
    ) { pv ->
        Box(
            modifier = Modifier.padding(pv).background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                SalaryWithdrawCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))
                OptionTabs(
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
                    options = listOfTabOptions
                )


                Spacer(modifier = Modifier.height(16.dp))

                // My Withdraw Section
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "My Withdraw",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(onClick = { }) {
                        Text("See All", color = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Withdraw Cards
                val listWithdraw = listOf(
                    WithdrawData("RM 12,000", "October"),
                    WithdrawData("RM 10,000", "September"),
                    WithdrawData("RM 8,000", "August")
                )

                val colors = listOf(
                    Color(0xFFF48FB1), // Darker Pink
                    Color(0xFF81D4FA), // Darker Light Blue
                    Color(0xFF4DD0E1)  // Darker Cyan
                )

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(end = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    state = remember { LazyListState() }
                ) {
                    itemsIndexed(listWithdraw) { index, item ->
                        val color = colors[index % colors.size]

                        Spacer(modifier = Modifier.width(if (index == 0) 16.dp else 4.dp))
                        WithdrawCard(
                            amount = item.amount,
                            month = item.month,
                            color = color,
                            modifier = Modifier
                                .width(300.dp)
                                .height(IntrinsicSize.Max)
                                .padding(end = 16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Column {


                    // Salary Earn Section
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Salary Earn",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                            Icon(Icons.Default.MoreVert, contentDescription = null)


                    }

                        Icon(
                            painter = painterResource(Res.drawable.ic_message_dot),
                            contentDescription = "message salary",
                            tint = Color.Gray,
                            modifier = Modifier.padding(horizontal = 16.dp).size(20.dp)
                        )


                    Spacer(modifier = Modifier.height(8.dp))

                    // Salary Earned Card
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(
                                            MaterialTheme.colorScheme.secondary.copy(
                                                alpha = .15f
                                            )
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
                                    color =MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.Bold
                                )
                                    Text(
                                        text = "21 Jan 2024",
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                            Text(
                                text = "RM 50,000",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(150.dp))
                }
            }

        }
    }

}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(PaddingValues(0.dp), UserDetails(
        name = "John Doe",
        email = ""
    ))
}

data class WithdrawData(
    val amount: String,
    val month: String,
)