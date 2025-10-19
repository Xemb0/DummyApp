package com.app.harigaji

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil3.compose.setSingletonImageLoaderFactory
import com.app.harigaji.core.auth.AuthViewModel
import com.app.harigaji.core.shared.SharedViewModel
import com.app.harigaji.core.user.UserViewModel
import com.app.harigaji.navigation.NavScreen
import com.app.harigaji.theme.LightColorTheme
import com.app.harigaji.theme.MyMaterialTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import harigaji.composeapp.generated.resources.Res
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
//    val sharedViewModel = koinViewModel<SharedViewModel>()
    val userViewModel = koinViewModel<UserViewModel>()
    val authViewModel = koinViewModel<AuthViewModel>()

    MyMaterialTheme {
//        setSingletonImageLoaderFactory { context ->
//            getAsyncImageLoader(context)
//        }
        NavScreen(authViewModel, userViewModel)
    }
}