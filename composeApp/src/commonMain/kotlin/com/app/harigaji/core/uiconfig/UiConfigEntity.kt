package com.app.harigaji.core.uiconfig

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "ui_config")
data class UiConfigEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int = 1, // Single config instance
    
    // Colors (stored as ARGB integers)
    val primaryColor: Long = 0xFF6200EE,
    val secondaryColor: Long = 0xFF2ECA83,
    val surfaceColor: Long = 0xFFFFFFFF,
    val surfaceLowestColor: Long = 0xFFF5F5F5,
    val backgroundColor: Long = 0xFFF1F2F3,
    val onSurfaceColor: Long = 0xFF254B2B,
    val onSurfaceVariantColor: Long = 0xFF186745,
    val secondaryContainerColor: Long = 0xFF208A5C,
    
    // Relative scaling multipliers (1.0 = default, stored as Float)
    val paddingHorizontalMultiplier: Float = 1.0f,
    val paddingVerticalMultiplier: Float = 1.0f,
    val spacingHorizontalMultiplier: Float = 1.0f,
    val spacingVerticalMultiplier: Float = 1.0f,
    val cornerRadiusMultiplier: Float = 1.0f,
    
    // Base values (defaults, stored as Float) - these define the base sizes
    val basePaddingSmall: Float = 4f,
    val basePaddingMedium: Float = 8f,
    val basePaddingLarge: Float = 16f,
    val basePaddingExtraLarge: Float = 24f,
    
    val baseSpacingSmall: Float = 4f,
    val baseSpacingMedium: Float = 8f,
    val baseSpacingLarge: Float = 16f,
    val baseSpacingExtraLarge: Float = 24f,
    
    val baseCornerRadiusSmall: Float = 4f,
    val baseCornerRadiusMedium: Float = 8f,
    val baseCornerRadiusLarge: Float = 16f,
    
    // Icon sizes (in dp, stored as Float)
    val iconSizeSmall: Float = 16f,
    val iconSizeMedium: Float = 24f,
    val iconSizeLarge: Float = 32f,
    val iconSizeExtraLarge: Float = 48f,
    
    // Text sizes (in sp, stored as Float)
    val textSizeSmall: Float = 12f,
    val textSizeMedium: Float = 14f,
    val textSizeLarge: Float = 16f,
    val textSizeExtraLarge: Float = 20f,
    val textSizeTitle: Float = 24f,
    val textSizeHeadline: Float = 32f,
    
    // Elevation (in dp, stored as Float)
    val elevationSmall: Float = 2f,
    val elevationMedium: Float = 4f,
    val elevationLarge: Float = 8f
)

