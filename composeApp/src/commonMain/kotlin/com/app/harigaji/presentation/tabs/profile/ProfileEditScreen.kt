package com.app.harigaji.presentation.tabs.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.data.UserProgressDetail
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_curve_back
import harigaji.composeapp.generated.resources.ic_envelop
import harigaji.composeapp.generated.resources.ic_invalid
import harigaji.composeapp.generated.resources.ic_profile
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileEditScreen(
    paddingValues: PaddingValues,
    userProgressDetail: UserProgressDetail?,
    onPrevious: ()  -> Unit ={}
) {
    var firstName by remember { mutableStateOf(TextFieldValue(userProgressDetail?.firstName?:"")) }
    var lastName by remember { mutableStateOf(TextFieldValue(userProgressDetail?.lastName?:"")) }
    var email by remember { mutableStateOf(TextFieldValue(userProgressDetail?.email?:"")) }
    var dateOfBirth by remember { mutableStateOf(TextFieldValue(userProgressDetail?.name?:"")) }


    Scaffold(
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            Button(
                onClick = { /* Handle save */ },
                modifier = Modifier
                    .fillMaxWidth()
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedIconButton(
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant),
                    colors = IconButtonDefaults.iconButtonColors(),
                    onClick = onPrevious,
                    modifier = Modifier
                        .size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_curve_back),
                        contentDescription = "Back",
                        modifier = Modifier.size(24.dp),
                        tint =MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Profile",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        },
        bottomBar = {  }
    ) { pv ->
        Box(
            modifier = Modifier
                .padding(pv)
        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll( rememberScrollState())
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))

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
                                Image(
                                    painter = painterResource(Res.drawable.ic_profile),
                                    contentDescription = "Back",
                                    modifier = Modifier.size(150.dp),
                                )
                            }?:Image(
                                painter = painterResource(Res.drawable.ic_profile),
                                contentDescription = "Back",
                                modifier = Modifier.size(150.dp),
                            )
                        }

                        // Edit Button
                        FloatingActionButton(
                            onClick = { /* Handle image picker */ },
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


                    // First Name Field
                    ProfileTextField(
                        label = "First Name",
                        value = firstName,
                        onValueChange = { firstName = it }
                    )


                    // Last Name Field
                    ProfileTextField(
                        label = "Last Name",
                        value = lastName,
                        onValueChange = { lastName = it }
                    )


                    // Email Field
                    ProfileTextField(
                        label = "Email",
                        value = email,
                        onValueChange = { email = it },
                        textColor = Color(0xFF2196F3)
                    )


                    // Date of Birth Field
                    ProfileTextField(
                        label = "Date of Birth",
                        value = dateOfBirth,
                        onValueChange = { dateOfBirth = it },
                        trailingIcon = {
                            IconButton(onClick = { /* Show date picker */ }) {
                                Icon(
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = "Select Date",
                                    tint = Color.Gray
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(124.dp))
                }
            }
        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTextField(
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
                        radius = .3.dp,
                        spread = .5.dp,
                        alpha = .5f,
                        blendMode = BlendMode.Multiply,
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
                .padding(horizontal = 8.dp, vertical = 0.dp),
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
                    imageVector = androidx.compose.material.icons.Icons.Default.Warning,
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