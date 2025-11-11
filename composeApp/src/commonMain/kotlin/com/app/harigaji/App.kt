package com.app.harigaji

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.app.harigaji.artical.ArticlesViewModel
import com.app.harigaji.chat.ChatViewModel
import com.app.harigaji.core.auth.AuthViewModel
import com.app.harigaji.core.shared.SharedViewModel
import com.app.harigaji.core.uiconfig.UiConfigViewModel
import com.app.harigaji.core.user.UserViewModel
import com.app.harigaji.navigation.NavScreen
import com.app.harigaji.presentation.uiconfig.FloatingControlButton
import com.app.harigaji.presentation.uiconfig.FloatingUiControlPanel
import com.app.harigaji.theme.AppTheme
import com.app.harigaji.theme.LocalUiConfig
import com.app.harigaji.theme.LocalOutlineMode
import androidx.compose.runtime.CompositionLocalProvider
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.roundToInt

@Composable
fun Modifier.draggable(
    containerSize: IntSize,
    onDrag: (Offset) -> Unit
): Modifier = this.pointerInput(Unit) {
    detectDragGestures { change, dragAmount ->
        change.consume()
        onDrag(dragAmount)
    }
}

@Composable
@Preview
fun App() {
    val sharedViewModel = koinViewModel<SharedViewModel>()
    val userViewModel = koinViewModel<UserViewModel>()
    val authViewModel = koinViewModel<AuthViewModel>()
    val chatViewModel = koinViewModel<ChatViewModel>()
    val articlesViewModel = koinViewModel<ArticlesViewModel>()
    val uiConfigViewModel = koinViewModel<UiConfigViewModel>()

    val uiConfig by uiConfigViewModel.uiConfig.collectAsState()
    val enableOutlineDebug = uiConfigViewModel.isOutlineModeEnabled

    fun triggerDummyCrash() {
        throw RuntimeException("💥 Dummy crash for Firebase Crashlytics testing")
    }

    var containerSize by remember { mutableStateOf(IntSize.Zero) }
    var buttonSize by remember { mutableStateOf(IntSize.Zero) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    val density = LocalDensity.current
    val paddingPx = with(density) { 16.dp.toPx() }

    // Initialize position at bottom-end
    LaunchedEffect(containerSize, buttonSize) {
        if (containerSize != IntSize.Zero && buttonSize != IntSize.Zero && offset == Offset.Zero) {
            offset = Offset(
                x = (containerSize.width - buttonSize.width - paddingPx).coerceAtLeast(0f),
                y = (containerSize.height - buttonSize.height - paddingPx).coerceAtLeast(0f)
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .onGloballyPositioned { coordinates ->
                containerSize = coordinates.size
            }
    ) {
        CompositionLocalProvider(
            LocalUiConfig provides uiConfig,
            LocalOutlineMode provides enableOutlineDebug
        ) {
            AppTheme(config = uiConfig) {
                NavScreen(
                    authViewModel,
                    userViewModel,
                    chatViewModel,
                    articlesViewModel,
                    sharedViewModel
                )
            }
        }

        FloatingUiControlPanel(
            viewModel = uiConfigViewModel,
            modifier = Modifier.fillMaxSize()
        )

        // Draggable Floating Control Button
        Box(
            modifier = Modifier
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                .onGloballyPositioned { coordinates ->
                    buttonSize = coordinates.size
                }
                .draggable(containerSize) { dragAmount ->
                    val newOffset = offset + dragAmount

                    // Constrain within bounds
                    offset = Offset(
                        x = newOffset.x.coerceIn(0f, (containerSize.width - buttonSize.width).toFloat()),
                        y = newOffset.y.coerceIn(0f, (containerSize.height - buttonSize.height).toFloat())
                    )
                }
        ) {
            FloatingControlButton(
                viewModel = uiConfigViewModel,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}