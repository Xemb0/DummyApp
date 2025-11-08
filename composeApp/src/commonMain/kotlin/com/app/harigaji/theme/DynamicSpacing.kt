package com.app.harigaji.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import com.app.harigaji.core.uiconfig.UiConfigEntity
import com.app.harigaji.core.uiconfig.rememberUiConfig
import com.app.harigaji.theme.DynamicTheme.DynamicThemeValues

/**
 * Helper functions for accessing dynamic spacing values throughout the app
 */

// Inner Padding Horizontal
@Composable
fun rememberDynamicInnerPaddingSmallHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.innerPaddingSmallHorizontal(config)
}

@Composable
fun rememberDynamicInnerPaddingMediumHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.innerPaddingMediumHorizontal(config)
}

@Composable
fun rememberDynamicInnerPaddingLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.innerPaddingLargeHorizontal(config)
}

@Composable
fun rememberDynamicInnerPaddingExtraLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.innerPaddingExtraLargeHorizontal(config)
}

// Outer Padding Horizontal
@Composable
fun rememberDynamicPaddingOuterSmallHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.outerPaddingMediumHorizontal(config)
}

@Composable
fun rememberDynamicPaddingOuterMediumHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.outerPaddingMediumHorizontal(config)
}

@Composable
fun rememberDynamicPaddingOuterLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.outerPaddingLargeHorizontal(config)
}

@Composable
fun rememberDynamicPaddingOuterExtraLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.outerPaddingExtraLargeHorizontal(config)
}

// Inner Padding Vertical
@Composable
fun rememberDynamicInnerPaddingSmallVertical(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.innerPaddingSmallVertical(config)
}

@Composable
fun rememberDynamicInnerPaddingMediumVertical(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.innerPaddingMediumVertical(config)
}

@Composable
fun rememberDynamicInnerPaddingLargeVertical(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.innerPaddingLargeVertical(config)
}

@Composable
fun rememberDynamicInnerPaddingExtraLargeVertical(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.innerPaddingExtraLargeVertical(config)
}

// Outer Padding Vertical
@Composable
fun rememberDynamicPaddingOuterSmallVertical(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.outerPaddingSmallVertical(config)
}

@Composable
fun rememberDynamicPaddingOuterMediumVertical(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.outerPaddingMediumVertical(config)
}

@Composable
fun rememberDynamicPaddingOuterLargeVertical(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.outerPaddingLargeVertical(config)
}

@Composable
fun rememberDynamicPaddingOuterExtraLargeVertical(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.outerPaddingExtraLargeVertical(config)
}

// Backward compatibility - uses inner padding
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

// Spacing Vertical
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

// Spacing Horizontal
@Composable
fun rememberDynamicSpacingSmallHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.spacingSmallHorizontal(config)
}

@Composable
fun rememberDynamicSpacingMediumHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.spacingMediumHorizontal(config)
}

@Composable
fun rememberDynamicSpacingLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.spacingLargeHorizontal(config)
}

@Composable
fun rememberDynamicSpacingExtraLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return DynamicThemeValues.spacingExtraLargeHorizontal(config)
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

// Custom Spacers - Vertical
@Composable
fun DynamicSpacerSmallVertical() {
    Spacer(modifier = Modifier.height(rememberDynamicSpacingSmall()))
}

@Composable
fun DynamicSpacerMediumVertical() {
    Spacer(modifier = Modifier.height(rememberDynamicSpacingMedium()))
}

@Composable
fun DynamicSpacerLargeVertical() {
    Spacer(modifier = Modifier.height(rememberDynamicSpacingLarge()))
}

@Composable
fun DynamicSpacerExtraLargeVertical() {
    Spacer(modifier = Modifier.height(rememberDynamicSpacingExtraLarge()))
}

// Custom Spacers - Horizontal
@Composable
fun DynamicSpacerSmallHorizontal() {
    Spacer(modifier = Modifier.width(rememberDynamicSpacingSmallHorizontal()))
}

