package com.app.harigaji.presentation

import androidx.compose.animation.animateContentSize
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
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.app.harigaji.theme.ThemeValues.roundedCornerShapeMedium
import com.app.harigaji.theme.rememberCornerRadiusLarge
import com.app.harigaji.theme.rememberCornerRadiusMedium
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_message_dot
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.serialization.json.JsonNull.content
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

    DockedSearchBar(
        modifier = modifier
            .fillMaxWidth()
            .innerShadow(
                shape = if(active) rememberCornerRadiusMedium() else rememberCornerRadiusLarge(),
                shadow = Shadow(
                    offset = DpOffset(0.dp, 0.dp),
                    radius = 2.dp,
                    spread = 1.dp,
                    color = Color(0x33000000)
                )
            )
            .wrapContentHeight(),
        inputField = {
            SearchBarDefaults.InputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                query = query,
                onQueryChange = { newQuery ->
                    query = newQuery
                    if (newQuery.isNotEmpty()) {
                        onSearch(newQuery)
                    }
                },
                onSearch = { searchQuery ->
                    onSearch(searchQuery)
                    active = false
                },
                expanded = active,
                onExpandedChange = { isActive ->
                    active = isActive
                },
                placeholder = {
                    Text(
                        text = searchHeading,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
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
                    } else {
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
        shape = if(active) rememberCornerRadiusMedium() else rememberCornerRadiusLarge(),
        colors = SearchBarDefaults.colors(
            containerColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.1f),
            dividerColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        content = {
            // Wrap content with minimal padding
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp), // Limit max height but allow wrap
                contentPadding = PaddingValues(vertical = 4.dp) // Reduced padding
            ) {
                // Show search history header
                item(key = "search_header") {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp) // Reduced padding
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_message_dot),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .size(20.dp)
                                .background(MaterialTheme.colorScheme.surface, CircleShape)
                                .padding(4.dp)
                        )
                        Text(
                            text = searchHeading,
                            style = MaterialTheme.typography.labelMedium,
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
                                .padding(horizontal = 8.dp, vertical = 4.dp) // Minimal padding
                                .clip(rememberCornerRadiusMedium())
                                .clickable {
                                    query = item
                                    onItemClick(item)
                                    active = false
                                },
                            headlineContent = {
                                Text(
                                    text = item,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            leadingContent = {
                                Icon(
                                    imageVector = Icons.Filled.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
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
                                    .padding(vertical = 16.dp), // Reduced padding
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "No results found",
                                    style = MaterialTheme.typography.bodyMedium,
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
                                    .padding(horizontal = 4.dp, vertical = 0.dp)
                                    .clip(rememberCornerRadiusMedium())
                                    .clickable {
                                        query = item
                                        onItemClick(item)
                                        active = false
                                    },
                                headlineContent = {
                                    Text(
                                        text = item,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                                leadingContent = {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        modifier = Modifier.size(20.dp)
                                    )
                                },
                            )
                        }
                    }
                }
            }
        }
    )
}