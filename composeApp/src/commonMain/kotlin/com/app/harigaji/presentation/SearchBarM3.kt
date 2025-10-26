package com.app.harigaji.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_message_dot
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarM3(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    onItemClick: (String) -> Unit,
    searchHeading: String = "Recent search",
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    // Sample search history - replace with your actual data
    val searchHistory = remember {
        listOf("Recent 1", "Recent 2", "Recent 3")
    }

    SearchBarDefaults.inputFieldColors(
        cursorColor = MaterialTheme.colorScheme.surfaceVariant
    )
    DockedSearchBar(

        modifier = modifier
            .wrapContentHeight(),
        inputField = {
            SearchBarDefaults.InputField(
                query = query,
                onQueryChange = { newQuery ->
                    query = newQuery
                    if (newQuery.isNotEmpty()) {
                        onSearch(newQuery)
                    }
                },
                onSearch = { searchQuery ->
                    onSearch(searchQuery)
                    active = false // Close after search
                },
                expanded = active,
                onExpandedChange = { isActive ->
                    active = isActive
                },
                placeholder = {
                    Text(
                        text = searchHeading,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                leadingIcon = {
                    if (active) {
                        IconButton(
                            onClick = {
                                if (query.isNotEmpty()) {
                                    query = ""
                                } else {
                                    active = false
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                trailingIcon = {
                    if (!active) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
            )
        },
        expanded = active,
        onExpandedChange = { isActive ->
            active = isActive
        },
        shape = SearchBarDefaults.dockedShape,
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f),
            dividerColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        content = {
            // Search results content - CRITICAL: Limited height
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp) // CRITICAL: Limit max height
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(vertical = 8.dp)
                ) {
                    // Show search history header
                    item(key = "search_header") {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_message_dot),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(MaterialTheme.colorScheme.surface, CircleShape)
                                    .padding(4.dp)
                            )
                            Text(
                                text = searchHeading,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }

                    // Show search results or history
                    if (query.isEmpty()) {
                        // Show search history
                        items(
                            items = searchHistory,
                            key = { it }
                        ) { item ->
                            ListItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        query = item
                                        onItemClick(item)
                                        active = false
                                    },
                                headlineContent = { Text(text = item) },
                                leadingContent = {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            )
                        }
                    } else {
                        // Show filtered results based on query
                        val filteredResults = searchHistory.filter {
                            it.contains(query, ignoreCase = true)
                        }

                        if (filteredResults.isEmpty()) {
                            item(key = "no_results") {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(32.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "No results found",
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        } else {
                            items(
                                items = filteredResults,
                                key = { it }
                            ) { item ->
                                ListItem(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            query = item
                                            onItemClick(item)
                                            active = false
                                        },
                                    headlineContent = { Text(text = item) },
                                    leadingContent = {
                                        Icon(
                                            imageVector = Icons.Filled.Search,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}