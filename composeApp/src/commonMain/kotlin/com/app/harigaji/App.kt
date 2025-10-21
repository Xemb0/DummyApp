package com.app.harigaji

import androidx.compose.runtime.*
import com.app.harigaji.artical.ArticlesViewModel
import com.app.harigaji.chat.ChatViewModel
import com.app.harigaji.core.auth.AuthViewModel
import com.app.harigaji.core.user.UserViewModel
import com.app.harigaji.navigation.NavScreen
import com.app.harigaji.theme.MyMaterialTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
//    val sharedViewModel = koinViewModel<SharedViewModel>()
    val userViewModel = koinViewModel<UserViewModel>()
    val authViewModel = koinViewModel<AuthViewModel>()
    val chatViewModel = koinViewModel<ChatViewModel>()
    val articlesViewModel = koinViewModel<ArticlesViewModel>()

    MyMaterialTheme {
//        setSingletonImageLoaderFactory { context ->
//            getAsyncImageLoader(context)
//        }
        NavScreen(authViewModel, userViewModel,chatViewModel,articlesViewModel)
    }
}