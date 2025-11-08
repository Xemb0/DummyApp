package com.app.harigaji.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.app.harigaji.core.uiconfig.UiConfigEntity

/**
 * Helper functions for accessing dynamic spacing values throughout the app
 */

@Composable
fun rememberDynamicPaddingSmall(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.paddingSmall(config)
}

@Composable
fun rememberDynamicPaddingMedium(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.paddingMedium(config)
}

@Composable
fun rememberDynamicPaddingLarge(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.paddingLarge(config)
}

@Composable
fun rememberDynamicPaddingExtraLarge(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.paddingExtraLarge(config)
}

@Composable
fun rememberDynamicCornerRadiusSmall(): RoundedCornerShape {
    val config = rememberUiConfig()
    return DynamicThemeValues.roundedCornerShapeSmall(config)
}

@Composable
fun rememberDynamicCornerRadiusMedium(): RoundedCornerShape {
    val config = rememberUiConfig()
    return DynamicThemeValues.roundedCornerShapeMedium(config)
}

@Composable
fun rememberDynamicCornerRadiusLarge(): RoundedCornerShape {
    val config = rememberUiConfig()
    return DynamicThemeValues.roundedCornerShapeLarge(config)
}

// Extension functions for direct access
@Composable
fun UiConfigEntity?.paddingSmall(): Dp = DynamicThemeValues.paddingSmall(this)

@Composable
fun UiConfigEntity?.paddingMedium(): Dp = DynamicThemeValues.paddingMedium(this)

@Composable
fun UiConfigEntity?.paddingLarge(): Dp = DynamicThemeValues.paddingLarge(this)

@Composable
fun UiConfigEntity?.paddingExtraLarge(): Dp = DynamicThemeValues.paddingExtraLarge(this)

@Composable
fun UiConfigEntity?.cornerRadiusSmall(): RoundedCornerShape = DynamicThemeValues.roundedCornerShapeSmall(this)

@Composable
fun UiConfigEntity?.cornerRadiusMedium(): RoundedCornerShape = DynamicThemeValues.roundedCornerShapeMedium(this)

@Composable
fun UiConfigEntity?.cornerRadiusLarge(): RoundedCornerShape = DynamicThemeValues.roundedCornerShapeLarge(this)

