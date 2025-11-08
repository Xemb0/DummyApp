package com.app.harigaji.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.core.uiconfig.UiConfigEntity
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.Urbanist
import org.jetbrains.compose.resources.Font

@Composable
fun ColorScheme(config: UiConfigEntity?): ColorScheme {
    val defaultConfig = UiConfigEntity()
    val uiConfig = config ?: defaultConfig
    
    return lightColorScheme(
        primary = Color(uiConfig.primaryColor),
        secondary = Color(uiConfig.secondaryColor),
        surface = Color(uiConfig.surfaceColor),
        surfaceContainerLowest = Color(uiConfig.surfaceLowestColor),
        background = Color(uiConfig.backgroundColor),
        onSurface = Color(uiConfig.onSurfaceColor),
        onSurfaceVariant = Color(uiConfig.onSurfaceVariantColor),
        secondaryContainer = Color(uiConfig.secondaryContainerColor)
    )
}

@Composable
fun Typography(config: UiConfigEntity?): Typography {
    val defaultConfig = UiConfigEntity()
    val uiConfig = config ?: defaultConfig
    
    val myFontFamily = FontFamily(
        Font(
            resource = Res.font.Urbanist,
            weight = FontWeight.Bold
        )
    )
    
    val interFont = FontFamily(
        Font(
            resource = Res.font.Urbanist,
            weight = FontWeight.Normal
        ),
        Font(
            resource = Res.font.Urbanist,
            weight = FontWeight.Medium
        ),
    )
    
    val textMultiplier = uiConfig.textSizeMultiplier
    
    return Typography(
        bodyLarge = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Normal,
            fontSize = (uiConfig.baseTextSizeLarge * textMultiplier).sp,
            lineHeight = (uiConfig.baseTextSizeLarge * textMultiplier * 1.4f).sp
        ),
        bodyMedium = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Medium,
            fontSize = (uiConfig.baseTextSizeMedium * textMultiplier).sp,
            lineHeight = (uiConfig.baseTextSizeMedium * textMultiplier * 1.3f).sp
        ),
        bodySmall = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Normal,
            fontSize = (uiConfig.baseTextSizeSmall * textMultiplier).sp,
            lineHeight = (uiConfig.baseTextSizeSmall * textMultiplier * 1.3f).sp
        ),
        titleLarge = TextStyle(
            fontFamily = myFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = (uiConfig.baseTextSizeHeadline * textMultiplier).sp,
            lineHeight = (uiConfig.baseTextSizeHeadline * textMultiplier * 1.1f).sp
        ),
        titleSmall = TextStyle(
            fontFamily = myFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = (uiConfig.baseTextSizeTitle * textMultiplier).sp,
            lineHeight = (uiConfig.baseTextSizeTitle * textMultiplier * 1.4f).sp
        ),
    )
}

@Composable
fun Shapes(config: UiConfigEntity?): Shapes {
    val defaultConfig = UiConfigEntity()
    val uiConfig = config ?: defaultConfig
    
    val baseSmall = uiConfig.baseCornerRadiusSmall
    val baseMedium = uiConfig.baseCornerRadiusMedium
    val baseLarge = uiConfig.baseCornerRadiusLarge
    val multiplier = uiConfig.cornerRadiusMultiplier
    
    return Shapes(
        extraSmall = RoundedCornerShape((baseSmall * multiplier).dp),
        small = RoundedCornerShape((baseSmall * multiplier).dp),
        medium = RoundedCornerShape((baseMedium * multiplier).dp),
        large = RoundedCornerShape((baseLarge * multiplier).dp),
        extraLarge = RoundedCornerShape((baseLarge * multiplier).dp)
    )
}

@Composable
fun AppTheme(
    config: UiConfigEntity?,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = ColorScheme(config),
        typography = Typography(config),
        shapes = Shapes(config),
        content = content
    )
}

