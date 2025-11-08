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
import com.app.harigaji.theme.ThemeValues

/**
 * Helper functions for accessing dynamic spacing values throughout the app
 */

// Inner Padding Horizontal
@Composable
fun rememberInnerPaddingSmallHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingSmallHorizontal(config)
}

@Composable
fun rememberInnerPaddingMediumHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingMediumHorizontal(config)
}

@Composable
fun rememberInnerPaddingLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingLargeHorizontal(config)
}

@Composable
fun rememberInnerPaddingExtraLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingExtraLargeHorizontal(config)
}

// Outer Padding Horizontal
@Composable
fun rememberPaddingOuterSmallHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingMediumHorizontal(config)
}

@Composable
fun rememberPaddingOuterMediumHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingMediumHorizontal(config)
}

@Composable
fun rememberPaddingOuterLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingLargeHorizontal(config)
}

@Composable
fun rememberPaddingOuterExtraLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingExtraLargeHorizontal(config)
}

// Inner Padding Vertical
@Composable
fun rememberInnerPaddingSmallVertical(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingSmallVertical(config)
}

@Composable
fun rememberInnerPaddingMediumVertical(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingMediumVertical(config)
}

@Composable
fun rememberInnerPaddingLargeVertical(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingLargeVertical(config)
}

@Composable
fun rememberInnerPaddingExtraLargeVertical(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingExtraLargeVertical(config)
}

// Outer Padding Vertical
@Composable
fun rememberPaddingOuterSmallVertical(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingSmallVertical(config)
}

@Composable
fun rememberPaddingOuterMediumVertical(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingMediumVertical(config)
}

@Composable
fun rememberPaddingOuterLargeVertical(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingLargeVertical(config)
}

@Composable
fun rememberPaddingOuterExtraLargeVertical(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingExtraLargeVertical(config)
}

// Backward compatibility - uses inner padding
@Composable
fun rememberPaddingSmallHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingSmallHorizontal(config)
}

@Composable
fun rememberPaddingMediumHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingMediumHorizontal(config)
}

@Composable
fun rememberPaddingLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingLargeHorizontal(config)
}

@Composable
fun rememberPaddingExtraLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingExtraLargeHorizontal(config)
}

@Composable
fun rememberPaddingSmallVertical(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingSmallVertical(config)
}

@Composable
fun rememberPaddingMediumVertical(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingMediumVertical(config)
}

@Composable
fun rememberPaddingLargeVertical(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingLargeVertical(config)
}

@Composable
fun rememberPaddingExtraLargeVertical(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingExtraLargeVertical(config)
}

// Backward compatibility - uses horizontal values
@Composable
fun rememberPaddingSmall(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingSmall(config)
}

@Composable
fun rememberPaddingMedium(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingMedium(config)
}

@Composable
fun rememberPaddingLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingLarge(config)
}

@Composable
fun rememberPaddingExtraLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingExtraLarge(config)
}

// Spacing Vertical
@Composable
fun rememberSpacingSmall(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.spacingSmall(config)
}

@Composable
fun rememberSpacingMedium(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.spacingMedium(config)
}

@Composable
fun rememberSpacingLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.spacingLarge(config)
}

@Composable
fun rememberSpacingExtraLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.spacingExtraLarge(config)
}

// Spacing Horizontal
@Composable
fun rememberSpacingSmallHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.spacingSmallHorizontal(config)
}

@Composable
fun rememberSpacingMediumHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.spacingMediumHorizontal(config)
}

@Composable
fun rememberSpacingLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.spacingLargeHorizontal(config)
}

@Composable
fun rememberSpacingExtraLargeHorizontal(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.spacingExtraLargeHorizontal(config)
}

@Composable
fun rememberCornerRadiusSmall(): RoundedCornerShape {
    val config = rememberUiConfig()
    return ThemeValues.roundedCornerShapeSmall(config)
}

@Composable
fun rememberCornerRadiusMedium(): RoundedCornerShape {
    val config = rememberUiConfig()
    return ThemeValues.roundedCornerShapeMedium(config)
}

@Composable
fun rememberCornerRadiusLarge(): RoundedCornerShape {
    val config = rememberUiConfig()
    return ThemeValues.roundedCornerShapeLarge(config)
}

// Custom Spacers - Vertical (uses spacing multipliers, not padding)
@Composable
fun SpacerSmallVertical() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingSmall(config)
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(spacing))
}

@Composable
fun SpacerMediumVertical() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingMedium(config)
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(spacing))
}

@Composable
fun SpacerLargeVertical() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingLarge(config)
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(spacing))
}

@Composable
fun SpacerExtraLargeVertical() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingExtraLarge(config)
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.height(spacing))
}

// Custom Spacers - Horizontal (uses spacing multipliers, not padding)
@Composable
fun SpacerSmallHorizontal() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingSmallHorizontal(config)
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(spacing))
}

