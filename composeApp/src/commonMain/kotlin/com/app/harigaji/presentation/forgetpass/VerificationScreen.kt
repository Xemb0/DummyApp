package com.app.harigaji.presentation.forgetpass


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.app.harigaji.data.UserProgressDetail
import com.app.harigaji.presentation.ScreenHeader
import com.app.harigaji.presentation.tabs.profile.CustomTextField
import com.app.harigaji.presentation.tabs.profile.OTPSection
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_curve_back
import harigaji.composeapp.generated.resources.ic_envelop
import harigaji.composeapp.generated.resources.ic_invalid
import harigaji.composeapp.generated.resources.ic_lock
import harigaji.composeapp.generated.resources.ic_profile
import io.github.ismoy.imagepickerkmp.domain.config.CameraCaptureConfig
import io.github.ismoy.imagepickerkmp.domain.config.ImagePickerConfig
import io.github.ismoy.imagepickerkmp.domain.config.PermissionAndConfirmationConfig
import io.github.ismoy.imagepickerkmp.domain.models.CompressionLevel
import io.github.ismoy.imagepickerkmp.domain.models.GalleryPhotoResult
import io.github.ismoy.imagepickerkmp.domain.models.MimeType
import io.github.ismoy.imagepickerkmp.domain.models.PhotoResult
import io.github.ismoy.imagepickerkmp.presentation.ui.components.GalleryPickerLauncher
import io.github.ismoy.imagepickerkmp.presentation.ui.components.ImagePickerLauncher
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import kotlinx.datetime.*
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun ChangePasswordScreen(
    paddingValues: PaddingValues,
    userProgressDetail: UserProgressDetail?,
    onPrevious: () -> Unit = {},
    onNext: (String, String) -> Unit = {_,_ ->}
) {

    var pass by remember { mutableStateOf(TextFieldValue("")) }
    var confirmPass by remember { mutableStateOf(TextFieldValue("")) }

    var confirmpass by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var agreeToTerms by remember { mutableStateOf(false) }
    var confirmpassError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmpassErrorMessage by remember { mutableStateOf("") }
    var passwordErrorMessage by remember { mutableStateOf("") }
    // Constants for validation
    val MAX_confirmpass_LENGTH = 50
    val MAX_PASSWORD_LENGTH = 20
    val MIN_PASSWORD_LENGTH = 8

    fun validatePassword(password: String): Pair<Boolean, String> {
        return when {
            password.isEmpty() -> Pair(false, "Password cannot be empty")
            password.length < MIN_PASSWORD_LENGTH ->
                Pair(false, "Password must be at least $MIN_PASSWORD_LENGTH characters")
            password.length > MAX_PASSWORD_LENGTH -> Pair(false, "Password is too long")
            else -> Pair(true, "")
        }
    }


    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Button(
                onClick = {
                    val confirmpassValidation = validatePassword(confirmpass)
                    val passwordValidation = validatePassword(password)

                    confirmpassError = !confirmpassValidation.first
                    confirmpassErrorMessage = confirmpassValidation.second

                    passwordError = !passwordValidation.first
                    passwordErrorMessage = passwordValidation.second

                    if (confirmpassValidation.first && passwordValidation.first) {
                        onNext(confirmpass, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = paddingValues.calculateBottomPadding())
                    .padding(horizontal = 16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                ),
                shape = CircleShape
            ) {
                Text(
                    text = "Save Changes",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        topBar = {
            ScreenHeader(
                title = "Change Password",
                onBackClick = onPrevious,
//                paddingValues = paddingValues
            )
        },
    ) { pv ->

        var otp by remember { mutableStateOf("") }
        var otpTimeout by remember { mutableStateOf(59) }
        var errorMessage by remember { mutableStateOf<String?>(null) }

        Box(

            modifier = Modifier
                .padding(pv)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 0.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.Start,
            ) {

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
                            painter = painterResource(Res.drawable.ic_lock),
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
                        cursorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        selectionColors = TextSelectionColors(
                            handleColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            backgroundColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                        )
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

                Text(
                    text = "✔ There must be at least 8 characters",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "✔ There must be a unique code like @!#\n",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center
                )
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
                            painter = painterResource(Res.drawable.ic_lock),
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
                        cursorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        selectionColors = TextSelectionColors(
                            handleColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            backgroundColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                        )
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
            }
        }
    }
}