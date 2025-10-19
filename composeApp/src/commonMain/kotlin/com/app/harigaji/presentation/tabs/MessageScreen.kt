package com.app.harigaji.presentation.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.IconButton
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.app.harigaji.presentation.MessageItem

// Message Screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(paddingValues: PaddingValues) {

    val listMessageDetails = listOf(
        MessageDetails(
            sender = "HariGaji Admin",
            message = "Earned Salary Updated: RM 5000",
            time = "12:48PM",
            date = "January, 2024"
        ),
        MessageDetails(
            sender = "HariGaji Admin",
            message = "Earned Salary Updated: RM 5000",
            time = "12:48PM",
            date = "January, 2024"
        ),
        MessageDetails(
            sender = "HariGaji Admin",
            message = "Earned Salary Updated: RM 5000",
            time = "12:48PM",
            date = "February, 2024"
        ),
    )

    Scaffold(topBar = {
        Column (
            modifier = Modifier.fillMaxWidth().padding(paddingValues)
        ) {
            TopAppBar(
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
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
                    ) {

                        Text(
                            "Messages",
                            style = MaterialTheme.typography.headlineSmall,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        )

                        OutlinedIconButton(
                            onClick = {},
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(46.dp).padding(4.dp)
                            )
                        }
                    }
                }
            )
        }
    }){pv ->
        Box(modifier = Modifier.padding(pv)) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                val sortedByDate = listMessageDetails.groupBy { it.date }

                sortedByDate.forEach { (date, messages) ->
                    item {
                        Text(
                            text = date?: "",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    items(messages) { messageDetail ->
                        MessageItem(
                            sender = messageDetail.sender,
                            message = messageDetail.message,
                            time = messageDetail.time,
                            date = messageDetail.date
                        )
                    }
                }
            }
        }
    }
}

data class MessageDetails(
    val sender: String,
    val message: String,
    val time: String,
    val date: String?
)