package com.app.harigaji.presentation.tabs

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.app.harigaji.presentation.OptionTab
import com.app.harigaji.presentation.OptionTabs
import com.app.harigaji.presentation.ScreenHeader
import com.app.harigaji.presentation.TransactionItem
import com.app.harigaji.theme.SpacerVerticalMedium
import com.app.harigaji.theme.rememberOuterHorizontalPaddingExtraLarge
import com.app.harigaji.theme.rememberOuterHorizontalPaddingLarge
import com.app.harigaji.theme.rememberOuterHorizontalPaddingMedium
import com.app.harigaji.theme.rememberOuterVerticalPaddingMedium
import kotlinx.coroutines.launch

// History Screen

private val listOfTabOptions = listOf(
    OptionTab(
        id = "1",
        name = "All",
        icon = "ic_launcher_foreground",
        color = "#FF0000"
    ),
    OptionTab(
        id = "2",
        name = "Pending",
        icon = "ic_launcher_foreground",
        color = "#00FF00"
    ),
    OptionTab(
        id = "3",
        name = "Successful",
        icon = "ic_launcher_foreground",
        color = "#0000FF"
    ),
    OptionTab(
        id = "4",
        name = "Failed",
        icon = "ic_launcher_foreground",
        color = "#FFFF00"
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    paddingValues: PaddingValues,
) {

    val listTransections = listOf(
        TransactionDetails(
            id = "T001",
            name = "John Doe",
            bank = "HDFC Bank",
            dateTime = "2024-10-01 | 10:30 AM",
            amount = 150.0,
            status = "Successful"
        ),
        TransactionDetails(
            id = "T002",
            name = "ABC Store",
            bank = "ICICI Bank",
            dateTime = "2024-10-02 | 02:15 PM",
            amount = 75.5,
            status = "Pending"
        ),
        TransactionDetails(
            id = "T003",
            name = "Netflix",
            bank = "SBI",
            dateTime = "2024-10-03 | 09:00 AM",
            amount = 20.0,
            status = "Failed"
        ),
        TransactionDetails(
            id = "T004",
            name = "Jane Smith",
            bank = "Axis Bank",
            dateTime = "2024-10-04 | 11:45 AM",
            amount = 200.0,
            status = "Successful"
        ),
        TransactionDetails(
            id = "T005",
            name = "XYZ Services",
            bank = "HDFC Bank",
            dateTime = "2024-10-05 | 03:30 PM",
            amount = 50.0,
            status = "Pending"
        ),
        TransactionDetails(
            id = "T006",
            name = "Amazon",
            bank = "ICICI Bank",
            dateTime = "2024-10-06 | 08:20 PM",
            amount = 120.0,
            status = "Successful"
        ),
        TransactionDetails(
            id = "T001",
            name = "John Doe",
            bank = "HDFC Bank",
            dateTime = "2024-10-01 | 10:30 AM",
            amount = 150.0,
            status = "Successful"
        ),
        TransactionDetails(
            id = "T002",
            name = "ABC Store",
            bank = "ICICI Bank",
            dateTime = "2024-10-02 | 02:15 PM",
            amount = 75.5,
            status = "Pending"
        ),
        TransactionDetails(
            id = "T003",
            name = "Netflix",
            bank = "SBI",
            dateTime = "2024-10-03 | 09:00 AM",
            amount = 20.0,
            status = "Failed"
        ),
        TransactionDetails(
            id = "T004",
            name = "Jane Smith",
            bank = "Axis Bank",
            dateTime = "2024-10-04 | 11:45 AM",
            amount = 200.0,
            status = "Successful"
        ),
        TransactionDetails(
            id = "T005",
            name = "XYZ Services",
            bank = "HDFC Bank",
            dateTime = "2024-10-05 | 03:30 PM",
            amount = 50.0,
            status = "Pending"
        ),
        TransactionDetails(
            id = "T006",
            name = "Amazon",
            bank = "ICICI Bank",
            dateTime = "2024-10-06 | 08:20 PM",
            amount = 120.0,
            status = "Successful"
        )
    )

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
            Column (
//                modifier = Modifier.fillMaxWidth().padding(top = paddingValues.calculateTopPadding())
            ){
                ScreenHeader(
                    title = "History",
                    modifier = Modifier.padding(horizontal = rememberOuterHorizontalPaddingExtraLarge()),
                    paddingValues = paddingValues,
                    horizontalPadding = rememberOuterHorizontalPaddingLarge(),
                    onTrailingClick = {}
                )
                SpacerVerticalMedium()
                OptionTabs(
                    height = 48.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 0.dp),
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
                    selectedOptionTextColor = when(pagerState.currentPage){
                        0 -> MaterialTheme.colorScheme.onBackground
                        1 -> Color(0xFFFFA500)
                        2 -> MaterialTheme.colorScheme.secondary
                        3 -> MaterialTheme.colorScheme.error
                        else -> Color.Blue
                    }
                )
            }
        }
    ) { pv ->
        Box(
            modifier = Modifier
                .padding(pv)
        ){
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                    ) { page ->
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                                .padding(horizontal = 8.dp)
                        ) {
                            when (page) {
                                0 -> {
                                    pageTransections(
                                        transactions = listTransections
                                    )
                                }

                                1 -> {
                                    pageTransections(
                                        transactions = listTransections.filter { it.status == "Pending" }
                                    )
                                }

                                2 -> {
                                    pageTransections(
                                        transactions = listTransections.filter { it.status == "Successful" }
                                    )
                                }

                                3 -> {
                                    pageTransections(
                                        transactions = listTransections.filter { it.status == "Failed" }
                                    )
                                }
                            }
                        }
            }
        }
    }
}

fun LazyListScope.pageTransections(
    modifier: Modifier = Modifier,
    transactions: List<TransactionDetails>
) {
    item {
        SpacerVerticalMedium()
    }
    items(transactions) {
        TransactionItem(
            label = it.name,
            value = it.bank,
            subValue = it.dateTime,
            amount = it.amount.toString(),
            status = it.status,
            modifier = Modifier.padding(horizontal = rememberOuterHorizontalPaddingMedium(), vertical = rememberOuterVerticalPaddingMedium()),
            statusColor = when (it.status) {
                "Successful" -> MaterialTheme.colorScheme.secondary
                "Pending" -> Color(0xFFFFA500)
                "Failed" -> MaterialTheme.colorScheme.error
                else -> Color.Gray
            }
        )
    }
    item{
        Spacer(modifier = Modifier.height(180.dp))
    }
}

data class TransactionDetails(
    val id: String,
    val name: String,
    val bank: String,
    val dateTime: String,
    val amount: Double,
    val status: String
)