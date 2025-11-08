package com.app.harigaji.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import com.app.harigaji.core.uiconfig.UiConfigEntity

/**
 * Helper functions for accessing dynamic spacing values throughout the app
 */

// Inner Padding Horizontal
@Composable
fun rememberInnerHorizontalPaddingSmall(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingSmallHorizontal(config)
}

@Composable
fun rememberInnerHorizontalPaddingMedium(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingMediumHorizontal(config)
}

@Composable
fun rememberInnerHorizontalPaddingLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingLargeHorizontal(config)
}

@Composable
fun rememberInnerHorizontalPaddingExtraLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingExtraLargeHorizontal(config)
}

// Outer Padding Horizontal
@Composable
fun rememberOuterHorizontalPaddingSmall(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingSmallHorizontal(config)
}

@Composable
fun rememberOuterHorizontalPaddingMedium(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingMediumHorizontal(config)
}

@Composable
fun rememberOuterHorizontalPaddingLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingLargeHorizontal(config)
}

@Composable
fun rememberOuterHorizontalPaddingExtraLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingExtraLargeHorizontal(config)
}

// Inner Padding Vertical
@Composable
fun rememberInnerVerticalPaddingSmall(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingSmallVertical(config)
}

@Composable
fun rememberInnerVerticalPaddingMedium(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingMediumVertical(config)
}

@Composable
fun rememberInnerVerticalPaddingLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingLargeVertical(config)
}

@Composable
fun rememberInnerVerticalPaddingExtraLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.innerPaddingExtraLargeVertical(config)
}

// Outer Padding Vertical
@Composable
fun rememberOuterVerticalPaddingSmall(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingSmallVertical(config)
}

@Composable
fun rememberOuterVerticalPaddingMedium(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingMediumVertical(config)
}

@Composable
fun rememberOuterVerticalPaddingLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingLargeVertical(config)
}

@Composable
fun rememberOuterVerticalPaddingExtraLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.outerPaddingExtraLargeVertical(config)
}

// Backward compatibility - uses inner padding
@Composable
fun rememberHorizontalPaddingSmall(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingSmallHorizontal(config)
}

@Composable
fun rememberHorizontalPaddingMedium(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingMediumHorizontal(config)
}

@Composable
fun rememberHorizontalPaddingLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingLargeHorizontal(config)
}

@Composable
fun rememberHorizontalPaddingExtraLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingExtraLargeHorizontal(config)
}

@Composable
fun rememberVerticalPaddingSmall(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingSmallVertical(config)
}

@Composable
fun rememberVerticalPaddingMedium(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingMediumVertical(config)
}

@Composable
fun rememberVerticalPaddingLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.paddingLargeVertical(config)
}

@Composable
fun rememberVerticalPaddingExtraLarge(): Dp {
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
fun rememberVerticalSpacingSmall(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.spacingSmall(config)
}

@Composable
fun rememberVerticalSpacingMedium(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.spacingMedium(config)
}

@Composable
fun rememberVerticalSpacingLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.spacingLarge(config)
}

@Composable
fun rememberVerticalSpacingExtraLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.spacingExtraLarge(config)
}

// Spacing Horizontal
@Composable
fun rememberHorizontalSpacingSmall(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.spacingSmallHorizontal(config)
}

@Composable
fun rememberHorizontalSpacingMedium(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.spacingMediumHorizontal(config)
}

@Composable
fun rememberHorizontalSpacingLarge(): Dp {
    val config = rememberUiConfig()
    return ThemeValues.spacingLargeHorizontal(config)
}

@Composable
fun rememberHorizontalSpacingExtraLarge(): Dp {
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

// Sizes
@Composable
fun rememberSizeSmall(): androidx.compose.ui.unit.Dp {
    val config = rememberUiConfig()
    return ThemeValues.sizeSmall(config)
}

@Composable
fun rememberSizeMedium(): androidx.compose.ui.unit.Dp {
    val config = rememberUiConfig()
    return ThemeValues.sizeMedium(config)
}

@Composable
fun rememberSizeLarge(): androidx.compose.ui.unit.Dp {
    val config = rememberUiConfig()
    return ThemeValues.sizeLarge(config)
}

@Composable
fun rememberSizeExtraLarge(): androidx.compose.ui.unit.Dp {
    val config = rememberUiConfig()
    return ThemeValues.sizeExtraLarge(config)
}

// Text Sizes
@Composable
fun rememberTextSizeSmall(): androidx.compose.ui.unit.TextUnit {
    val config = rememberUiConfig()
    return ThemeValues.textSizeSmall(config)
}

@Composable
fun rememberTextSizeMedium(): androidx.compose.ui.unit.TextUnit {
    val config = rememberUiConfig()
    return ThemeValues.textSizeMedium(config)
}

@Composable
fun rememberTextSizeLarge(): androidx.compose.ui.unit.TextUnit {
    val config = rememberUiConfig()
    return ThemeValues.textSizeLarge(config)
}

@Composable
fun rememberTextSizeExtraLarge(): androidx.compose.ui.unit.TextUnit {
    val config = rememberUiConfig()
    return ThemeValues.textSizeExtraLarge(config)
}

@Composable
fun rememberTextSizeTitle(): androidx.compose.ui.unit.TextUnit {
    val config = rememberUiConfig()
    return ThemeValues.textSizeTitle(config)
}

@Composable
fun rememberTextSizeHeadline(): androidx.compose.ui.unit.TextUnit {
    val config = rememberUiConfig()
    return ThemeValues.textSizeHeadline(config)
}

// Custom Spacers - Vertical (uses spacing multipliers, not padding)
@Composable
fun SpacerVerticalSmall() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingSmall(config)
    Spacer(modifier = Modifier.height(spacing))
}

@Composable
fun SpacerVerticalMedium() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingMedium(config)
    Spacer(modifier = Modifier.height(spacing))
}

@Composable
fun SpacerVerticalLarge() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingLarge(config)
    Spacer(modifier = Modifier.height(spacing))
}

@Composable
fun SpacerVerticalExtraLarge() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingExtraLarge(config)
    Spacer(modifier = Modifier.height(spacing))
}

// Custom Spacers - Horizontal (uses spacing multipliers, not padding)
@Composable
fun SpacerHorizontalSmall() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingSmallHorizontal(config)
    Spacer(modifier = Modifier.width(spacing))
}

@Composable
fun SpacerHorizontalMedium() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingMediumHorizontal(config)
    Spacer(modifier = Modifier.width(spacing))
}

@Composable
fun SpacerHorizontalLarge() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingLargeHorizontal(config)
    Spacer(modifier = Modifier.width(spacing))
}

@Composable
fun SpacerHorizontalExtraLarge() {
    val config = rememberUiConfig()
    val spacing = ThemeValues.spacingExtraLargeHorizontal(config)
    Spacer(modifier = Modifier.width(spacing))
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

