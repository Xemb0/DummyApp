package com.app.harigaji.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.core.customcomposables.ConvexBottomCard
import com.app.harigaji.core.customcomposables.gradients.darkSecondaryGradient
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_envelop
import harigaji.composeapp.generated.resources.ic_farward
import harigaji.composeapp.generated.resources.ic_invalid
import harigaji.composeapp.generated.resources.logo_app
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

// Language data class
data class Language(val flag: String, val name: String)

@Composable
fun LoginScreen(
    onSignIn: (String, String) -> Unit = { _, _ -> },
    onSignUp: () -> Unit = {},
) {
    var showTopLogo by remember { mutableStateOf(false) }
    var showMainLogo by remember { mutableStateOf(false) }
    var showButton by remember { mutableStateOf(false) }
    var arcAnimationStarted by remember { mutableStateOf(false) }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var agreeToTerms by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var emailErrorMessage by remember { mutableStateOf("") }
    var passwordErrorMessage by remember { mutableStateOf("") }

    // Language selection
    var isLanguageDropdownExpanded by remember { mutableStateOf(false) }
    val languages = remember {
        listOf(
            Language("🇬🇧", "English"),
            Language("🇮🇩", "Bahasa Indonesia"),
            Language("🇮🇳", "हिन्दी"),
            Language("🇪🇸", "Español"),
            Language("🇫🇷", "Français"),
            Language("🇩🇪", "Deutsch"),
            Language("🇯🇵", "日本語"),
            Language("🇨🇳", "中文")
        )
    }
    var selectedLanguage by remember { mutableStateOf(languages[0]) }

    // Constants for validation
    val MAX_EMAIL_LENGTH = 50
    val MAX_PASSWORD_LENGTH = 20
    val MIN_PASSWORD_LENGTH = 8

    // Email validation function
    fun validateEmail(email: String): Pair<Boolean, String> {
        return when {
            email.isEmpty() -> Pair(false, "Email cannot be empty")
            email.length > MAX_EMAIL_LENGTH -> Pair(false, "Email is too long")
            !email.contains("@") -> Pair(false, "Invalid email format")
            !email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) ->
                Pair(false, "Invalid email address")
            else -> Pair(true, "")
        }
    }

    // Password validation function
    fun validatePassword(password: String): Pair<Boolean, String> {
        return when {
            password.isEmpty() -> Pair(false, "Password cannot be empty")
            password.length < MIN_PASSWORD_LENGTH ->
                Pair(false, "Password must be at least $MIN_PASSWORD_LENGTH characters")
            password.length > MAX_PASSWORD_LENGTH -> Pair(false, "Password is too long")
            else -> Pair(true, "")
        }
    }

    LaunchedEffect(Unit) {
        arcAnimationStarted = true
        delay(200)
        showMainLogo = true
        showTopLogo = true
        delay(300)
        showButton = true
    }

    val scrollState = remember { mutableStateOf(0) }
    val atTop by remember { derivedStateOf { scrollState.value < 20 } }

    val arcHeight by animateFloatAsState(
        targetValue = if (atTop && arcAnimationStarted) 150f else 0f,
        animationSpec = tween(2000),
        label = "arcHeightAnim"
    )
    val scale = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        launch {
            scale.animateTo(
                targetValue = 1.1f,
                animationSpec = tween(durationMillis = 30000)
            )
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxWidth(),
        topBar = {
            ConvexBottomCard(
                corners = 0.dp,
                elevation = 2.dp,
                arcHeight = arcHeight,
                paddingBottom = 0.dp,
                background = darkSecondaryGradient,
                modifier = Modifier.wrapContentSize(),
                color = MaterialTheme.colorScheme.secondary
            ) {
                Column(
                    modifier = Modifier.wrapContentSize().fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    AnimatedVisibility(
                        visible = showTopLogo,
                        enter = fadeIn(
                            animationSpec = tween(1000)
                        ) + slideInVertically(
                            initialOffsetY = { -it / 2 },
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.logo_app),
                            contentDescription = "App Logo",
                            modifier = Modifier.padding(vertical = 48.dp)
                                .scale(scale.value)
                        )
                    }
                }
            }
        }
    ) { pv ->
        Box(
            modifier = Modifier
                .padding(pv)
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))

                // Title and subtitle
                Text(
                    text = "Login to Your Account",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Get salary in advance.",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Email Address Field
                Text(
                    text = "Email Address",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { value ->
                        if (value.length <= MAX_EMAIL_LENGTH) {
                            email = value
                            emailError = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth().dropShadow(
                        shape = CircleShape,
                        shadow = Shadow(
                            offset = DpOffset(0.dp, 0.dp),
                            radius = 6.dp,
                            spread = 3.dp,
                            color = if (emailError) {
                                MaterialTheme.colorScheme.error.copy(alpha = 0.3f)
                            } else {
                                Color(0x40000000)
                            }
                        )
                    ),
                    placeholder = {
                        Text(
                            "Enter your email...",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_envelop),
                            contentDescription = "Email Icon",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                    },

                    shape = CircleShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.White,
                        focusedBorderColor = if (emailError) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.secondary
                        },
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        cursorColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    singleLine = true,
                    isError = emailError
                )

                Row (
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.Start
                ){

                Text(
                    text = "${email.length}/$MAX_EMAIL_LENGTH",
                    fontSize = 12.sp,
                    color = if (email.length >= MAX_EMAIL_LENGTH) {
                        MaterialTheme.colorScheme.error
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    }
                )
                }

                if (emailError) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.errorContainer)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_invalid),
                            contentDescription = "Invalid Icon",
                            tint = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = emailErrorMessage,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Password Field
                Text(
                    text = "Password",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { value ->
                        if (value.length <= MAX_PASSWORD_LENGTH) {
                            password = value
                            passwordError = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth().dropShadow(
                        shape = CircleShape,
                        shadow = Shadow(
                            radius = 6.dp,
                            spread = 3.dp,
                            color = if (passwordError) {
                                MaterialTheme.colorScheme.error.copy(alpha = 0.3f)
                            } else {
                                Color(0x40000000)
                            }
                        )
                    ),
                    placeholder = {
                        Text(
                            "Enter your password...",
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
                            painter = painterResource(Res.drawable.ic_envelop),
                            contentDescription = "Password Icon",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    shape = CircleShape,
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.White,
                        focusedBorderColor = if (passwordError) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.secondary
                        },
                        unfocusedContainerColor = Color.White,
                        focusedContainerColor = Color.White,
                        cursorColor = MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    singleLine = true,
                    isError = passwordError
                )

                Row (
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
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
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.errorContainer)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_invalid),
                            contentDescription = "Invalid Icon",
                            tint = MaterialTheme.colorScheme.onErrorContainer,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = passwordErrorMessage,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Language Selection
                Text(
                    text = "Select Your Language",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Language Dropdown
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color.White)
                            .clickable { isLanguageDropdownExpanded = !isLanguageDropdownExpanded }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = selectedLanguage.flag,
                                fontSize = 20.sp
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Text(
                                text = selectedLanguage.name,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown arrow",
                            tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                            modifier = Modifier.rotate(if (isLanguageDropdownExpanded) 180f else 0f)
                        )
                    }

                    // Dropdown Menu
                    AnimatedVisibility(
                        visible = isLanguageDropdownExpanded,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                languages.forEach { language ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                selectedLanguage = language
                                                isLanguageDropdownExpanded = false
                                            }
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = language.flag,
                                            fontSize = 20.sp
                                        )
                                        Spacer(modifier = Modifier.width(12.dp))
                                        Text(
                                            text = language.name,
                                            fontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onBackground,
                                            modifier = Modifier.weight(1f)
                                        )
                                        if (selectedLanguage == language) {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Selected",
                                                tint = MaterialTheme.colorScheme.secondary,
                                                modifier = Modifier.size(20.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Terms and Conditions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier
                            .size(20.dp)
                            .border(
                                width = 2.dp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                shape = CircleShape
                            )
                            .padding(2.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = buildAnnotatedString {
                            append("By signing in to this HariGaji app, You agree with the ")
                            withStyle(
                                style = SpanStyle(
                                    color = MaterialTheme.colorScheme.secondary,
                                    fontWeight = FontWeight.Bold
                                )
                            ) {
                                append("Terms & Conditions")
                            }
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W900,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
                        lineHeight = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Sign In Button
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(CircleShape)
                        .background(darkSecondaryGradient)
                        .height(56.dp),
                    onClick = {
                        val emailValidation = validateEmail(email)
                        val passwordValidation = validatePassword(password)

                        emailError = !emailValidation.first
                        emailErrorMessage = emailValidation.second

                        passwordError = !passwordValidation.first
                        passwordErrorMessage = passwordValidation.second

                        if (emailValidation.first && passwordValidation.first) {
                            onSignIn(email, password)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    shape = MaterialTheme.shapes.large
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            "Sign In",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.width(12.dp))
                        Icon(
                            painter = painterResource(Res.drawable.ic_farward),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sign Up Link
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Don't have an account? ",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                    Text(
                        text = "Sign Up",
                        fontSize = 14.sp,
                        color = Color(0xFFFF6B6B),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable { onSignUp() }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
@Preview
fun LoginScreenPreview2() {
    MaterialTheme {
        LoginScreen()
    }
}