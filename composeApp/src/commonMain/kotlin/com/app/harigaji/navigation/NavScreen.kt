package com.app.harigaji.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
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
import androidx.navigation.toRoute
import co.touchlab.kermit.Logger.Companion.a
import com.app.harigaji.artical.ArticlesScreen
import com.app.harigaji.artical.ArticlesViewModel
import com.app.harigaji.chat.ChatViewModel
import com.app.harigaji.core.auth.AuthViewModel
import com.app.harigaji.core.user.UserViewModel
import com.app.harigaji.core.utils.ObserverAsEvent
import com.app.harigaji.core.utils.SnackBarController
import com.app.harigaji.core.utils.SnackBarEvent
import com.app.harigaji.core.utils.extract12HourTime
import com.app.harigaji.data.UiEvent
import com.app.harigaji.presentation.LanguageSelectionScreen
import com.app.harigaji.presentation.LoginScreen
import com.app.harigaji.presentation.ScreenHolder
import com.app.harigaji.presentation.about.PrivacyPolicyScreen
import com.app.harigaji.presentation.about.TermsAndConditionsScreen
import com.app.harigaji.presentation.chat.ChatScreen
import com.app.harigaji.presentation.forgetpass.ChangePasswordScreen
import com.app.harigaji.presentation.popups.AttendanceClockScreen
import com.app.harigaji.presentation.popups.ClockState
import com.app.harigaji.presentation.tabs.profile.ForgetPassScreen
import com.app.harigaji.presentation.tabs.profile.ProfileEditScreen
import com.app.harigaji.presentation.tabs.profile.VerificationScreen
import com.app.harigaji.presentation.withdrawal.WithdrawalRequestScreen
import com.app.harigaji.splash.SplashScreen
import com.app.harigaji.splash.SplashStartScreen
import kotlinx.coroutines.flow.merge
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@OptIn(ExperimentalTime::class)
@Composable
fun NavScreen(
    authViewModel: AuthViewModel,
    userViewModel: UserViewModel,
    chatViewModel: ChatViewModel,
    articlesViewModel: ArticlesViewModel,
) {

    val navController = rememberNavController()
    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val userProgressDetail by userViewModel.userProgressDetail.collectAsState()
    val mergedEvents = merge(authViewModel.uiEvent)

    val authState by authViewModel.authState.collectAsState()

    val isLoggedIn by userViewModel.isUserLoggedIn.collectAsState()
    val listUserMessages by chatViewModel.messages.collectAsState()


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
                        listUserMessages = listUserMessages,
                        onChatMessageClick = {
                            navController.navigate(Route.Chat(
                                id = it
                            ))
                        },
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
                        onClockIn = {
                            navController.navigate(
                                Route.ClockIn(
                                    clockInTime = Clock.System.now().toEpochMilliseconds(),
                                    isClockedIn = true
                                )
                            )
                        },
                        onLogout = {
                            authViewModel.logout()
                            userViewModel.logout()
                            navController.navigate(Route.Login) {
                                popUpTo(Route.NavGraph) {
                                    inclusive = true
                                }
                            }
                        },
                        onProfileClick = {
                            navController.navigate(Route.ProfileEdit)
                        },
                        onForgetPassClick = {
                            navController.navigate(Route.ForgetPassword)
                        },
                        onUpdatePinClick = {},
                        onLanguageClick = {
                            navController.navigate(Route.LanguageSelection)
                        },
                        onClearCacheClick = {},
                        onPrivacyPolicyClick = {
                            navController.navigate(Route.PrivacyPolicy)
                        },
                        onTermsConditionsClick = {
                            navController.navigate(Route.TermsAndConditions)
                        },
                        onBalanceCardClick = {
                            navController.navigate(Route.WithdrawalRequest)
                        },
                        onArticleClick = {
                            navController.navigate(Route.Article)
                        }
                    )

                }
                composable<Route.ClockIn>{ entry ->
                    val args = entry.toRoute<Route.ClockIn>()
                    AttendanceClockScreen(
                        paddingValues = innerPadding,
                        onGoToHome = {
                            navController.navigate(Route.HolderScreen) {
                                popUpTo(Route.NavGraph) {
                                    inclusive = false
                                }
                            }
                        },
                        clockState = ClockState(args.isClockedIn, extract12HourTime(args.clockInTime))
                    )
                }

                composable<Route.Settings> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        // Settings Screen Content
                    }
                }
                composable<Route.ProfileEdit> {
                    ProfileEditScreen(
                        innerPadding, userProgressDetail = userProgressDetail,
                        onPrevious = {
                            navController.navigateUp()
                        },
                        onSaveChanges = {
                            navController.navigateUp()
                        }
                    )
                }
                composable<Route.ForgetPassword> {
                    ForgetPassScreen(
                        innerPadding, userProgressDetail = userProgressDetail,
                        onPrevious = {
                            navController.navigateUp()
                        },
                        onResetClick = { emailID ->
                            navController.navigate(Route.Verification(
                                emailID = emailID,
                                label = "We’ve the code send to your email",
                                buttonText = "Reset Password"
                            ))
                        }
                    )
                }

                composable<Route.Verification> { entry ->
                    val args = entry.toRoute<Route.Verification>()
                    VerificationScreen(
                        emailId = args.emailID,
                        label = args.label,
                        innerPadding, userProgressDetail = userProgressDetail,
                        onPrevious = {
                            navController.navigateUp()
                        },
                        onNext = {

                            if(args.emailID!=null){
                                navController.navigate(Route.ChangePassword)
                            }else
                            navController.navigate(Route.HolderScreen){
                                popUpTo(Route.NavGraph) {
                                    inclusive = false
                                }
                            }
                        }
                    )
                }
                composable<Route.ChangePassword> { entry ->
                    ChangePasswordScreen(
                        innerPadding, userProgressDetail = userProgressDetail,
                        onPrevious = {
                            navController.navigateUp()
                        },
                        onNext = { _, _ ->
                            navController.navigate(Route.HolderScreen){
                                popUpTo(Route.NavGraph) {
                                    inclusive = false
                                }
                            }
                        }
                    )
                }
                composable<Route.WithdrawalRequest> { entry ->
                    WithdrawalRequestScreen(
                        innerPadding, userProgressDetail = userProgressDetail,
                        onPrevious = {
                            navController.navigateUp()
                        },
                        onNext = {
                            navController.navigate(Route.Verification(null,
                                label= "Enter Pin to validate",
                                buttonText = "Verify"
                            ))
                        }
                    )
                }
                composable<Route.PrivacyPolicy> { entry ->
                    PrivacyPolicyScreen(
                        innerPadding,
                        onPrevious = {
                            navController.navigateUp()
                        },
                    )
                }
                composable<Route.TermsAndConditions> { entry ->
                    TermsAndConditionsScreen(
                        innerPadding,
                        onPrevious = {
                            navController.navigateUp()
                        },
                    )
                }
                composable<Route.LanguageSelection> { entry ->
                    LanguageSelectionScreen(
                        innerPadding,
                        onPrevious = {
                            navController.navigateUp()
                        },
                    )
                }

                composable<Route.Chat> { entry ->
                    val args = entry.toRoute<Route.Chat>()

                    ChatScreen(
                        innerPadding,
                        userMessageDetails = listUserMessages.find { it.id == args.id },
                        onBackClick = {
                            navController.navigateUp()
                        },
                    )
                }

                composable<Route.Article> { entry ->

                    ArticlesScreen(
                        paddingValues = innerPadding,
                        onPrevious = {
                            navController.navigateUp()
                        },
                        viewModel =articlesViewModel
                    )
                }

            }
        }
    }
}

