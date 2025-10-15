package com.app.harigaji.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.app.harigaji.presentation.LoginScreen
import com.app.harigaji.splash.SplashScreen
import com.app.harigaji.splash.SplashStartScreen


@Composable
fun NavScreen() {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.NavGraph,
        enterTransition = { fadeIn(animationSpec = tween(600)) },
        exitTransition = { fadeOut(animationSpec = tween(600)) },
        popEnterTransition = { fadeIn(animationSpec = tween(600)) },
        popExitTransition = { fadeOut(animationSpec = tween(600)) }
    ) {
        navigation<Route.NavGraph>(
            startDestination = Route.Login
        ) {
            composable<Route.Splash> {
                SplashScreen(
                    navController = navController,
                    isLoggedIn = false
                )

            }

            composable<Route.GetStarted> {
                SplashStartScreen(
                    onGetStartedClick = {
                        navController.navigate(Route.Login) {
                            popUpTo(Route.NavGraph) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
            composable<Route.Login> {
                LoginScreen(onSignIn = {_,_->
                    navController.navigate(Route.Home) {
                        popUpTo(Route.NavGraph) {
                            inclusive = true
                        }
                    }
                }, onSignUp = {
                    navController.navigate(Route.Home) {
                        popUpTo(Route.NavGraph) {
                            inclusive = true
                        }
                    }
                })
            }
        }
    }
}