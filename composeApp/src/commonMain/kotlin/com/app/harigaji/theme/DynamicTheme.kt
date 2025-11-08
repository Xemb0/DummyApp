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
fun DynamicColorScheme(config: UiConfigEntity?): ColorScheme {
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
fun DynamicTypography(config: UiConfigEntity?): Typography {
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
    
    return Typography(
        bodyLarge = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Normal,
            fontSize = uiConfig.textSizeLarge.sp,
            lineHeight = (uiConfig.textSizeLarge * 1.4f).sp
        ),
        bodyMedium = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Medium,
            fontSize = uiConfig.textSizeMedium.sp,
            lineHeight = (uiConfig.textSizeMedium * 1.3f).sp
        ),
        bodySmall = TextStyle(
            fontFamily = interFont,
            fontWeight = FontWeight.Normal,
            fontSize = uiConfig.textSizeSmall.sp,
            lineHeight = (uiConfig.textSizeSmall * 1.3f).sp
        ),
        titleLarge = TextStyle(
            fontFamily = myFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = uiConfig.textSizeHeadline.sp,
            lineHeight = (uiConfig.textSizeHeadline * 1.1f).sp
        ),
        titleSmall = TextStyle(
            fontFamily = myFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = uiConfig.textSizeTitle.sp,
            lineHeight = (uiConfig.textSizeTitle * 1.4f).sp
        ),
    )
}

@Composable
fun DynamicShapes(config: UiConfigEntity?): Shapes {
    val defaultConfig = UiConfigEntity()
    val uiConfig = config ?: defaultConfig
    
    return Shapes(
        extraSmall = RoundedCornerShape(uiConfig.cornerRadiusSmall.dp),
        small = RoundedCornerShape(uiConfig.cornerRadiusSmall.dp),
        medium = RoundedCornerShape(uiConfig.cornerRadiusMedium.dp),
        large = RoundedCornerShape(uiConfig.cornerRadiusLarge.dp),
        extraLarge = RoundedCornerShape(uiConfig.cornerRadiusLarge.dp)
    )
}

@Composable
fun DynamicMaterialTheme(
    config: UiConfigEntity?,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DynamicColorScheme(config),
        typography = DynamicTypography(config),
        shapes = DynamicShapes(config),
        content = content
    )
}

// Extension properties for easy access to config values
object DynamicThemeValues {
    @Composable
    fun paddingSmall(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return (config?.paddingSmall ?: 4f).dp
    }
    
    @Composable
    fun paddingMedium(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return (config?.paddingMedium ?: 8f).dp
    }
    
    @Composable
    fun paddingLarge(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return (config?.paddingLarge ?: 16f).dp
    }
    
    @Composable
    fun paddingExtraLarge(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return (config?.paddingExtraLarge ?: 24f).dp
    }
    
    @Composable
    fun iconSizeSmall(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return (config?.iconSizeSmall ?: 16f).dp
    }
    
    @Composable
    fun iconSizeMedium(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return (config?.iconSizeMedium ?: 24f).dp
    }
    
    @Composable
    fun iconSizeLarge(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return (config?.iconSizeLarge ?: 32f).dp
    }
    
    @Composable
    fun iconSizeExtraLarge(config: UiConfigEntity?): androidx.compose.ui.unit.Dp {
        return (config?.iconSizeExtraLarge ?: 48f).dp
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
        return androidx.compose.foundation.shape.CornerSize((config?.cornerRadiusSmall ?: 4f).dp)
    }
    
    @Composable
    fun cornerRadiusMedium(config: UiConfigEntity?): androidx.compose.foundation.shape.CornerSize {
        return androidx.compose.foundation.shape.CornerSize((config?.cornerRadiusMedium ?: 8f).dp)
    }
    
    @Composable
    fun cornerRadiusLarge(config: UiConfigEntity?): androidx.compose.foundation.shape.CornerSize {
        return androidx.compose.foundation.shape.CornerSize((config?.cornerRadiusLarge ?: 16f).dp)
    }
    
    @Composable
    fun roundedCornerShapeSmall(config: UiConfigEntity?): RoundedCornerShape {
        return RoundedCornerShape(config?.cornerRadiusSmall ?: 4f)
    }
    
    @Composable
    fun roundedCornerShapeMedium(config: UiConfigEntity?): RoundedCornerShape {
        return RoundedCornerShape(config?.cornerRadiusMedium ?: 8f)
    }
    
    @Composable
    fun roundedCornerShapeLarge(config: UiConfigEntity?): RoundedCornerShape {
        return RoundedCornerShape(config?.cornerRadiusLarge ?: 16f)
    }
}

// CompositionLocal to provide UI config throughout the app
val LocalUiConfig = compositionLocalOf<UiConfigEntity?> { null }

@Composable
fun rememberUiConfig(): UiConfigEntity? {
    return LocalUiConfig.current
}

