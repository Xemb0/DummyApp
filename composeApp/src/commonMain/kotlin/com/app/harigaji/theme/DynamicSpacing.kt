package com.app.harigaji.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.app.harigaji.core.uiconfig.UiConfigEntity

/**
 * Helper functions for accessing dynamic spacing values throughout the app
 */

// Padding Horizontal
@Composable
fun rememberDynamicPaddingSmallHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.paddingSmallHorizontal(config)
}

@Composable
fun rememberDynamicPaddingMediumHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.paddingMediumHorizontal(config)
}

@Composable
fun rememberDynamicPaddingLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.paddingLargeHorizontal(config)
}

@Composable
fun rememberDynamicPaddingExtraLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.paddingExtraLargeHorizontal(config)
}

// Padding Vertical
@Composable
fun rememberDynamicPaddingSmallVertical(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.paddingSmallVertical(config)
}

@Composable
fun rememberDynamicPaddingMediumVertical(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.paddingMediumVertical(config)
}

@Composable
fun rememberDynamicPaddingLargeVertical(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.paddingLargeVertical(config)
}

@Composable
fun rememberDynamicPaddingExtraLargeVertical(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.paddingExtraLargeVertical(config)
}

// Backward compatibility - uses horizontal values
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

// Spacing
@Composable
fun rememberDynamicSpacingSmall(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.spacingSmall(config)
}

@Composable
fun rememberDynamicSpacingMedium(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.spacingMedium(config)
}

@Composable
fun rememberDynamicSpacingLarge(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.spacingLarge(config)
}

@Composable
fun rememberDynamicSpacingExtraLarge(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.spacingExtraLarge(config)
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

// Extension functions for direct access - Horizontal
@Composable
fun UiConfigEntity?.paddingSmallHorizontal(): Dp = DynamicThemeValues.paddingSmallHorizontal(this)

@Composable
fun UiConfigEntity?.paddingMediumHorizontal(): Dp = DynamicThemeValues.paddingMediumHorizontal(this)

@Composable
fun UiConfigEntity?.paddingLargeHorizontal(): Dp = DynamicThemeValues.paddingLargeHorizontal(this)

@Composable
fun UiConfigEntity?.paddingExtraLargeHorizontal(): Dp = DynamicThemeValues.paddingExtraLargeHorizontal(this)

// Extension functions for direct access - Vertical
@Composable
fun UiConfigEntity?.paddingSmallVertical(): Dp = DynamicThemeValues.paddingSmallVertical(this)

@Composable
fun UiConfigEntity?.paddingMediumVertical(): Dp = DynamicThemeValues.paddingMediumVertical(this)

@Composable
fun UiConfigEntity?.paddingLargeVertical(): Dp = DynamicThemeValues.paddingLargeVertical(this)

@Composable
fun UiConfigEntity?.paddingExtraLargeVertical(): Dp = DynamicThemeValues.paddingExtraLargeVertical(this)

// Backward compatibility
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

