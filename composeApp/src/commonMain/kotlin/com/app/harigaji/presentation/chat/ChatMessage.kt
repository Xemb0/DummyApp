package com.app.harigaji.presentation.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.app.harigaji.chat.ChatMessage
import com.app.harigaji.chat.UserMessageDetails
import com.app.harigaji.presentation.MyTopBar
import com.app.harigaji.theme.SpacerVerticalSmall
import com.app.harigaji.theme.debugUi
import com.app.harigaji.theme.rememberCornerRadiusLarge
import com.app.harigaji.theme.rememberCornerRadiusMediumDp
import com.app.harigaji.theme.rememberInnerHorizontalPaddingLarge
import com.app.harigaji.theme.rememberInnerVerticalPaddingMedium
import com.app.harigaji.theme.rememberOuterHorizontalPaddingExtraLarge
import com.app.harigaji.theme.rememberPaddingSmall
import com.app.harigaji.theme.rememberSizeExtraLarge
import com.app.harigaji.theme.rememberSizeLarge
import com.app.harigaji.theme.rememberSizeMedium
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_profile
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    paddingValues: PaddingValues,
    userMessageDetails: UserMessageDetails? = null,
    onBackClick: () -> Unit = {},
    onSendMessage: (String) -> Unit = {}
) {

    if (userMessageDetails == null) {
        return
    }
    var messageText by remember { mutableStateOf("") }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            MyTopBar(
                paddingValues = paddingValues,
                title = userMessageDetails.sender,
                onLeadingClick = onBackClick,
                modifier = Modifier.padding(horizontal = rememberOuterHorizontalPaddingExtraLarge())
                    .debugUi()
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth().debugUi(),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp,
                shape = RoundedCornerShape(
                    topStart = rememberCornerRadiusMediumDp(),
                    topEnd = rememberCornerRadiusMediumDp()
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = paddingValues.calculateBottomPadding())
                        .padding(
                            horizontal = rememberInnerHorizontalPaddingLarge(),
                            vertical = rememberInnerVerticalPaddingMedium()
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(
                        onClick = { /* Handle camera */ },
                        modifier = Modifier.size(rememberSizeMedium()).debugUi()
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Camera",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    IconButton(
                        onClick = { /* Handle attachment */ },
                        modifier = Modifier
                            .size(rememberSizeMedium())
                            .clip(rememberCornerRadiusLarge())
                    ) {
                        Icon(
                            imageVector = Icons.Default.AttachFile,
                            contentDescription = "Attach",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    OutlinedTextField(
                        value = messageText,
                        onValueChange = { messageText = it },
                        modifier = Modifier
                            .weight(1f)
                            .heightIn(min = 48.dp, max = 120.dp),
                        placeholder = {
                            Text(
                                text = "Message",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 15.sp
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
                            focusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .5f),
                            disabledBorderColor = Color.Transparent,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            cursorColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            selectionColors = TextSelectionColors(
                                handleColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                backgroundColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = .1f)
                            )
                            ),
                        shape = RoundedCornerShape(25.dp),
                        singleLine = false,
                        maxLines = 5
                    )

                    Box(
                        modifier = Modifier
                            .size(rememberSizeExtraLarge())
                            .clip(rememberCornerRadiusLarge())
                            .clickable {
                                if (messageText.isNotBlank()) {
                                    onSendMessage(messageText)
                                    messageText = ""
                                }
                            }
                            .background(MaterialTheme.colorScheme.secondary)
                            .innerShadow(
                                shape =rememberCornerRadiusLarge(),
                                shadow = Shadow(
                                    offset = DpOffset(0.dp, 0.dp),
                                    radius = 4.dp,
                                    spread = 2.dp,
                                    color = Color(0x33000000)
                                )
                            ).debugUi()
                                ,
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .size(rememberSizeLarge())
                                .padding(rememberPaddingSmall())
                        )
                    }
                }
            }
        }
    ) { pv ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv)
                .padding(horizontal = rememberOuterHorizontalPaddingExtraLarge()).debugUi(),
            reverseLayout = false,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            items(userMessageDetails.message ?: emptyList()) { message ->
                ChatMessageItem(message = message)
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ChatMessageItem(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth().debugUi(),
        horizontalArrangement = if (message.isSent) Arrangement.End else Arrangement.Start
    ) {
        if (!message.isSent) {
            // Avatar for received messages
            Surface(
                modifier = Modifier.size(40.dp),
                shape = CircleShape,
                color = MaterialTheme.colorScheme.primaryContainer,
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    message.avatarUrl?.let {
                        AsyncImage(
                            model = it,
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } ?: run {
                        Image(
                            painter = painterResource(Res.drawable.ic_profile),
                            contentDescription = "Avatar",
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            horizontalAlignment = if (message.isSent) Alignment.End else Alignment.Start,
            modifier = Modifier.widthIn(max = 280.dp).debugUi()
        ) {
            Surface(
                color = if (message.isSent) {
                    MaterialTheme.colorScheme.surface
                } else {
                    MaterialTheme.colorScheme.secondary.copy(alpha = .1f)
                },
                modifier = Modifier
                    .clip(RoundedCornerShape(
                        topStart = if (message.isSent) 16.dp else 4.dp,
                        topEnd = if (message.isSent) 4.dp else 16.dp,
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp
                    ))
                    .innerShadow(
                        shape = RoundedCornerShape(
                            topStart = if (message.isSent) 16.dp else 4.dp,
                            topEnd = if (message.isSent) 4.dp else 16.dp,
                            bottomStart = 16.dp,
                            bottomEnd = 16.dp
                        ),
                        shadow = Shadow(
                            offset = DpOffset(0.dp, 0.dp),
                            radius = 6.dp,
                            spread = 3.dp,
                            color = Color(0x33000000)
                        )
                    )
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp).debugUi()
                ){
                    Text(
                        text = message.message,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 20.sp
                    )
                    Text(
                        text = message.time,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = .6f),
                        modifier = Modifier.fillMaxWidth(),
                        fontWeight = FontWeight.Medium,
                        textAlign = if (message.isSent) TextAlign.End else TextAlign.Start,
                    )
                }
            }

            SpacerVerticalSmall()
        }

        if (message.isSent) {
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    val sampleMessages = listOf(
        ChatMessage(
            message = "Hello!",
            time = "10:00 AM",
            isSent = false,
            avatarUrl = null,
            id = 1
        ),
        ChatMessage(
            message = "Hi there",
            time = "10:01 AM",
            isSent = true,
            avatarUrl = null,
            id = 2
        ),
        ChatMessage(
            message = "How are you?",
            time = "10:02 AM",
            isSent = false,
            avatarUrl = null,
            id = 3
        )
    )
    val userDetails = UserMessageDetails(
        sender = "Alice",
        message = sampleMessages,
        date = "234-345-2",
        time = "9:3"
    )

    MaterialTheme {
        ChatScreen(
            paddingValues = PaddingValues(0.dp),
            userMessageDetails = userDetails,
            onBackClick = {},
            onSendMessage = {}
        )
    }
}