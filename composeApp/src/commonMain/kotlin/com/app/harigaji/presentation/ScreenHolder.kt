package com.app.harigaji.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.harigaji.chat.UserMessageDetails
import com.app.harigaji.core.language.Language
import com.app.harigaji.core.user.UserViewModel
import com.app.harigaji.navigation.CurvedBottomNavigation
import com.app.harigaji.navigation.FabMenuItem
import com.app.harigaji.navigation.getBottomNavIcons
import com.app.harigaji.presentation.tabs.HistoryScreen
import com.app.harigaji.presentation.tabs.HomeScreen
import com.app.harigaji.presentation.tabs.MessageScreen
import com.app.harigaji.presentation.tabs.profile.ProfileScreen
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_calender
import harigaji.composeapp.generated.resources.ic_user_check
import harigaji.composeapp.generated.resources.ic_wallet


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHolder(
    currentTab :Int,
    setCurrentTab:(Int)->Unit,
    paddingValues: PaddingValues,
    userViewModel: UserViewModel,
    onClockIn:()->Unit,
    onLogout:()->Unit,
    onForgetPassClick:()->Unit,
    onProfileClick:()->Unit,
    onUserIconClick:()->Unit,
    onNotificationClick:()->Unit,
    newNotificationCount: Int = 0,
    onUpdatePinClick:()->Unit,
    onLanguageClick:()->Unit,
    onClearCacheClick:()->Unit,
    onPrivacyPolicyClick:()->Unit,
    onTermsConditionsClick:()->Unit,
    onBalanceCardClick:()->Unit,
    onChatMessageClick:(id:Int)->Unit,
    onArticleClick:()->Unit,
    listUserMessages:List<UserMessageDetails>
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


    val pagerState = rememberPagerState(pageCount = { 4 })

    LaunchedEffect(currentTab){

        co.touchlab.kermit.Logger.d("current tab changed $currentTab")
        if (pagerState.currentPage != currentTab) {
            pagerState.animateScrollToPage(currentTab)
        }
    }



    var showBottomSheet by remember { mutableStateOf(false) }




    Scaffold(



        bottomBar = {
            val fabMenuItems = listOf(
                FabMenuItem(Res.drawable.ic_calender, "User", MaterialTheme.colorScheme.secondary),
                FabMenuItem(Res.drawable.ic_wallet, "Wallet", MaterialTheme.colorScheme.secondary)
            )
            CurvedBottomNavigation(
                leftItems = getBottomNavIcons().take(2),
                rightItems = getBottomNavIcons().takeLast(2),
                fabMenuItems = fabMenuItems,
                selectedIndex = currentTab,
                onItemSelected = { setCurrentTab(it) },
                onFabMenuItemClick = { title ->
                    when(title){
                        "Wallet"-> onBalanceCardClick()
                        "User"-> onUserIconClick()
                    }
                }
            )
        },
        content = {
//            HorizontalPager(
//                state = pagerState,
//                modifier = Modifier.fillMaxSize(),
//                verticalAlignment = Alignment.Top,
//            ) { page ->
                when (currentTab) {
                    0 -> HomeScreen(
                        paddingValues = paddingValues,
                        userDetails = userDetails,
                        onClockIn = onClockIn,
                        onBalanceCardClick = onBalanceCardClick,
                    )

                    3 -> ProfileScreen(
                        paddingValues,
                        onLogout = onLogout,
                        onProfileClick = onProfileClick,
                        onForgetPassClick = onForgetPassClick,
                        onUpdatePinClick = onUpdatePinClick,
                        onLanguageClick = onLanguageClick,
                        onClearCacheClick = onClearCacheClick,
                        onPrivacyPolicyClick = onPrivacyPolicyClick,
                        onTermsConditionsClick = onTermsConditionsClick,
                        onArticleClick = onArticleClick
                    )

                    2 -> MessageScreen(
                        paddingValues,
                        onPrevious = {
                            setCurrentTab(0)
                        },
                        onChatMessageClick = onChatMessageClick,
                        listUserMessages = listUserMessages

                    )
                    1 -> HistoryScreen(paddingValues)
                }
//            }

        }
    )

}

