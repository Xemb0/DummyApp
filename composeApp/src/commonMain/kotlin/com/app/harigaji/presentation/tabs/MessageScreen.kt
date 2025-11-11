package com.app.harigaji.presentation.tabs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.app.harigaji.chat.UserMessageDetails
import com.app.harigaji.presentation.MessageItem
import com.app.harigaji.presentation.ScreenHeader
import com.app.harigaji.theme.debugUi
import com.app.harigaji.theme.rememberOuterHorizontalPaddingExtraLarge
import com.app.harigaji.theme.rememberOuterHorizontalPaddingLarge
import com.app.harigaji.theme.rememberTextStyleHeadline
import com.app.harigaji.theme.rememberTextStyleMedium
import com.app.harigaji.theme.rememberTextStyleSmall

// Message Screen
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MessageScreen(
    paddingValues: PaddingValues,
    onChatMessageClick: (id:Int) -> Unit = {},
    onPrevious: () -> Unit = {},
    listUserMessages: List<UserMessageDetails>
){



    Scaffold(topBar = {
        Column (

        ) {
            ScreenHeader(
                title = "Messages",
                onTrailingClick = {},
                paddingValues = paddingValues,
                modifier = Modifier.padding(horizontal = rememberOuterHorizontalPaddingExtraLarge())
            )
        }
    }){pv ->
        Box(modifier = Modifier.padding(pv).debugUi()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()

                    .background(MaterialTheme.colorScheme.background)
                    .debugUi(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                val sortedByDate = listUserMessages.groupBy { it.date }

                sortedByDate.entries.forEach { entry ->
                    val date = entry.key
                    val messages = entry.value

                    // Date header
                    item(key = "header_$date") {
                        DateHeader(date = date, modifier = Modifier.padding(horizontal = rememberOuterHorizontalPaddingExtraLarge()).debugUi())
                    }

                    // Messages for this date
                    items(
                        items = messages,
                        key = { it.id }
                    ) { userMessage ->
                        MessageItem(
                            sender = userMessage.sender,
                            message = userMessage.message,
                            time = userMessage.time,
                            onClick = { onChatMessageClick(userMessage.id) }
                        )
                    }
                }

                item {
                    Spacer(modifier = Modifier.size(200.dp))
                }
            }


        }
    }
}

@Composable
fun DateHeader(date: String?,modifier: Modifier) {
    if (date == null) return
    BasicText(
        modifier = modifier,
        text = date,
        style = rememberTextStyleSmall().copy(
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .8f)
        ),
    )
}