// Extension properties for easy access to config values
object ThemeValues {
    // Font families - Urbanist
    @Composable
    fun getFontFamily(fontWeight: androidx.compose.ui.text.font.FontWeight): androidx.compose.ui.text.font.FontFamily {
        return when (fontWeight) {
            androidx.compose.ui.text.font.FontWeight.Bold -> FontFamily(
                Font(
                    resource = Res.font.Urbanist,
                    weight = FontWeight.Bold
                )
            )
            else -> FontFamily(
                Font(
                    resource = Res.font.Urbanist,
                    weight = FontWeight.Normal
                ),
                Font(
                    resource = Res.font.Urbanist,
                    weight = FontWeight.Medium
                )
            )
        }
    }
    // Inner Padding Horizontal - uses baseInner * innerMultiplier
    @Composable
    fun innerPaddingSmallHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseInnerPaddingSmall ?: 4f
        val multiplier = config?.innerPaddingHorizontalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun innerPaddingMediumHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseInnerPaddingMedium ?: 8f
        val multiplier = config?.innerPaddingHorizontalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun innerPaddingLargeHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseInnerPaddingLarge ?: 16f
        val multiplier = config?.innerPaddingHorizontalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun innerPaddingExtraLargeHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseInnerPaddingExtraLarge ?: 24f
        val multiplier = config?.innerPaddingHorizontalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    // Outer Padding Horizontal - uses baseOuter * outerMultiplier
    @Composable
    fun outerPaddingSmallHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseOuterPaddingSmall ?: 4f
        val multiplier = config?.outerPaddingHorizontalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun outerPaddingMediumHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseOuterPaddingMedium ?: 8f
        val multiplier = config?.outerPaddingHorizontalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun outerPaddingLargeHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseOuterPaddingLarge ?: 16f
        val multiplier = config?.outerPaddingHorizontalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun outerPaddingExtraLargeHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseOuterPaddingExtraLarge ?: 24f
        val multiplier = config?.outerPaddingHorizontalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    // Inner Padding Vertical - uses baseInner * innerMultiplier
    @Composable
    fun innerPaddingSmallVertical(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseInnerPaddingSmall ?: 4f
        val multiplier = config?.innerPaddingVerticalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun innerPaddingMediumVertical(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseInnerPaddingMedium ?: 8f
        val multiplier = config?.innerPaddingVerticalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun innerPaddingLargeVertical(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseInnerPaddingLarge ?: 16f
        val multiplier = config?.innerPaddingVerticalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun innerPaddingExtraLargeVertical(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseInnerPaddingExtraLarge ?: 24f
        val multiplier = config?.innerPaddingVerticalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    // Outer Padding Vertical - uses baseOuter * outerMultiplier
    @Composable
    fun outerPaddingSmallVertical(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseOuterPaddingSmall ?: 4f
        val multiplier = config?.outerPaddingVerticalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun outerPaddingMediumVertical(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseOuterPaddingMedium ?: 8f
        val multiplier = config?.outerPaddingVerticalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun outerPaddingLargeVertical(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseOuterPaddingLarge ?: 16f
        val multiplier = config?.outerPaddingVerticalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun outerPaddingExtraLargeVertical(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseOuterPaddingExtraLarge ?: 24f
        val multiplier = config?.outerPaddingVerticalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    // Backward compatibility - uses inner horizontal values
    @Composable
    fun paddingSmallHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return innerPaddingSmallHorizontal(config)
    }
    
    @Composable
    fun paddingMediumHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return innerPaddingMediumHorizontal(config)
    }
    
    @Composable
    fun paddingLargeHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return innerPaddingLargeHorizontal(config)
    }
    
    @Composable
    fun paddingExtraLargeHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return innerPaddingExtraLargeHorizontal(config)
    }
    
    @Composable
    fun paddingSmallVertical(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return innerPaddingSmallVertical(config)
    }
    
    @Composable
    fun paddingMediumVertical(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return innerPaddingMediumVertical(config)
    }
    
    @Composable
    fun paddingLargeVertical(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return innerPaddingLargeVertical(config)
    }
    
    @Composable
    fun paddingExtraLargeVertical(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return innerPaddingExtraLargeVertical(config)
    }
    
    @Composable
    fun paddingSmall(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return innerPaddingSmallHorizontal(config)
    }
    
    @Composable
    fun paddingMedium(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return innerPaddingMediumHorizontal(config)
    }
    
    @Composable
    fun paddingLarge(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return innerPaddingLargeHorizontal(config)
    }
    
    @Composable
    fun paddingExtraLarge(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return innerPaddingExtraLargeHorizontal(config)
    }
    
    // Spacing - uses base * multiplier
    @Composable
    fun spacingSmall(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseSpacingSmall ?: 4f
        val multiplier = config?.spacingVerticalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun spacingMedium(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseSpacingMedium ?: 8f
        val multiplier = config?.spacingVerticalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun spacingLarge(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseSpacingLarge ?: 16f
        val multiplier = config?.spacingVerticalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun spacingExtraLarge(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseSpacingExtraLarge ?: 24f
        val multiplier = config?.spacingVerticalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    // Spacing Horizontal
    @Composable
    fun spacingSmallHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseSpacingSmall ?: 4f
        val multiplier = config?.spacingHorizontalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun spacingMediumHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseSpacingMedium ?: 8f
        val multiplier = config?.spacingHorizontalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun spacingLargeHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseSpacingLarge ?: 16f
        val multiplier = config?.spacingHorizontalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun spacingExtraLargeHorizontal(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseSpacingExtraLarge ?: 24f
        val multiplier = config?.spacingHorizontalMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    // Sizes - uses base * multiplier
    @Composable
    fun sizeSmall(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseSizeSmall ?: 16f
        val multiplier = config?.sizeMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun sizeMedium(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseSizeMedium ?: 24f
        val multiplier = config?.sizeMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun sizeLarge(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseSizeLarge ?: 32f
        val multiplier = config?.sizeMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun sizeExtraLarge(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        val base = config?.baseSizeExtraLarge ?: 48f
        val multiplier = config?.sizeMultiplier ?: 1.0f
        return (base * multiplier).dp
    }
    
    @Composable
    fun elevationSmall(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return (config?.elevationSmall ?: 2f).dp
    }
    
    @Composable
    fun elevationMedium(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return (config?.elevationMedium ?: 4f).dp
    }
    
    @Composable
    fun elevationLarge(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return (config?.elevationLarge ?: 8f).dp
    }
    
    @Composable
    fun cornerRadiusSmall(config: UiConfigEntity?): androidx.compose.foundation.shape.CornerSize {
        val base = config?.baseCornerRadiusSmall ?: 4f
        val multiplier = config?.cornerRadiusMultiplier ?: 1.0f
        return androidx.compose.foundation.shape.CornerSize((base * multiplier).dp)
    }
    
    @Composable
    fun cornerRadiusMedium(config: UiConfigEntity?): androidx.compose.foundation.shape.CornerSize {
        val base = config?.baseCornerRadiusMedium ?: 8f
        val multiplier = config?.cornerRadiusMultiplier ?: 1.0f
        return androidx.compose.foundation.shape.CornerSize((base * multiplier).dp)
    }
    
    @Composable
    fun cornerRadiusLarge(config: UiConfigEntity?): androidx.compose.foundation.shape.CornerSize {
        val base = config?.baseCornerRadiusLarge ?: 16f
        val multiplier = config?.cornerRadiusMultiplier ?: 1.0f
        return androidx.compose.foundation.shape.CornerSize((base * multiplier).dp)
    }
    
    @Composable
    fun roundedCornerShapeSmall(config: UiConfigEntity?): RoundedCornerShape {
        val base = config?.baseCornerRadiusSmall ?: 4f
        val multiplier = config?.cornerRadiusMultiplier ?: 1.0f
        return RoundedCornerShape(base * multiplier)
    }
    
    @Composable
    fun roundedCornerShapeMedium(config: UiConfigEntity?): RoundedCornerShape {
        val base = config?.baseCornerRadiusMedium ?: 8f
        val multiplier = config?.cornerRadiusMultiplier ?: 1.0f
        return RoundedCornerShape(base * multiplier)
    }
    
    @Composable
    fun roundedCornerShapeLarge(config: UiConfigEntity?): RoundedCornerShape {
        val base = config?.baseCornerRadiusLarge ?: 16f
        val multiplier = config?.cornerRadiusMultiplier ?: 1.0f
        return RoundedCornerShape(base * multiplier)
    }
    
    // Text sizes - uses base * multiplier
    @Composable
    fun textSizeSmall(config: UiConfigEntity?): androidx.compose.ui.unit.TextUnit {
        val base = config?.baseTextSizeSmall ?: 12f
        val multiplier = config?.textSizeMultiplier ?: 1.0f
        return (base * multiplier).sp
    }
    
    @Composable
    fun textSizeMedium(config: UiConfigEntity?): androidx.compose.ui.unit.TextUnit {
        val base = config?.baseTextSizeMedium ?: 14f
        val multiplier = config?.textSizeMultiplier ?: 1.0f
        return (base * multiplier).sp
    }
    
    @Composable
    fun textSizeLarge(config: UiConfigEntity?): androidx.compose.ui.unit.TextUnit {
        val base = config?.baseTextSizeLarge ?: 16f
        val multiplier = config?.textSizeMultiplier ?: 1.0f
        return (base * multiplier).sp
    }
    
    @Composable
    fun textSizeExtraLarge(config: UiConfigEntity?): androidx.compose.ui.unit.TextUnit {
        val base = config?.baseTextSizeExtraLarge ?: 20f
        val multiplier = config?.textSizeMultiplier ?: 1.0f
        return (base * multiplier).sp
    }
    
    @Composable
    fun textSizeTitle(config: UiConfigEntity?): androidx.compose.ui.unit.TextUnit {
        val base = config?.baseTextSizeTitle ?: 24f
        val multiplier = config?.textSizeMultiplier ?: 1.0f
        return (base * multiplier).sp
    }
    
    @Composable
    fun textSizeHeadline(config: UiConfigEntity?): androidx.compose.ui.unit.TextUnit {
        val base = config?.baseTextSizeHeadline ?: 32f
        val multiplier = config?.textSizeMultiplier ?: 1.0f
        return (base * multiplier).sp
    }
}

// CompositionLocal to provide UI config throughout the app
val LocalUiConfig = compositionLocalOf<UiConfigEntity?> { null }

@Composable
fun rememberUiConfig(): UiConfigEntity? {
    return LocalUiConfig.current
}