@Composable
fun SpacerMediumHorizontal() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingMediumHorizontal(config)
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(spacing))
}

@Composable
fun SpacerLargeHorizontal() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingLargeHorizontal(config)
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(spacing))
}

@Composable
fun SpacerExtraLargeHorizontal() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingExtraLargeHorizontal(config)
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.width(spacing))
}

// Extension functions for direct access - Inner Padding
@Composable
fun UiConfigEntity?.innerPaddingSmallHorizontal(): Dp = ThemeValues.innerPaddingSmallHorizontal(this)

@Composable
fun UiConfigEntity?.innerPaddingMediumHorizontal(): Dp = ThemeValues.innerPaddingMediumHorizontal(this)

@Composable
fun UiConfigEntity?.innerPaddingLargeHorizontal(): Dp = ThemeValues.innerPaddingLargeHorizontal(this)

@Composable
fun UiConfigEntity?.innerPaddingExtraLargeHorizontal(): Dp = ThemeValues.innerPaddingExtraLargeHorizontal(this)

@Composable
fun UiConfigEntity?.innerPaddingSmallVertical(): Dp = ThemeValues.innerPaddingSmallVertical(this)

@Composable
fun UiConfigEntity?.innerPaddingMediumVertical(): Dp = ThemeValues.innerPaddingMediumVertical(this)

@Composable
fun UiConfigEntity?.innerPaddingLargeVertical(): Dp = ThemeValues.innerPaddingLargeVertical(this)

@Composable
fun UiConfigEntity?.innerPaddingExtraLargeVertical(): Dp = ThemeValues.innerPaddingExtraLargeVertical(this)

// Extension functions for direct access - Outer Padding
@Composable
fun UiConfigEntity?.outerPaddingSmallHorizontal(): Dp = ThemeValues.outerPaddingSmallHorizontal(this)

@Composable
fun UiConfigEntity?.outerPaddingMediumHorizontal(): Dp = ThemeValues.outerPaddingMediumHorizontal(this)

@Composable
fun UiConfigEntity?.outerPaddingLargeHorizontal(): Dp = ThemeValues.outerPaddingLargeHorizontal(this)

@Composable
fun UiConfigEntity?.outerPaddingExtraLargeHorizontal(): Dp = ThemeValues.outerPaddingExtraLargeHorizontal(this)

@Composable
fun UiConfigEntity?.outerPaddingSmallVertical(): Dp = ThemeValues.outerPaddingSmallVertical(this)

@Composable
fun UiConfigEntity?.outerPaddingMediumVertical(): Dp = ThemeValues.outerPaddingMediumVertical(this)

@Composable
fun UiConfigEntity?.outerPaddingLargeVertical(): Dp = ThemeValues.outerPaddingLargeVertical(this)

@Composable
fun UiConfigEntity?.outerPaddingExtraLargeVertical(): Dp = ThemeValues.outerPaddingExtraLargeVertical(this)

// Backward compatibility
@Composable
fun UiConfigEntity?.paddingSmallHorizontal(): Dp = ThemeValues.paddingSmallHorizontal(this)

@Composable
fun UiConfigEntity?.paddingMediumHorizontal(): Dp = ThemeValues.paddingMediumHorizontal(this)

@Composable
fun UiConfigEntity?.paddingLargeHorizontal(): Dp = ThemeValues.paddingLargeHorizontal(this)

@Composable
fun UiConfigEntity?.paddingExtraLargeHorizontal(): Dp = ThemeValues.paddingExtraLargeHorizontal(this)

@Composable
fun UiConfigEntity?.paddingSmallVertical(): Dp = ThemeValues.paddingSmallVertical(this)

@Composable
fun UiConfigEntity?.paddingMediumVertical(): Dp = ThemeValues.paddingMediumVertical(this)

@Composable
fun UiConfigEntity?.paddingLargeVertical(): Dp = ThemeValues.paddingLargeVertical(this)

@Composable
fun UiConfigEntity?.paddingExtraLargeVertical(): Dp = ThemeValues.paddingExtraLargeVertical(this)

// Backward compatibility
@Composable
fun UiConfigEntity?.paddingSmall(): Dp = ThemeValues.paddingSmall(this)

@Composable
fun UiConfigEntity?.paddingMedium(): Dp = ThemeValues.paddingMedium(this)

@Composable
fun UiConfigEntity?.paddingLarge(): Dp = ThemeValues.paddingLarge(this)

@Composable
fun UiConfigEntity?.paddingExtraLarge(): Dp = ThemeValues.paddingExtraLarge(this)

@Composable
fun UiConfigEntity?.cornerRadiusSmall(): RoundedCornerShape = ThemeValues.roundedCornerShapeSmall(this)

@Composable
fun UiConfigEntity?.cornerRadiusMedium(): RoundedCornerShape = ThemeValues.roundedCornerShapeMedium(this)

@Composable
fun UiConfigEntity?.cornerRadiusLarge(): RoundedCornerShape = ThemeValues.roundedCornerShapeLarge(this)

