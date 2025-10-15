package com.app.harigaji.core.nativefetures

import androidx.compose.ui.graphics.Color


actual fun String.toColor(): Color {
    return try {
        val hex = removePrefix("#")
        val intColor = hex.toLong(16)
        val red = ((intColor shr 16) and 0xFF) / 255f
        val green = ((intColor shr 8) and 0xFF) / 255f
        val blue = (intColor and 0xFF) / 255f
        Color(red, green, blue, 1f)
    } catch (e: Exception) {
        Color.Gray // Default fallback color
    }
}
