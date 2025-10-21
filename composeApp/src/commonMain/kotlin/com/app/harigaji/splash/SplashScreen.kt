package com.app.harigaji.splash


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.navigation.NavHostController
import com.app.harigaji.navigation.Route
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.logo_splash
import org.jetbrains.compose.resources.painterResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(navController: NavHostController, isLoggedIn: Boolean?) {
    val scale = remember { Animatable(0.5f) }
    val alpha = remember { Animatable(0f) }
//
//    LaunchedEffect(Unit) {
//        // Launch both animations simultaneously
//        launch {
//            scale.animateTo(
//                targetValue = 1.6f,
//                animationSpec = tween(durationMillis = 200)
//            )
//        }
//        launch {
//            alpha.animateTo(
//                targetValue = 1f,
//                animationSpec = tween(durationMillis = 200)
//            )
//        }
//    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.logo_splash),
            contentDescription = "App Logo",
            modifier = Modifier
        )
    }

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn != null) {
            when (isLoggedIn) {
                true -> {
                    navController.popBackStack()
                    navController.navigate(Route.HolderScreen)
                }
                false -> {
                    navController.navigate(Route.GetStarted) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
            }
        }
    }
}