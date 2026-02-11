package com.app.harigaji.presentation.tabs.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.harigaji.core.auth.AuthViewModel
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
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_envelop
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime

@Composable
fun ForgetPasswordRoot(
    paddingValues: PaddingValues,
    authViewModel: AuthViewModel = koinViewModel(),
    onPrevious: () -> Unit = {},
    title: String? = null,
    onNavigateToVerification: (String) -> Unit = {},
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
                is UiEvent.Navigate.Varification -> {
                    onNavigateToVerification(event.email)
                }
                else -> {}
            }
        }
    }

    ForgetPassScreen(
        paddingValues = paddingValues,
        isLoading = authState.isLoading,
        error = authState.error,
        onPrevious = onPrevious,
        title = title,
        onResetClick = { email ->
            authViewModel.onSendResetCode(email)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun ForgetPassScreen(
    paddingValues: PaddingValues,
    isLoading: Boolean = false,
    error: String? = null,
    title: String? = null,
    onPrevious: () -> Unit = {},
    onResetClick: (String) -> Unit = {},
) {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var emailError by remember { mutableStateOf(false) }
    var emailErrorMessage by remember { mutableStateOf("") }

    val outerHorizontalPadding = rememberOuterHorizontalPaddingExtraLarge()
    val outerVerticalPadding = rememberOuterVerticalPaddingMedium()

    fun validateEmail(email: String): Pair<Boolean, String> {
        return when {
            email.isBlank() -> Pair(false, "Email cannot be empty")
            !email.contains("@") -> Pair(false, "Invalid email format")
            !email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) ->
                Pair(false, "Please enter a valid email address")
            else -> Pair(true, "")
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            MyBottomNav(
                paddingValues = paddingValues,
                text = if (isLoading) "Sending..." else "Send Reset Code",
                onClick = {
                    if (!isLoading) {
                        val validation = validateEmail(email.text)
                        emailError = !validation.first
                        emailErrorMessage = validation.second

                        if (validation.first) {
                            onResetClick(email.text)
                        }
                    }
                },
                enabled = !isLoading && email.text.isNotEmpty(),
                modifier = Modifier.padding(
                    horizontal = outerHorizontalPadding,
                    vertical = outerVerticalPadding
                )
            )
        },
        topBar = {
            MyTopBar(
                paddingValues = paddingValues,
                title = title?:"Forget Password",
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
                    .padding(
                        horizontal = outerHorizontalPadding,
                        vertical = outerVerticalPadding
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Loading indicator
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(bottom = 16.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Title and subtitle
                Text(
                    text = "Forget Password",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                SpacerVerticalMedium()

                Text(
                    text = "Enter your email address and we will send you a verification code",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )

                SpacerVerticalLarge()

                // Show global error if exists
                error?.let { errorMsg ->
                    Text(
                        text = errorMsg,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    )
                }

                CustomTextField(
                    modifier = Modifier.fillMaxWidth(),
                    trailingIcon = {},
                    label = "Email Address",
                    leadingIconRes = Res.drawable.ic_envelop,
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = false
                    },
                    isError = emailError,
                    enabled = !isLoading
                )

                SpacerVerticalLarge()

                // Helper text
                Text(
                    text = "We'll send a 6-digit verification code to your email address",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewForgetPassScreen() {
    MyMaterialTheme {
        ForgetPassScreen(
            paddingValues = PaddingValues()
        )
    }
}

@Preview
@Composable
fun PreviewForgetPassScreenLoading() {
    MyMaterialTheme {
        ForgetPassScreen(
            paddingValues = PaddingValues(),
            isLoading = true
        )
    }
}

@Preview
@Composable
fun PreviewForgetPassScreenError() {
    MyMaterialTheme {
        ForgetPassScreen(
            paddingValues = PaddingValues(),
            error = "Email not found. Please check your email address."
        )
    }
}