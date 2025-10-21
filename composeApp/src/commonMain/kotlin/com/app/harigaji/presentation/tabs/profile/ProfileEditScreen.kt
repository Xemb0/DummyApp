package com.app.harigaji.presentation.tabs.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.app.harigaji.data.UserProgressDetail
import com.app.harigaji.presentation.ScreenHeader
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_curve_back
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
fun ProfileEditScreen(
    paddingValues: PaddingValues,
    userProgressDetail: UserProgressDetail?,
    onPrevious: () -> Unit = {},
    onSaveChanges: () -> Unit = {}
) {
    var firstName by remember { mutableStateOf(TextFieldValue(userProgressDetail?.firstName ?: "")) }
    var lastName by remember { mutableStateOf(TextFieldValue(userProgressDetail?.lastName ?: "")) }
    var email by remember { mutableStateOf(TextFieldValue(userProgressDetail?.email ?: "")) }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showFilePicker by remember { mutableStateOf(false) }

    val fileType = listOf("jpg", "png")
    FilePicker(show = showFilePicker, fileExtensions = fileType) { platformFile ->
        showFilePicker = false
        // do something with the file
    }

    var showCamera by remember { mutableStateOf(false) }
    var capturedPhoto by remember { mutableStateOf<PhotoResult?>(null) }
    var showGallery by remember { mutableStateOf(false) }
    var selectedImages by remember { mutableStateOf<List<GalleryPhotoResult>>(emptyList()) }

    // Format date using kotlinx-datetime
    val formattedDate = remember(selectedDateMillis) {
        selectedDateMillis?.let {
            val instant = Instant.fromEpochMilliseconds(it)
            val localDate = instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
            val month = localDate.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
            "${month} ${localDate.dayOfMonth}, ${localDate.year}"
        } ?: ""
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Button(
                onClick = {
                    onSaveChanges()
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
                    text = "Reset Password",
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
                title = "Profile",
                onBackClick = onPrevious,
                paddingValues = paddingValues
            )
        },
    ) { pv ->
        Box(
            modifier = Modifier
                .padding(pv)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Profile Picture with Edit Button
                    Box(
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color(0xFFFFCDD2)),
                            contentAlignment = Alignment.Center
                        ) {
                            userProgressDetail?.profilePic?.let {
                                if(it.isEmpty()){
                                    Image(
                                        painter = painterResource(Res.drawable.ic_profile),
                                        contentDescription = "Profile Picture",
                                        modifier = Modifier.size(150.dp),
                                    )
                                    return@let
                                }
                                AsyncImage(
                                    model = it,
                                    contentDescription = "Avatar",
                                    modifier = Modifier
                                        .size(150.dp)
                                        .clip(CircleShape)
                                )
                            } ?:  Image(
                                painter = painterResource(Res.drawable.ic_profile),
                                contentDescription = "Profile Picture",
                                modifier = Modifier.size(150.dp),
                            )
                        }

                        // Edit Button
                        FloatingActionButton(
                            onClick = {
                                showGallery = true
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .offset(x = (-4).dp, y = (-4).dp),
                            containerColor = MaterialTheme.colorScheme.secondary,
                            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Photo",
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // First Name Field
                    CustomTextField(
                        label = "First Name",
                        value = firstName,
                        onValueChange = { firstName = it }
                    )


                    // Last Name Field
                    CustomTextField(
                        label = "Last Name",
                        value = lastName,
                        onValueChange = { lastName = it }
                    )

                    // Email Field
                    CustomTextField(
                        label = "Email",
                        value = email,
                        onValueChange = { email = it },
                        textColor = Color(0xFF2196F3)
                    )

                    // Date of Birth Field with DatePicker
                    DatePickerField(
                        label = "Date of Birth",
                        selectedDate = formattedDate,
                        onClick = { showDatePicker = true }
                    )

                    Spacer(modifier = Modifier.height(124.dp))
                }
            }

            // DatePicker Dialog
            if (showDatePicker) {
                val datePickerState = rememberDatePickerState(
                    initialSelectedDateMillis = selectedDateMillis ?: Clock.System.now().toEpochMilliseconds()
                )

                DatePickerDialog(
                    onDismissRequest = { showDatePicker = false },

                    confirmButton = {
                        TextButton(
                            onClick = {
                                selectedDateMillis = datePickerState.selectedDateMillis
                                showDatePicker = false
                            },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text(
                                "OK",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDatePicker = false },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Color.Gray
                            )
                        ) {
                            Text("Cancel")
                        }
                    },
                    colors = DatePickerDefaults.colors(
                        containerColor = Color.White,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        headlineContentColor = MaterialTheme.colorScheme.secondary,
                        weekdayContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        subheadContentColor = MaterialTheme.colorScheme.onSurface,
                        yearContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        currentYearContentColor = MaterialTheme.colorScheme.secondary,
                        selectedYearContentColor = Color.White,
                        selectedYearContainerColor = MaterialTheme.colorScheme.secondary,
                        dayContentColor = MaterialTheme.colorScheme.onSurface,
                        disabledDayContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                        selectedDayContentColor = Color.White,
                        disabledSelectedDayContentColor = Color.White.copy(alpha = 0.38f),
                        selectedDayContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledSelectedDayContainerColor = MaterialTheme.colorScheme.secondary.copy(
                            alpha = 0.38f
                        ),
                        todayContentColor = MaterialTheme.colorScheme.secondary,
                        todayDateBorderColor = MaterialTheme.colorScheme.secondary,
                        dayInSelectionRangeContentColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        dateTextFieldColors = TextFieldDefaults.colors(
                            focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            cursorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            selectionColors =  TextSelectionColors(
                                handleColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                backgroundColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)),
                            focusedContainerColor = MaterialTheme.colorScheme.onSurfaceVariant

                        )
                    ),
                ){                    DatePicker(
                        state = datePickerState,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            // Camera Picker
            if (showCamera) {
                ImagePickerLauncher(
                    config = ImagePickerConfig(
                        enableCrop = false,
                        onPhotoCaptured = { result ->
                            capturedPhoto = result
                            println("Camera photo size: ${result.fileSize}KB")
                            showCamera = false
                        },
                        onError = {
                            showCamera = false
                        },
                        onDismiss = {
                            showCamera = false
                        },
                        directCameraLaunch = false,
                        cameraCaptureConfig = CameraCaptureConfig(
                            compressionLevel = CompressionLevel.HIGH,
                            permissionAndConfirmationConfig = PermissionAndConfirmationConfig(
                                skipConfirmation = true
                            )
                        )
                    )
                )
            }

            // Gallery Picker
            if (showGallery) {
                GalleryPickerLauncher(
                    onPhotosSelected = { photos ->
                        selectedImages = photos
                        showGallery = false
                    },
                    onError = { error ->
                        showGallery = false
                    },
                    onDismiss = {
                        println("User cancelled or dismissed the picker")
                        showGallery = false
                    },
                    enableCrop = false,
                    allowMultiple = true,
                    mimeTypes = listOf(MimeType.IMAGE_PNG, MimeType.IMAGE_JPEG)
                )
            }
        }
    }
}

