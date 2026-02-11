package com.app.harigaji.presentation.tabs.profile

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
import com.app.harigaji.presentation.MyBottomNav
import com.app.harigaji.presentation.MyTopBar
import com.app.harigaji.theme.SpacerVerticalMedium
import com.app.harigaji.theme.rememberCornerRadiusLarge
import com.app.harigaji.theme.rememberOuterHorizontalPaddingExtraLarge
import com.app.harigaji.theme.rememberOuterVerticalPaddingMedium
import com.app.harigaji.theme.rememberSizeBig
import com.app.harigaji.theme.rememberSizeExtraBig
import com.app.harigaji.theme.rememberSizeLarge
import com.app.harigaji.theme.rememberSizeMedium
import com.app.harigaji.theme.debugUi
import com.darkrockstudios.libraries.mpfilepicker.FilePicker
import harigaji.composeapp.generated.resources.Res
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
import kotlinx.datetime.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
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
            MyBottomNav(
                paddingValues = paddingValues,
                text = "Update profile",
                onClick = {}
            )
        },
        topBar = {
            MyTopBar(
                title = "Profile",
                onLeadingClick = onPrevious,
                paddingValues = paddingValues,
                modifier = Modifier.padding(horizontal = rememberOuterHorizontalPaddingExtraLarge(), vertical = rememberOuterVerticalPaddingMedium()).debugUi(),
            )
        },
    ) { pv ->
        Box(
            modifier = Modifier
                .padding(pv)
                .debugUi()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = rememberOuterHorizontalPaddingExtraLarge())
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
                    .debugUi()
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .debugUi(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Profile Picture with Edit Button
                    Box(
                        modifier = Modifier.debugUi(),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(Color(0xFFFFCDD2))
                                .debugUi(),
                            contentAlignment = Alignment.Center
                        ) {
                            userProgressDetail?.profilePic?.let {
                                if(it.isEmpty()){
                                    Image(
                                        painter = painterResource(Res.drawable.ic_profile),
                                        contentDescription = "Profile Picture",
                                        modifier = Modifier.size(rememberSizeExtraBig()).debugUi(),
                                    )
                                    return@let
                                }
                                AsyncImage(
                                    model = it,
                                    contentDescription = "Avatar",
                                    modifier = Modifier
                                        .size(rememberSizeBig())
                                        .clip(rememberCornerRadiusLarge())
                                        .debugUi()
                                )
                            } ?:  Image(
                                painter = painterResource(Res.drawable.ic_profile),
                                contentDescription = "Profile Picture",
                                modifier = Modifier.size(rememberSizeBig()).debugUi(),
                            )
                        }

                        // Edit Button
                        FloatingActionButton(
                            onClick = {
                                showGallery = true
                            },
                            modifier = Modifier
                                .size(rememberSizeLarge())
                                .offset(x = (-4).dp, y = (-4).dp)
                                .debugUi(),
                            containerColor = MaterialTheme.colorScheme.secondary,
                            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit Photo",
                                tint = Color.White,
                                modifier = Modifier.size(rememberSizeMedium()).debugUi()
                            )
                        }
                    }

                    SpacerVerticalMedium()

                    // First Name Field
                    CustomTextField(
                        label = "First Name",
                        value = firstName,
                        onValueChange = { firstName = it }
                    )
                    SpacerVerticalMedium()


                    // Last Name Field
                    CustomTextField(
                        label = "Last Name",
                        value = lastName,
                        onValueChange = { lastName = it }
                    )
                    SpacerVerticalMedium()

                    // Email Field
                    CustomTextField(
                        label = "Email",
                        value = email,
                        onValueChange = { email = it },
                        textColor = Color(0xFF2196F3)
                    )
                    SpacerVerticalMedium()

                    // Date of Birth Field with DatePicker
                    DatePickerField(
                        label = "Date of Birth",
                        selectedDate = formattedDate,
                        onClick = { showDatePicker = true }
                    )

                    Spacer(modifier = Modifier.height(124.dp).debugUi())
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
                            modifier = Modifier.debugUi(),
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.secondary
                            )
                        ) {
                            Text(
                                "OK",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.debugUi()
                            )
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = { showDatePicker = false },
                            modifier = Modifier.debugUi(),
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
                            )
                        ) {
                            Text(
                                "Cancel",
                                modifier = Modifier.debugUi()
                            )
                        }
                    },
                    colors = DatePickerDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        headlineContentColor = MaterialTheme.colorScheme.secondary,
                        weekdayContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        subheadContentColor = MaterialTheme.colorScheme.onSurface,
                        yearContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                        currentYearContentColor = MaterialTheme.colorScheme.secondary,
                        selectedYearContentColor = MaterialTheme.colorScheme.onSecondary,
                        selectedYearContainerColor = MaterialTheme.colorScheme.secondary,
                        dayContentColor = MaterialTheme.colorScheme.onSurface,
                        disabledDayContentColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                        selectedDayContentColor = MaterialTheme.colorScheme.onSecondary,
                        disabledSelectedDayContentColor = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.38f),
                        selectedDayContainerColor = MaterialTheme.colorScheme.secondary,
                        disabledSelectedDayContainerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.38f),
                        todayContentColor = MaterialTheme.colorScheme.secondary,
                        todayDateBorderColor = MaterialTheme.colorScheme.secondary,
                        dayInSelectionRangeContentColor = MaterialTheme.colorScheme.onSurface,
                        dayInSelectionRangeContainerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.12f),

                    )
                ) {
                    DatePicker(
                        colors = DatePickerDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            titleContentColor = MaterialTheme.colorScheme.onSurface,
                            selectedDayContainerColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        state = datePickerState,
                        modifier = Modifier.debugUi()
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
    Column(modifier = modifier.fillMaxWidth().debugUi()) {
        // Label
        Text(
            text = label,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth().debugUi()
        )

        Spacer(modifier = Modifier.height(8.dp).debugUi())

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
                )
                .debugUi(),
            placeholder = {
                Text(
                    "Select date of birth...",
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                    modifier = Modifier.debugUi()
                )
            },
            trailingIcon = {
                IconButton(
                    onClick = onClick,
                    modifier = Modifier.debugUi()
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Select Date",
                        tint = Color.Gray,
                        modifier = Modifier.debugUi()
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
    Column(modifier = modifier.fillMaxWidth().debugUi()) {
        // Label


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .debugUi(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f).debugUi()
            )
            Text(
                text = "${value.text.length}/$maxLength",
                fontSize = 10.sp,
                color = if (value.text.length >= maxLength) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                },
                modifier = Modifier.debugUi()
            )
        }

        SpacerVerticalMedium()
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
                )
                .debugUi(),
            placeholder = {
                Text(
                    placeholder,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                    modifier = Modifier.debugUi()
                )
            },
            leadingIcon = leadingIconRes?.let {
                {
                    Icon(
                        painter = painterResource(it),
                        contentDescription = "$label Icon",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(24.dp).debugUi()
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
        SpacerVerticalMedium()



        // Error Message
        if (isError && errorMessage.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.errorContainer)
                    .debugUi(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Error Icon",
                    tint = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.size(rememberSizeMedium()).debugUi()
                )
                Text(
                    text = errorMessage,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.debugUi()
                )
            }
        }
    }
}