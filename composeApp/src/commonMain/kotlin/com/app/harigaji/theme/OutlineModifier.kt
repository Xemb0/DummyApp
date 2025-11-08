package com.app.harigaji.theme

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * CompositionLocal to provide outline mode state throughout the app
 */
val LocalOutlineMode = compositionLocalOf<Boolean> { false }

/**
 * Helper function to get outline mode state
 */
@Composable
fun rememberOutlineMode(): Boolean {
    return LocalOutlineMode.current
}

/**
 * Extension function to add outline border when outline mode is enabled
 * Similar to CSS: outline: 2px solid red
 */
@Composable
fun Modifier.outlineIfEnabled(): Modifier {
    val isEnabled = rememberOutlineMode()
    return if (isEnabled) {
        this.border(
            width = 2.dp,
            color = Color.Red,
            shape = RoundedCornerShape(0.dp)
        )
    } else {
        this
    }
}

/**
 * Extension function to add outline border with custom width
 */
@Composable
fun Modifier.outlineIfEnabled(width: androidx.compose.ui.unit.Dp): Modifier {
    val isEnabled = rememberOutlineMode()
    return if (isEnabled) {
        this.border(
            width = width,
            color = Color.Red,
            shape = RoundedCornerShape(0.dp)
        )
    } else {
        this
    }
}

/**
 * Global wrapper that applies outline to all layouts automatically
 * Wrap your entire app content with this to see all layout boundaries
 */
@Composable
fun OutlineDebugWrapper(
    enabled: Boolean,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier.outlineIfEnabled(if (enabled) 1.dp else 0.dp)
    ) {
        content()
    }
}

