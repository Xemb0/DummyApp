package com.app.harigaji.presentation.tabs.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.harigaji.core.auth.AuthViewModel
import com.app.harigaji.core.user.UserViewModel
import com.app.harigaji.core.utils.SnackBarEvent
import com.app.harigaji.data.UiEvent
import com.app.harigaji.data.UserProgressDetail
import com.app.harigaji.presentation.MyBottomNav
import com.app.harigaji.presentation.MyTopBar
import com.app.harigaji.theme.MyMaterialTheme
import com.app.harigaji.theme.SpacerVerticalLarge
import com.app.harigaji.theme.SpacerVerticalMedium
import com.app.harigaji.theme.rememberOuterHorizontalPaddingExtraLarge
import com.app.harigaji.theme.rememberOuterVerticalPaddingMedium
import com.app.harigaji.theme.rememberSizeExtraLarge
import com.app.harigaji.theme.rememberSizeLarge
import com.app.harigaji.theme.rememberSizeMedium
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime

@Composable
fun VerificationRoot(
    paddingValues: PaddingValues,
    emailId: String? = null,
    label: String,
    authViewModel: AuthViewModel = koinViewModel(),
    onPrevious: () -> Unit = {},
    onNavigateToResetPassword: () -> Unit = {},
) {
    val authState by authViewModel.authState.collectAsStateWithLifecycle()


    // Collect UI events
    LaunchedEffect(Unit) {
        authViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    SnackBarEvent(
                        message = event.message,
                    )
                }
                is UiEvent.Navigate.ResetPassword -> {
                    onNavigateToResetPassword()
                }
                else -> {}
            }
        }
    }

    VerificationScreen(
        paddingValues = paddingValues,
        emailId = emailId,
        label = label,
        isLoading = authState.isLoading,
        error = authState.error,
        onPrevious = onPrevious,
        onVerifyClick = authViewModel::onVerifyClick,
        onResendOtp = { authViewModel.onResendEmailOtp(emailId ?: "") }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun VerificationScreen(
    paddingValues: PaddingValues,
    emailId: String? = null,
    label: String,
    isLoading: Boolean = false,
    error: String? = null,
    onPrevious: () -> Unit = {},
    onVerifyClick: (String) -> Unit = {},
    onResendOtp: () -> Unit = {}
) {
    var otp by remember { mutableStateOf("") }
    var otpTimeout by remember { mutableStateOf(59) }

    val outerHorizontalPadding = rememberOuterHorizontalPaddingExtraLarge()
    val outerVerticalPadding = rememberOuterVerticalPaddingMedium()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            MyBottomNav(
                paddingValues = paddingValues,
                text = if (isLoading) "Verifying..." else "Verify Code",
                onClick = {
                    if (!isLoading) {
                        onVerifyClick(otp)
                    }
                },
                enabled = !isLoading && otp.length == 6,
                modifier = Modifier.padding(
                    horizontal = outerHorizontalPadding,
                    vertical = outerVerticalPadding
                )
            )
        },
        topBar = {
            MyTopBar(
                paddingValues = paddingValues,
                title = "Verification",
                onLeadingClick = onPrevious,
                modifier = Modifier.padding(
                    horizontal = outerHorizontalPadding,
                    vertical = outerVerticalPadding
                )
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = outerVerticalPadding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Title and subtitle
                Text(
                    text = "Verification",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                SpacerVerticalMedium()

                Text(
                    text = label,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = outerHorizontalPadding)
                )

                emailId?.let {
                    SpacerVerticalMedium()
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center
                    )
                }

                SpacerVerticalLarge()

                // Show error message if exists
                error?.let { errorMsg ->
                    Text(
                        text = errorMsg,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = outerHorizontalPadding)
                            .padding(bottom = 16.dp)
                    )
                }

                // Loading indicator
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(bottom = 16.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Full OTP Section with resend and error handling
                OTPSection(
                    otp = otp,
                    onOtpChange = { newOtp ->
                        otp = newOtp
                    },
                    otpTimeout = otpTimeout,
                    onResendOtp = {
                        onResendOtp()
                        otpTimeout = 60
                    },
                    onNotMyNumberClick = {
                        onPrevious()
                    },
                    resetOtpTimeout = {
                        otpTimeout = 60
                    },
                    isError = error,
                    otpLength = 6,
                    otpBoxSize = 42.dp,
                    otpBoxBorderColor = if (error != null) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onBackground.copy(alpha = .5f)
                    },
                    otpBoxBackgroundColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    resendColor = MaterialTheme.colorScheme.secondary,
                    errorColor = MaterialTheme.colorScheme.error,
                    enabled = !isLoading
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewVerificationScreen() {
    MyMaterialTheme {
        VerificationScreen(
            paddingValues = PaddingValues(),
            emailId = "user@example.com",
            label = "Enter the 6-digit code sent to your email",
        )
    }
}

@Preview
@Composable
fun PreviewVerificationScreenLoading() {
    MyMaterialTheme {
        VerificationScreen(
            paddingValues = PaddingValues(),
            emailId = "user@example.com",
            label = "Enter the 6-digit code sent to your email",
            isLoading = true
        )
    }
}

@Preview
@Composable
fun PreviewVerificationScreenError() {
    MyMaterialTheme {
        VerificationScreen(
            paddingValues = PaddingValues(),
            emailId = "user@example.com",
            label = "Enter the 6-digit code sent to your email",
            error = "Invalid OTP code. Please try again."
        )
    }
}