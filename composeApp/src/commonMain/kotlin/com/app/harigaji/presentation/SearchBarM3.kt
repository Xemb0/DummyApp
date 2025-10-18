package com.app.harigaji.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_home
import harigaji.composeapp.generated.resources.ic_message_dot
import harigaji.composeapp.generated.resources.ic_user
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarM3(
    onSearch:(String) -> Unit,
    onItemClick:(String)->Unit,
    searchHeading:String = "Recent search",
) {

    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }


//    val keyboard
    SearchBar (
        modifier = Modifier.clip(RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp)).animateContentSize(),
        shape = if(active) RoundedCornerShape(topEnd = 32.dp, topStart = 32.dp) else RoundedCornerShape(32.dp),
        colors = SearchBarDefaults.colors(

            containerColor = MaterialTheme.colorScheme.background,
        ),
        windowInsets = WindowInsets(0),
        query = query,
        onQueryChange = {

            query = it
//            if(it.length>1){
                onSearch(it)
//            }

                        },
        onSearch = { newQuery ->
            onSearch(newQuery)

        },
        active = active,
        onActiveChange = { active = it },
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
        },
        trailingIcon = {
//            Row {
//                IconButton(onClick = { /* open mic dialog */ }) {
//                    Icon(painter = painterResource(Res.drawable.ic_go_to_cart), contentDescription = "Mic")
//                }
                if (active) {
                    IconButton(
                        onClick = { if (query.isNotEmpty()) query = "" else active = false }
                    ) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                    }
                }
//            }
        }
    ) {
        LazyColumn {

            item {
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
                    Text(searchHeading, color = MaterialTheme.colorScheme.onSurfaceVariant, )
                }
            }

        }
    }
}



