package com.app.harigaji.theme

import androidx.compose.foundation.border
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
 * Simple debug modifier - just add .debugUi() to any composable modifier
 * Similar to CSS: outline: 2px solid red
 * 
 * Usage:
 * Box(modifier = Modifier.fillMaxWidth().debugUi()) { ... }
 * Column(modifier = Modifier.debugUi()) { ... }
 */
@Composable
fun Modifier.debugUi(): Modifier {
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
 * Debug modifier with custom width
 */
@Composable
fun Modifier.debugUi(width: androidx.compose.ui.unit.Dp): Modifier {
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

