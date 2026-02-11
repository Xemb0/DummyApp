package com.app.harigaji.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import com.app.harigaji.about.PrivacyPolicyRoot
import com.app.harigaji.about.PrivacyPolicyScreen
import com.app.harigaji.about.TermsAndConditionsRoot
import com.app.harigaji.artical.presentation.ArticleDetailRoot
import com.app.harigaji.artical.presentation.ArticleRoot
import com.app.harigaji.chat.ChatViewModel
import com.app.harigaji.core.auth.AuthViewModel
import com.app.harigaji.core.shared.SharedViewModel
import com.app.harigaji.core.user.UserViewModel
import com.app.harigaji.core.utils.ObserverAsEvent
import com.app.harigaji.core.utils.SnackBarController
import com.app.harigaji.core.utils.SnackBarEvent
import com.app.harigaji.core.utils.extract12HourTime
import com.app.harigaji.data.UiEvent
import com.app.harigaji.language.LanguageSelectionRoot
import com.app.harigaji.presentation.LoginScreen
import com.app.harigaji.presentation.ScreenHolder
import com.app.harigaji.presentation.chat.ChatScreen
import com.app.harigaji.presentation.forgetpass.ChangePasswordRoot
import com.app.harigaji.presentation.popups.AttendanceClockScreen
import com.app.harigaji.presentation.popups.ClockState
import com.app.harigaji.presentation.tabs.profile.ForgetPasswordRoot
import com.app.harigaji.presentation.tabs.profile.ProfileEditScreen
import com.app.harigaji.presentation.tabs.profile.VerificationRoot
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
    sharedViewModel: SharedViewModel,
) {

    val currentTab by sharedViewModel.currentTab.collectAsState()

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
                    navController.navigateUp()
                    a { "Login Success: $loginData" }
                    navController.navigate(Route.HolderScreen)
                }
            }

            else -> {

            }
        }
    }

    var initialIndex = remember { mutableStateOf(0)}


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
                            actionOnNewLine = true,
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
            enterTransition = { fadeIn(animationSpec = tween(400)) },
            exitTransition = { fadeOut(animationSpec = tween(400)) },
            popEnterTransition = { fadeIn(animationSpec = tween(400)) },
            popExitTransition = { fadeOut(animationSpec = tween(400)) }
        ) {
            navigation<Route.NavGraph>(
                startDestination = Route.Splash
            ) {
                // Splash Screen - Fade animation
                composable<Route.Splash>(
                    enterTransition = { fadeIn(animationSpec = tween(600)) },
                    exitTransition = { fadeOut(animationSpec = tween(600)) }
                ) {
                    SplashScreen(
                        navController = navController,
                        isLoggedIn = isLoggedIn
                    )
                }

                // Get Started - Slide from right
                composable<Route.GetStarted>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(500)
                        ) + fadeIn(animationSpec = tween(500))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(500)
                        ) + fadeOut(animationSpec = tween(500))
                    }
                ) {
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

                // Login Screen - Slide from right
                composable<Route.Login>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(500)
                        ) + fadeIn(animationSpec = tween(500))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(500)
                        ) + fadeOut(animationSpec = tween(500))
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(500)
                        ) + fadeIn(animationSpec = tween(500))
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(500)
                        ) + fadeOut(animationSpec = tween(500))
                    }
                ) {
                    LoginScreen(onSignIn = authViewModel::login, onSignUp = {
                        navController.navigate(Route.HolderScreen) {
                            popUpTo(Route.NavGraph) {
                                inclusive = true
                            }
                        }
                    })
                }

                // Holder Screen (Main) - Fade animation
                composable<Route.HolderScreen>(
                    enterTransition = { fadeIn(animationSpec = tween(400)) },
                    exitTransition = { fadeOut(animationSpec = tween(400)) },
                    popEnterTransition = { fadeIn(animationSpec = tween(400)) },
                    popExitTransition = { fadeOut(animationSpec = tween(400)) }
                ) { entry ->
                    ScreenHolder(
                        currentTab = currentTab,
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
                            navController.navigate(Route.Article)
                        },
                        onNotificationClick = {
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
                            navController.navigate(Route.ForgetPassword(
                                title = "Forget Password"
                            ))
                        },
                        onChangePassClick = {
                            navController.navigate(Route.ForgetPassword(
                                title = "Change Password"
                            ))
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
                        },
                        setCurrentTab = sharedViewModel::setCurrentTab
                    )
                }

                // Clock In - Slide from bottom (modal style)
                composable<Route.ClockIn>(
                    enterTransition = {
                        slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    exitTransition = {
                        slideOutVertically(
                            targetOffsetY = { it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    },
                    popEnterTransition = {
                        slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    popExitTransition = {
                        slideOutVertically(
                            targetOffsetY = { it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    }
                ){ entry ->
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

                // Settings - Slide from right
                composable<Route.Settings>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        // Settings Screen Content
                    }
                }

                // Profile Edit - Slide from right
                composable<Route.ProfileEdit>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    }
                ) {
                    ProfileEditScreen(
                        innerPadding, userProgressDetail = userProgressDetail,
                        onPrevious = {
                            navController.navigateUp()
                            sharedViewModel.setCurrentTab(3)
                        },
                        onSaveChanges = {
                            navController.navigateUp()
                        }
                    )
                }

                // Forget Password - Slide from right
                composable<Route.ForgetPassword>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    }
                ) { entry ->

                    val args = entry.toRoute<Route.ForgetPassword>()
                    ForgetPasswordRoot(
                       paddingValues =  innerPadding,
                        title = args.title,
                        onPrevious = {
                            navController.navigateUp()
                            sharedViewModel.setCurrentTab(3)
                        },
                        onNavigateToVerification = { emailID ->
                            navController.navigate(Route.Verification(
                                emailID = emailID,
                                label = "We've the code send to your email",
                                buttonText = "Reset Password"
                            ))
                        }
                    )
                }

                composable<Route.Verification>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    }
                ) { entry ->
                    val args = entry.toRoute<Route.Verification>()
                    VerificationRoot(
                        emailId = args.emailID,
                        label = args.label,
                        buttonText = args.buttonText,
                       paddingValues =  innerPadding,
                        onPrevious = {
                            navController.navigateUp()
                        },
                        onNavigateToResetPassword = {
                            navController.navigate(Route.ResetPassword)
                        },
                        onGoHome = {
                            navController.navigateUp()
                            navController.navigateUp()
                            navController.navigate(Route.HolderScreen)
                        }
                    )
                }

                // Change Password - Slide from right
                composable<Route.ResetPassword>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    }
                ) { entry ->
                    ChangePasswordRoot(
                        paddingValues = innerPadding,
                        onPrevious = {
                            navController.navigateUp()
                        },
                        onGoHome = {
                            navController.navigateUp()
                            navController.navigateUp()
                            navController.navigateUp()
                            navController.navigate(Route.HolderScreen)
                        }
                    )
                }

                // Withdrawal Request - Slide from right
                composable<Route.WithdrawalRequest>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    }
                ) { entry ->
                    WithdrawalRequestScreen(
                        innerPadding, userProgressDetail = userProgressDetail,
                        onPrevious = {
                            navController.navigateUp()
                            sharedViewModel.setCurrentTab(0)
                        },
                        onNext = {
                            navController.navigate(Route.Verification(null,
                                label= "Enter Pin to validate",
                                buttonText = "Verify"
                            ))
                        }
                    )
                }

                // Privacy Policy - Slide from right
                composable<Route.PrivacyPolicy>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    }
                ) { entry ->
                    PrivacyPolicyRoot(
                        innerPadding,
                        onPrevious = {
                            navController.navigateUp()
                            sharedViewModel.setCurrentTab(3)
                        },
                    )
                }

                // Terms and Conditions - Slide from right
                composable<Route.TermsAndConditions>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    }
                ) { entry ->
                    TermsAndConditionsRoot(
                        innerPadding,
                        onPrevious = {
                            navController.navigateUp()
                            sharedViewModel.setCurrentTab(3)
                        },
                    )
                }

                // Language Selection - Slide from right
                composable<Route.LanguageSelection>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    }
                ) { entry ->
                    LanguageSelectionRoot(
                       paddingValues =  innerPadding,
                        onPrevious = {
                            navController.navigateUp()
                            sharedViewModel.setCurrentTab(3)
                        },
                    )
                }

                // Chat - Slide from right
                composable<Route.Chat>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    }
                ) { entry ->
                    val args = entry.toRoute<Route.Chat>()

                    ChatScreen(
                        innerPadding,
                        userMessageDetails = listUserMessages.find { it.id == args.id },
                        onBackClick = {
                            navController.navigateUp()
                        },
                    )
                }

                // Article - Slide from right
                composable<Route.Article>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    }
                ) { entry ->
                    ArticleRoot(
                        paddingValues = innerPadding,
                        onPrevious = {
                            navController.navigateUp()
                        },
                        onArticleClick = { article ->
                            navController.navigate(Route.ArticleDetail(articleId = article.id))
                        }
                    )
                }
                composable<Route.ArticleDetail>(
                    enterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    exitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    },
                    popEnterTransition = {
                        slideInHorizontally(
                            initialOffsetX = { -it },
                            animationSpec = tween(400)
                        ) + fadeIn(animationSpec = tween(400))
                    },
                    popExitTransition = {
                        slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(400)
                        ) + fadeOut(animationSpec = tween(400))
                    }
                ) { entry ->


                    val args = entry.toRoute<Route.ArticleDetail>()
                    ArticleDetailRoot(
                        articleID = args.articleId,
                        paddingValues = innerPadding,
                        onPrevious = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }
}