@Composable
fun DatePickerField(
    label: String,
    selectedDate: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Label
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Date Picker Field
        OutlinedTextField(
            value = selectedDate,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .dropShadow(
                    shape = CircleShape,
                    shadow = Shadow(
                        offset = DpOffset(0.dp, 0.dp),
                        radius = .3.dp,
                        spread = .5.dp,
                        alpha = .5f,
                        blendMode = BlendMode.Multiply,
                        color = Color(0x40000000)
                    )
                ),
            placeholder = {
                Text(
                    "Select date of birth...",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                )
            },
            trailingIcon = {
                IconButton(onClick = onClick) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Select Date",
                        tint = Color.Gray
                    )
                }
            },
            shape = CircleShape,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.White,
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White,
                disabledBorderColor = Color.White,
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                cursorColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            readOnly = true,
            enabled = false,
            singleLine = true
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    label: String,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Black,
    placeholder: String = "Enter ${label.lowercase()}...",
    leadingIconRes: DrawableResource? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    maxLength: Int = 100,
    isError: Boolean = false,
    errorMessage: String = "Invalid $label",
    enabled: Boolean = true,
    singleLine: Boolean = true
) {
    Column(modifier = modifier.fillMaxWidth()) {
        // Label
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Text Field
        OutlinedTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.text.length <= maxLength) {
                    onValueChange(newValue)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .dropShadow(
                    shape = CircleShape,
                    shadow = Shadow(
                        offset = DpOffset(0.dp, 0.dp),
                        radius = 4.dp,
                        spread = 2.dp,
                        color = if (isError) {
                            MaterialTheme.colorScheme.error.copy(alpha = 0.3f)
                        } else {
                            Color(0x40000000)
                        }
                    )
                ),
            placeholder = {
                Text(
                    placeholder,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
                )
            },
            leadingIcon = leadingIconRes?.let {
                {
                    Icon(
                        painter = painterResource(it),
                        contentDescription = "$label Icon",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            trailingIcon = trailingIcon,
            shape = CircleShape,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.White,
                focusedBorderColor = if (isError) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.secondary
                },
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                disabledContainerColor = Color.White.copy(alpha = 0.6f),
                cursorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                selectionColors = TextSelectionColors(
                    handleColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    backgroundColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.4f)
                )
            ),
            singleLine = singleLine,
            isError = isError,
            enabled = enabled
        )

        // Character Counter
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                text = "${value.text.length}/$maxLength",
                fontSize = 12.sp,
                color = if (value.text.length >= maxLength) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                }
            )
        }

        // Error Message
        if (isError && errorMessage.isNotEmpty()) {
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
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Error Icon",
                    tint = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = errorMessage,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
    }
}