package com.app.harigaji.presentation.forgetpass

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.harigaji.core.auth.AuthViewModel
import com.app.harigaji.core.utils.SnackBarEvent
import com.app.harigaji.data.UiEvent
import com.app.harigaji.data.UserProgressDetail
import com.app.harigaji.dialog.DialogConfig
import com.app.harigaji.dialog.DialogManager
import com.app.harigaji.dialog.DialogType
import com.app.harigaji.presentation.MyBottomNav
import com.app.harigaji.presentation.MyTopBar
import com.app.harigaji.theme.MyMaterialTheme
import com.app.harigaji.theme.SpacerVerticalLarge
import com.app.harigaji.theme.SpacerVerticalMedium
import com.app.harigaji.theme.rememberOuterHorizontalPaddingExtraLarge
import com.app.harigaji.theme.rememberOuterVerticalPaddingMedium
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_invalid
import harigaji.composeapp.generated.resources.ic_lock
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime

@Composable
fun ChangePasswordRoot(
    paddingValues: PaddingValues,
    authViewModel: AuthViewModel = koinViewModel(),
    onPrevious: () -> Unit = {},
    onGoHome: () -> Unit = {},
) {
    val authState by authViewModel.authState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        authViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    SnackBarEvent(
                        message = event.message,
                    )
                }
                is UiEvent.Auth.PasswordChanged -> {
                    DialogManager.showDialog(
                        DialogConfig(
                            type = DialogType.Success,
                            title = "Success",
                            message = "Your password has been reset successfully.",
                            confirmText = "Take me home",
                            onConfirm = {
                              onGoHome()
                            }
                        )
                    )
                }
                else -> {}
            }
        }
    }

    ChangePasswordScreen(
        paddingValues = paddingValues,
        isLoading = authState.isLoading,
        error = authState.error,
        onPrevious = onPrevious,
        onSavePassword = { newPassword, confirmPassword ->
            authViewModel.onChangePassword(newPassword, confirmPassword)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun ChangePasswordScreen(
    paddingValues: PaddingValues,
    isLoading: Boolean = false,
    error: String? = null,
    onPrevious: () -> Unit = {},
    onSavePassword: (String, String) -> Unit = { _, _ -> }
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var passwordErrorMessage by remember { mutableStateOf("") }
    var confirmPasswordErrorMessage by remember { mutableStateOf("") }

    // Constants for validation
    val MAX_PASSWORD_LENGTH = 20
    val MIN_PASSWORD_LENGTH = 8

    val outerHorizontalPadding = rememberOuterHorizontalPaddingExtraLarge()
    val outerVerticalPadding = rememberOuterVerticalPaddingMedium()

    fun validatePassword(password: String): Pair<Boolean, String> {
        return when {
            password.isEmpty() -> Pair(false, "Password cannot be empty")
            password.length < MIN_PASSWORD_LENGTH ->
                Pair(false, "Password must be at least $MIN_PASSWORD_LENGTH characters")
            password.length > MAX_PASSWORD_LENGTH ->
                Pair(false, "Password is too long")
            !password.any { it.isDigit() } ->
                Pair(false, "Password must contain at least one number")
            !password.any { it in "!@#$%^&*()_+-=[]{}|;:,.<>?" } ->
                Pair(false, "Password must contain at least one special character")
            else -> Pair(true, "")
        }
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): Pair<Boolean, String> {
        return when {
            confirmPassword.isEmpty() -> Pair(false, "Please confirm your password")
            password != confirmPassword -> Pair(false, "Passwords do not match")
            else -> Pair(true, "")
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            MyBottomNav(
                paddingValues = paddingValues,
                text = if (isLoading) "Saving..." else "Save Changes",
                onClick = {
                    if (!isLoading) {
                        val passwordValidation = validatePassword(password)
                        val confirmValidation = validateConfirmPassword(password, confirmPassword)

                        passwordError = !passwordValidation.first
                        passwordErrorMessage = passwordValidation.second

                        confirmPasswordError = !confirmValidation.first
                        confirmPasswordErrorMessage = confirmValidation.second

                        if (passwordValidation.first && confirmValidation.first) {
                            onSavePassword(password, confirmPassword)
                        }
                    }
                },
                enabled = !isLoading && password.isNotEmpty() && confirmPassword.isNotEmpty(),
                modifier = Modifier.padding(
                    horizontal = outerHorizontalPadding,
                    vertical = outerVerticalPadding
                )
            )
        },
        topBar = {
            MyTopBar(
                paddingValues = paddingValues,
                title = "Change Password",
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
            // Loading indicator
            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(48.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = outerHorizontalPadding,
                        vertical = outerVerticalPadding
                    )
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
            ) {
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

                // New Password Field
                Text(
                    text = "New Password",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )

                SpacerVerticalMedium()

                OutlinedTextField(
                    value = password,
                    onValueChange = { value ->
                        if (value.length <= MAX_PASSWORD_LENGTH) {
                            password = value
                            passwordError = false
                        }
                    },
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "Enter your new password...",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_lock),
                            contentDescription = "Password Icon",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    shape = MaterialTheme.shapes.large,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedBorderColor = if (passwordError) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.primary
                        },
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    singleLine = true,
                    isError = passwordError
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "${password.length}/$MAX_PASSWORD_LENGTH",
                        fontSize = 12.sp,
                        color = if (password.length >= MAX_PASSWORD_LENGTH) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                        }
                    )
                }

                if (passwordError) {
                    SpacerVerticalMedium()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.errorContainer)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_invalid),
                            contentDescription = "Error Icon",
                            tint = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = passwordErrorMessage,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }

                SpacerVerticalLarge()

                // Password Requirements
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f))
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Password Requirements:",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    PasswordRequirement(
                        text = "At least 8 characters",
                        isMet = password.length >= MIN_PASSWORD_LENGTH
                    )
                    PasswordRequirement(
                        text = "Contains a number",
                        isMet = password.any { it.isDigit() }
                    )
                    PasswordRequirement(
                        text = "Contains a special character (@!#\$...)",
                        isMet = password.any { it in "!@#$%^&*()_+-=[]{}|;:,.<>?" }
                    )
                }

                SpacerVerticalLarge()

                // Confirm Password Field
                Text(
                    text = "Confirm Password",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )

                SpacerVerticalMedium()

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { value ->
                        if (value.length <= MAX_PASSWORD_LENGTH) {
                            confirmPassword = value
                            confirmPasswordError = false
                        }
                    },
                    enabled = !isLoading,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "Re-enter your password...",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                imageVector = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                            )
                        }
                    },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_lock),
                            contentDescription = "Password Icon",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    shape = MaterialTheme.shapes.large,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedBorderColor = if (confirmPasswordError) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.primary
                        },
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    singleLine = true,
                    isError = confirmPasswordError
                )

                if (confirmPasswordError) {
                    SpacerVerticalMedium()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(MaterialTheme.shapes.medium)
                            .background(MaterialTheme.colorScheme.errorContainer)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_invalid),
                            contentDescription = "Error Icon",
                            tint = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = confirmPasswordErrorMessage,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }

                SpacerVerticalLarge()
            }
        }
    }
}

@Composable
fun PasswordRequirement(
    text: String,
    isMet: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Icon(
            imageVector = if (isMet) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
            contentDescription = null,
            tint = if (isMet) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = if (isMet) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }
}

@Preview
@Composable
fun PreviewChangePasswordScreen() {
    MyMaterialTheme {
        ChangePasswordScreen(
            paddingValues = PaddingValues()
        )
    }
}

@Preview
@Composable
fun PreviewChangePasswordScreenLoading() {
    MyMaterialTheme {
        ChangePasswordScreen(
            paddingValues = PaddingValues(),
            isLoading = true
        )
    }
}

@Preview
@Composable
fun PreviewChangePasswordScreenError() {
    MyMaterialTheme {
        ChangePasswordScreen(
            paddingValues = PaddingValues(),
            error = "Failed to change password. Please try again."
        )
    }
}