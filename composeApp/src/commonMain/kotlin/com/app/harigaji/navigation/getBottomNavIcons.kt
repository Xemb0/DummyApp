package com.app.harigaji.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_home
import harigaji.composeapp.generated.resources.ic_message
import harigaji.composeapp.generated.resources.ic_message_dot
import harigaji.composeapp.generated.resources.ic_recent
import harigaji.composeapp.generated.resources.ic_user
import org.jetbrains.compose.resources.painterResource

@Composable
fun getBottomNavIcons(): List<BottomNavIcon> {
 return listOf(
    BottomNavIcon(
        title = "Home",
        route = Route.HomeTab,
        label = "Home",
        selectedIcon = painterResource(Res.drawable.ic_home),
        unSelectedIcon = painterResource(Res.drawable.ic_home),
        hasNews = false,
        badgeCount = 0
    ),
     BottomNavIcon(
         title = "History",
         route = Route.Settings,
         label = "History",
         selectedIcon = painterResource(Res.drawable.ic_recent),
         unSelectedIcon = painterResource(Res.drawable.ic_recent),
         hasNews = false,
         badgeCount = 0
     ),
     BottomNavIcon(
         title = "Chat",
         route = Route.MyOrderTab,
         label = "My Order",
         selectedIcon = painterResource(Res.drawable.ic_message_dot),
         unSelectedIcon = painterResource(Res.drawable.ic_message_dot),
         hasNews = false,
         badgeCount = 0
     ),
    BottomNavIcon(
        title = "User",
        route = Route.CartTab,
        label = "User",
        selectedIcon = painterResource(Res.drawable.ic_user),
        unSelectedIcon = painterResource(Res.drawable.ic_user),
        hasNews = false,
        badgeCount = 0
    ),


 )
}


data class BottomNavIcon(
    val title: String,
    val route: Route,
    val label: String,
    val selectedIcon: Painter,
    val unSelectedIcon:Painter,
    val hasNews : Boolean,
    val badgeCount : Int
)
