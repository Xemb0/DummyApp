package com.app.harigaji.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// History Screen
@Composable
fun HistoryScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("All", "Pending", "Successful", "Failed")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "History",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        }

        // Tabs
        ScrollableTabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.White,
            edgePadding = 16.dp
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Transaction List
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TransactionItem(
                name = "Mr. Warner Lobodile",
                bank = "Malayan Bank Berhad",
                date = "Dec 24, 2024 | 14:26:34 PM",
                amount = "RM 600",
                status = "Failed",
                statusColor = Color(0xFFEF5350)
            )
            
            TransactionItem(
                name = "Mr. Warner Lobodile",
                bank = "Malayan Bank Berhad",
                date = "Dec 24, 2024 | 14:26:34 PM",
                amount = "RM 600",
                status = "Approved",
                statusColor = Color(0xFF66BB6A)
            )
            
            TransactionItem(
                name = "Mr. Warner Lobodile",
                bank = "Malayan Bank Berhad",
                date = "Dec 24, 2024 | 14:26:34 PM",
                amount = "RM 600",
                status = "Approved",
                statusColor = Color(0xFF66BB6A)
            )
            
            TransactionItem(
                name = "Mr. Warner Lobodile",
                bank = "Malayan Bank Berhad",
                date = "Dec 24, 2024 | 14:26:34 PM",
                amount = "RM 600",
                status = "Approved",
                statusColor = Color(0xFF66BB6A)
            )
        }
    }
}

