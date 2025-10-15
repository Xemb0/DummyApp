package com.app.harigaji.core.nativefetures

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

actual fun String.toColor(): Color {
    return try {
        Color(this.toColorInt())
    } catch (e: IllegalArgumentException) {
        Color.Gray // Default fallback color
    }
}
