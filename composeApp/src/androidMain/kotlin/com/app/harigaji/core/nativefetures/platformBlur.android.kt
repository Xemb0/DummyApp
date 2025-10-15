package com.app.harigaji.core.nativefetures

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp

// AndroidMain source set
actual fun Modifier.platformBlur(radius: Dp): Modifier {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        this.graphicsLayer {

             RenderEffect.createBlurEffect(radius.value, 0f,
                Shader.TileMode.CLAMP)
                 .asComposeRenderEffect()
        }
    } else {
        this
    }
}