@Composable
fun DynamicSpacerMediumHorizontal() {
    Spacer(modifier = Modifier.width(rememberDynamicSpacingMediumHorizontal()))
}

@Composable
fun DynamicSpacerLargeHorizontal() {
    Spacer(modifier = Modifier.width(rememberDynamicSpacingLargeHorizontal()))
}

@Composable
fun DynamicSpacerExtraLargeHorizontal() {
    Spacer(modifier = Modifier.width(rememberDynamicSpacingExtraLargeHorizontal()))
}

// Extension functions for direct access - Inner Padding
@Composable
fun UiConfigEntity?.innerPaddingSmallHorizontal(): Dp = DynamicThemeValues.innerPaddingSmallHorizontal(this)

@Composable
fun UiConfigEntity?.innerPaddingMediumHorizontal(): Dp = DynamicThemeValues.innerPaddingMediumHorizontal(this)

@Composable
fun UiConfigEntity?.innerPaddingLargeHorizontal(): Dp = DynamicThemeValues.innerPaddingLargeHorizontal(this)

@Composable
fun UiConfigEntity?.innerPaddingExtraLargeHorizontal(): Dp = DynamicThemeValues.innerPaddingExtraLargeHorizontal(this)

@Composable
fun UiConfigEntity?.innerPaddingSmallVertical(): Dp = DynamicThemeValues.innerPaddingSmallVertical(this)

@Composable
fun UiConfigEntity?.innerPaddingMediumVertical(): Dp = DynamicThemeValues.innerPaddingMediumVertical(this)

@Composable
fun UiConfigEntity?.innerPaddingLargeVertical(): Dp = DynamicThemeValues.innerPaddingLargeVertical(this)

@Composable
fun UiConfigEntity?.innerPaddingExtraLargeVertical(): Dp = DynamicThemeValues.innerPaddingExtraLargeVertical(this)

// Extension functions for direct access - Outer Padding
@Composable
fun UiConfigEntity?.outerPaddingSmallHorizontal(): Dp = DynamicThemeValues.outerPaddingSmallHorizontal(this)

@Composable
fun UiConfigEntity?.outerPaddingMediumHorizontal(): Dp = DynamicThemeValues.outerPaddingMediumHorizontal(this)

@Composable
fun UiConfigEntity?.outerPaddingLargeHorizontal(): Dp = DynamicThemeValues.outerPaddingLargeHorizontal(this)

@Composable
fun UiConfigEntity?.outerPaddingExtraLargeHorizontal(): Dp = DynamicThemeValues.outerPaddingExtraLargeHorizontal(this)

@Composable
fun UiConfigEntity?.outerPaddingSmallVertical(): Dp = DynamicThemeValues.outerPaddingSmallVertical(this)

@Composable
fun UiConfigEntity?.outerPaddingMediumVertical(): Dp = DynamicThemeValues.outerPaddingMediumVertical(this)

@Composable
fun UiConfigEntity?.outerPaddingLargeVertical(): Dp = DynamicThemeValues.outerPaddingLargeVertical(this)

@Composable
fun UiConfigEntity?.outerPaddingExtraLargeVertical(): Dp = DynamicThemeValues.outerPaddingExtraLargeVertical(this)

// Backward compatibility
@Composable
fun UiConfigEntity?.paddingSmallHorizontal(): Dp = DynamicThemeValues.paddingSmallHorizontal(this)

@Composable
fun UiConfigEntity?.paddingMediumHorizontal(): Dp = DynamicThemeValues.paddingMediumHorizontal(this)

@Composable
fun UiConfigEntity?.paddingLargeHorizontal(): Dp = DynamicThemeValues.paddingLargeHorizontal(this)

@Composable
fun UiConfigEntity?.paddingExtraLargeHorizontal(): Dp = DynamicThemeValues.paddingExtraLargeHorizontal(this)

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