//@Composable
//fun SummaryItem(
//    product : ProductWithCartValue,
//    onItemClick:(String)->Unit
//){
//    Card(
//        shape = RoundedCornerShape(16.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable {
//                onItemClick(product.id)
//            }
//            .padding(horizontal = 16.dp, vertical = 2.dp),
//        colors = CardDefaults.cardColors(
//            containerColor = mySurface
//        )
//    ){
//        Row (
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier.fillMaxWidth()
//                .height(IntrinsicSize.Min)
////                .padding(vertical = 8.dp, horizontal = 16.dp)
//        ){
//            Box(
//                modifier = Modifier.weight(.2f).background(mySurface)
//            ){
//
//            AsyncImage(
//                modifier = Modifier.size(50.dp).align(Alignment.Center),
//                model = product.image,
//                contentDescription = null,
//
//                )
//            }
////        Image(
////            painter = painterResource(Res.drawable.biscuitdarkfantasy_biscuitdarkfantasy_300gm),
////            contentDescription = null,
////            modifier = Modifier.weight(.2f),
////        )
//            Column(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .weight(.8f)
//                    .padding(start = 16.dp, end = 16.dp)
//
//            ) {
//                MyAppSubHeadingLabel(
//                    product.name
//                )
//
////                Row (
////                    verticalAlignment = Alignment.CenterVertically,
////                    horizontalArrangement = Arrangement.SpaceEvenly,
////                    modifier = Modifier
////
////                ) {
////                    MyAppSubHeadingLabel(
////                        "MRP: ", alpha = .7f
////                    )
////                    MyAppSubHeadingLabel(
////                        product.mrp.toCleanString()
////                    )
////
////                }
//                product.productSpec.getOrNull(0)
//                    ?.takeIf { !it.label.isNullOrBlank() || !it.value.isNullOrBlank() }
//                    ?.let { item ->
//                        Row(
//                            horizontalArrangement = Arrangement.Start,
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(horizontal = 8.dp)
//                                .background(mySecodary.copy(alpha = 0f))
//                        ) {
//                            MyAppSubHeadingLabel(
//                                padding = 0.dp,
//                                label = item.label.orEmpty() + "  ",
//                                maxLines = 2,
//                                fontSize = fontsmall,
//                                modifier = Modifier,
//                                color = mySecodary.copy(alpha = 0.8f),
//                                lineHeight = 14
//                            )
//
//                            MyAppBodyText(
//                                label = buildString {
//                                    append(item.value.orEmpty())
//                                    product.productSpec.getOrNull(1)
//                                        ?.takeIf { !it.label.isNullOrBlank() || !it.value.isNullOrBlank() }
//                                        ?.let {
//                                            append(" (${it.value.orEmpty()}${it.label.orEmpty()})")
//                                        }
//                                },
//                                fontSize = fontsmall,
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                                    .alpha(0.8f)
//                                    .weight(1f),
//                                lineHeight = 14,
//                                maxLines = 2,
//                                fontWeight = FontWeight.W600,
//                                padding = 0.dp,
//                                color = mySecodary.copy(alpha = 0.8f),
//                            )
//                        }
//                    }
//                product.productSpec.getOrNull(2)?.takeIf { !it.label.isNullOrBlank() || !it.value.isNullOrBlank() }?.let { item ->
//                    Row(
//                        horizontalArrangement = Arrangement.Start,
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 8.dp)
//                            .background(mySecodary.copy(alpha = 0f))
//                    ) {
//                        MyAppSubHeadingLabel(
//                            padding = 0.dp,
//                            label = item.label.orEmpty() + "  ",
//                            maxLines = 2,
//                            fontSize = fontsmall,
//                            modifier = Modifier,
//                            color = mySecodary.copy(alpha = 0.8f),
//                            lineHeight = 14
//                        )
//
//                        MyAppBodyText(
//                            label = buildString {
//                                append(item.value.orEmpty())
//                                product.productSpec.getOrNull(3)
//                                    ?.takeIf { !it.label.isNullOrBlank() || !it.value.isNullOrBlank() }
//                                    ?.let {
//                                        append(" (${it.value.orEmpty()}${it.label.orEmpty()})")
//                                    }
//                            },
//                            fontSize = fontsmall,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .alpha(0.8f)
//                                .weight(1f),
//                            lineHeight = 14,
//                            maxLines = 2,
//                            fontWeight = FontWeight.W600,
//                            padding = 0.dp,
//                            color = mySecodary.copy(alpha = 0.8f),
//                        )
//                    }
//                }?:product.productSpec.getOrNull(3)?.takeIf { !it.label.isNullOrBlank() || !it.value.isNullOrBlank() }?.let { item ->
//                    Row(
//                        horizontalArrangement = Arrangement.Start,
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 8.dp)
//                            .background(mySecodary.copy(alpha = 0f))
//                    ) {
//                        MyAppSubHeadingLabel(
//                            padding = 0.dp,
//                            label = item.label.orEmpty() + "  ",
//                            maxLines = 2,
//                            fontSize = fontsmall,
//                            modifier = Modifier,
//                            color = mySecodary.copy(alpha = 0.8f),
//                            lineHeight = 14
//                        )
//
//                        MyAppBodyText(
//                            label = buildString {
//                                append(item.value.orEmpty())
//                            },
//                            fontSize = fontsmall,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .alpha(0.8f)
//                                .weight(1f),
//                            lineHeight = 14,
//                            maxLines = 2,
//                            fontWeight = FontWeight.W600,
//                            padding = 0.dp,
//                            color = mySecodary.copy(alpha = 0.8f),
//                        )
//                    }
//                }?: product.productSpec.getOrNull(4)?.takeIf { !it.label.isNullOrBlank() || !it.value.isNullOrBlank() }?.let { item ->
//                    Row(
//                        horizontalArrangement = Arrangement.Start,
//                        verticalAlignment = Alignment.CenterVertically,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 8.dp)
//                            .background(mySecodary.copy(alpha = 0f))
//                    ) {
//                        MyAppSubHeadingLabel(
//                            padding = 0.dp,
//                            label = item.label.orEmpty() + "  ",
//                            maxLines = 2,
//                            fontSize = fontsmall,
//                            modifier = Modifier,
//                            color = mySecodary.copy(alpha = 0.8f),
//                            lineHeight = 14
//                        )
//
//                        MyAppBodyText(
//                            label = buildString {
//                                append(item.value.orEmpty())
//                                product.productSpec.getOrNull(3)
//                                    ?.takeIf { !it.label.isNullOrBlank() || !it.value.isNullOrBlank() }
//                                    ?.let {
//                                        append(" (${it.value.orEmpty()}${it.label.orEmpty()})")
//                                    }
//                            },
//                            fontSize = fontsmall,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .alpha(0.8f)
//                                .weight(1f),
//                            lineHeight = 14,
//                            maxLines = 2,
//                            fontWeight = FontWeight.W600,
//                            padding = 0.dp,
//                            color = mySecodary.copy(alpha = 0.8f),
//                        )
//                    }
//                }
//
//            }
//            MyAppSubHeadingLabel(
//                (product.sellingPrice).toCleanCurrencyString(),
//                align = TextAlign.End,
//                modifier = Modifier.weight(.3f)
//            )
//
//        }
//    }
//}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DockedSearchBarM3() {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    val searchHistory = listOf("Android", "Kotlin", "Compose", "Material Design", "GPT-4")

    DockedSearchBar(
        query = query,
        onQueryChange = { query = it },
        onSearch = { newQuery ->
            println("Performing search on query: $newQuery")
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
        },
        trailingIcon = {
            Row {
                IconButton(onClick = { /* open mic dialog */ }) {
                    Icon(painter = painterResource(Res.drawable.ic_user), contentDescription = "Mic")
                }
                if (active) {
                    IconButton(
                        onClick = { if (query.isNotEmpty()) query = "" else active = false }
                    ) {
                        Icon(imageVector = Icons.Filled.Close, contentDescription = "Close")
                    }
                }
            }
        }
    ) {
        searchHistory.takeLast(3).forEach { item ->
            ListItem(
                modifier = Modifier.clickable { query = item },
                headlineContent = { Text(text = item) },
                leadingContent = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_home),
                        contentDescription = null
                    )
                }
            )
        }
    }
}