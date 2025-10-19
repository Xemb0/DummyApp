package com.app.harigaji.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.harigaji.core.language.Language
import com.app.harigaji.core.user.UserViewModel
import com.app.harigaji.navigation.getBottomNavIcons
import com.app.harigaji.presentation.tabs.HistoryScreen
import com.app.harigaji.presentation.tabs.HomeScreen
import com.app.harigaji.presentation.tabs.MessageScreen
import com.app.harigaji.presentation.tabs.profile.ProfileScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHolder(
    paddingValues: PaddingValues,
    userViewModel: UserViewModel,
    onClockIn:()->Unit,
    onLogout:()->Unit,
    onProfileClick:()->Unit,
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
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White.copy(alpha = 0f),

                    ),
                    elevation = CardDefaults.cardElevation(32.dp
                    ),
                    modifier = Modifier.fillMaxWidth(.99f)
                        .padding(paddingValues.calculateBottomPadding())
                        .wrapContentSize()
                ) {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .height(IntrinsicSize.Min)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.surface.copy(alpha = .7f),
                                    shape = CircleShape
                                )
                                .dropShadow(
                                    shadow = Shadow(
                                        offset = DpOffset(0.dp, 0.dp),
                                        radius = 9.dp,
                                        spread = 3.dp,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = .1f)
                                    ),
                                    shape = CircleShape
                                )
                                .blur(40.dp)


                        )
                        NavigationBar(
                            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = .1f),
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

                                        when (index) {
                                            0 -> {
                                            }

                                            1 -> {
                                            }

                                            2 -> {

                                            }

                                            3 -> {
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
                                                    ) else MaterialTheme.colorScheme.secondary.copy(
                                                        alpha = 1f
                                                    ),
                                                    modifier = Modifier
                                                        .clip(CircleShape)
                                                        .border(
                                                            width = if (selectedItem == index) 2.dp else 0.dp,
                                                            color = if (selectedItem == index) MaterialTheme.colorScheme.secondary.copy(
                                                                alpha = .8f
                                                            ) else Color.Transparent,
                                                            shape = CircleShape
                                                        )
                                                        .background(if (selectedItem == index) MaterialTheme.colorScheme.surface.copy(alpha = .9f) else MaterialTheme.colorScheme.surface.copy(alpha = .7f))
                                                        .clickable {
                                                            selectedItem = index
                                                            showBottomSheet = index == 3

                                                            when (index) {
                                                                0 -> {

                                                                }

                                                                1 -> {}
                                                                2 -> {
                                                                }

                                                                3 -> {
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

            }


        },
        content = {
//            HorizontalPager(
//                state = pagerState,
//                modifier = Modifier.fillMaxSize(),
//                verticalAlignment = Alignment.Top,
//            ) { page ->
                when (selectedItem) {
                    0 -> HomeScreen(
                        paddingValues = paddingValues,
                        userDetails = userDetails,
                        onClockIn = onClockIn

                    )

                    3 -> ProfileScreen(paddingValues,
                        onLogout = onLogout,
                        onProfileClick = onProfileClick
                    )

                    2 -> MessageScreen(paddingValues)
                    1 -> HistoryScreen(paddingValues)
                }
//            }

        }
    )

}

