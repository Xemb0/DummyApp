package com.app.harigaji.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.harigaji.core.language.Language
import com.app.harigaji.core.user.UserViewModel
import com.app.harigaji.navigation.getBottomNavIcons
import kotlinx.serialization.json.JsonNull.content


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHolder(
    paddingValues: PaddingValues,
    userViewModel: UserViewModel,
    onUserIconClick:()->Unit,
    onNotificationClick:()->Unit,
    newNotificationCount: Int = 0,
    ) {



    val bottomPadding = 80.dp

    val userDetails by userViewModel.userDetails.collectAsStateWithLifecycle()
    val isUserLoggedIn by userViewModel.isUserLoggedIn.collectAsStateWithLifecycle()
    val selectedLangIso by userViewModel.currentLang.collectAsStateWithLifecycle()


    val selectedLanguage by remember {
        derivedStateOf {
            Language.entries.first { it.iso == selectedLangIso }
        }
    }


    val coroutineScope = rememberCoroutineScope()

    var selectedItem by remember { mutableIntStateOf(0) }

    val pagerState = rememberPagerState(pageCount = { 4 })

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            selectedItem = page
        }
    }

    var showBottomSheet by remember { mutableStateOf(false) }




    Scaffold(



        bottomBar = {



            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier.fillMaxWidth()
            ) {


                Card(
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(8.dp),
                    modifier = Modifier.fillMaxWidth(.99f)
                        .padding(paddingValues.calculateBottomPadding())
                        .wrapContentSize()
                ) {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.onSurface,
                        windowInsets = WindowInsets(0.dp),
                        modifier = Modifier
//                                .padding(horizontal = 8.dp)
                            .clip(CircleShape)
                    ) {


                        getBottomNavIcons().forEachIndexed { index, bottomNavItem ->
                            NavigationBarItem(
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.Transparent,
                                    indicatorColor = Color.Transparent,
                                    unselectedIconColor = Color.Transparent,
                                    selectedTextColor = Color.Transparent,
                                    unselectedTextColor = Color.Transparent,
                                    disabledIconColor = Color.Transparent,
                                    disabledTextColor = Color.Transparent,
                                ),
                                interactionSource = null,
                                modifier = Modifier.windowInsetsPadding(WindowInsets(0.dp)),
                                selected = index == selectedItem,
                                onClick = {


                                    selectedItem = index

                                    when(index){
                                        0->{
                                        }
                                        1->{
                                        }
                                        2->{

                                        }
                                        3->{
                                        }
                                    }

                                },
                                icon = {
                                    BadgedBox(
                                        badge = {
                                            if (bottomNavItem.badgeCount > 0) {
                                                Badge {
                                                    Text(text = bottomNavItem.badgeCount.toString())
                                                }
                                            } else if (bottomNavItem.hasNews) {
                                                Badge()
                                            }
                                        },
                                        content = {


                                            Icon(
                                                painter = if (index == selectedItem) bottomNavItem.selectedIcon else bottomNavItem.unSelectedIcon,
                                                contentDescription = bottomNavItem.title,
                                                tint = if (index == selectedItem) MaterialTheme.colorScheme.secondary.copy(
                                                    alpha = .8f
                                                ) else  MaterialTheme.colorScheme.secondary.copy(
                                                    alpha = 1f
                                                ),
                                                modifier = Modifier
                                                    .clip(CircleShape)
                                                    .background(if (selectedItem == index)  MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface)
                                                    .clickable {
                                                        selectedItem = index
                                                        showBottomSheet = index==3

                                                        when(index){
                                                            0->{

                                                            }
                                                            1->{}
                                                            2->{
                                                            }
                                                            3->{
                                                            }
                                                        }
                                                    }
                                                    .padding(10.dp)
                                                    .size(28.dp)
                                            )


                                        }
                                    )
                                },
//                                    label = {
//                                        Text(text = bottomNavItem.label, maxLines = 1)
//                                    }
                            )

                        }

                    }
                }

            }


        },
        content = {
//            HorizontalPager(
//                state = pagerState,
//                modifier = Modifier.fillMaxSize(),
//                verticalAlignment = Alignment.Top,
//            ) { page ->
                when (selectedItem) {
                    0 -> HomeScreen(paddingValues,userDetails)

                    3 -> ProfileScreen()

                    2 -> MessageScreen()
                    1 -> HistoryScreen()
                }
//            }

        }
    )

}

