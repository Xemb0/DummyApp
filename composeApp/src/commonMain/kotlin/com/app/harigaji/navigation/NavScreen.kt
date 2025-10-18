package com.app.harigaji.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import co.touchlab.kermit.Logger.Companion.a
import com.app.harigaji.core.auth.AuthViewModel
import com.app.harigaji.core.shared.SharedViewModel
import com.app.harigaji.core.user.UserViewModel
import com.app.harigaji.core.utils.ObserverAsEvent
import com.app.harigaji.core.utils.SnackBarController
import com.app.harigaji.core.utils.SnackBarEvent
import com.app.harigaji.data.UiEvent
import com.app.harigaji.presentation.LoginScreen
import com.app.harigaji.presentation.ScreenHolder
import com.app.harigaji.splash.SplashScreen
import com.app.harigaji.splash.SplashStartScreen
import kotlinx.coroutines.flow.merge


@Composable
fun NavScreen(
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
) {

    val navController = rememberNavController()
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val mergedEvents = merge(authViewModel.uiEvent)

    val authState by authViewModel.authState.collectAsState()

    val isLoggedIn by userViewModel.isUserLoggedIn.collectAsState()


    ObserverAsEvent(mergedEvents) { event ->
        when (event) {
            is UiEvent.Navigate.HolderScreen -> {
                navController.navigateUp()
                navController.navigateUp()
                navController.navigate(Route.HolderScreen)

            }

            is UiEvent.Navigate.EnterOTP -> {
                navController.navigate(
                    Route.EnterOTP(
                        mobile = authState.phone
                    )
                )
            }

            is UiEvent.ShowSnackBar -> {
                SnackBarController.sendEvent(
                    event = SnackBarEvent(
                        message = event.message,
                    )
                )
            }

            is UiEvent.Auth.VerifyOtp -> {
                authViewModel.verifyOtp()
            }

            is UiEvent.Auth.LoginSuccess -> {
                event.loginData?.let { loginData ->
                    a { "Login Success: $loginData" }
                    navController.navigate(Route.HolderScreen)
                }
            }



            else -> {

            }
        }
    }



    Scaffold(


        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.9f)
            ) {


                SnackbarHost(
                    modifier = Modifier.align(Alignment.TopCenter),
                    hostState = snackbarHostState,
                    snackbar = { data ->
                        Snackbar(
                            snackbarData = data,
                            modifier = Modifier,
                            actionOnNewLine = true, // or false
                            actionContentColor = MaterialTheme.colorScheme.secondary,
                            dismissActionContentColor = MaterialTheme.colorScheme.surface,
                            containerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            contentColor = MaterialTheme.colorScheme.surface,
                            actionColor = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(12.dp),
                        )

                    }
                )
            }
        },
    )
    { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Route.NavGraph,
            enterTransition = { fadeIn(animationSpec = tween(600)) },
            exitTransition = { fadeOut(animationSpec = tween(600)) },
            popEnterTransition = { fadeIn(animationSpec = tween(600)) },
            popExitTransition = { fadeOut(animationSpec = tween(600)) }
        ) {
            navigation<Route.NavGraph>(
                startDestination = Route.Splash
            ) {
                composable<Route.Splash> {
                    SplashScreen(
                        navController = navController,
                        isLoggedIn = isLoggedIn
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
                    LoginScreen(onSignIn = authViewModel::login, onSignUp = {
                        navController.navigate(Route.Home) {
                            popUpTo(Route.NavGraph) {
                                inclusive = true
                            }
                        }
                    })
                }
                composable<Route.HolderScreen> {
                    ScreenHolder(
                        paddingValues = innerPadding,
                        userViewModel = userViewModel,
                        newNotificationCount = 3,
                        onUserIconClick = {
                            navController.navigate(Route.Settings)
                        },

                        onNotificationClick = {
//                                SnackBarController.sendEvent(
//                                    event = SnackBarEvent(
//                                        message = "Notification Clicked",
//                                    )
//                                )
                            navController.navigate(Route.Notification)
                        },

                    )

                }
            }
        }
    }
}