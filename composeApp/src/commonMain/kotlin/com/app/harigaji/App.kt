package com.app.harigaji

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.app.harigaji.artical.ArticlesViewModel
import com.app.harigaji.chat.ChatViewModel
import com.app.harigaji.core.auth.AuthViewModel
import com.app.harigaji.core.shared.SharedViewModel
import com.app.harigaji.core.uiconfig.UiConfigViewModel
import com.app.harigaji.core.user.UserViewModel
import com.app.harigaji.navigation.NavScreen
import com.app.harigaji.presentation.uiconfig.FloatingControlButton
import com.app.harigaji.presentation.uiconfig.FloatingUiControlPanel
import com.app.harigaji.theme.DynamicMaterialTheme
import com.app.harigaji.theme.LocalUiConfig
import androidx.compose.runtime.CompositionLocalProvider
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    val sharedViewModel = koinViewModel<SharedViewModel>()
    val userViewModel = koinViewModel<UserViewModel>()
    val authViewModel = koinViewModel<AuthViewModel>()
    val chatViewModel = koinViewModel<ChatViewModel>()
    val articlesViewModel = koinViewModel<ArticlesViewModel>()
    val uiConfigViewModel = koinViewModel<UiConfigViewModel>()
    
    val uiConfig by uiConfigViewModel.uiConfig.collectAsState()
    
    fun triggerDummyCrash() {
            throw RuntimeException("💥 Dummy crash for Firebase Crashlytics testing")
    }

    Box(modifier = Modifier.fillMaxSize()) {
        CompositionLocalProvider(LocalUiConfig provides uiConfig) {
            DynamicMaterialTheme(config = uiConfig) {
                NavScreen(
                    authViewModel,
                    userViewModel,
                    chatViewModel,
                    articlesViewModel,
                    sharedViewModel
                )
            }
        }

        FloatingUiControlPanel(
            viewModel = uiConfigViewModel,
            modifier = Modifier.fillMaxSize()
        )
        
        // Floating Control Button
        FloatingControlButton(
            viewModel = uiConfigViewModel,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}