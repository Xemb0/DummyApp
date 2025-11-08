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
    
    // Padding values (in dp, stored as Float)
    val paddingSmall: Float = 4f,
    val paddingMedium: Float = 8f,
    val paddingLarge: Float = 16f,
    val paddingExtraLarge: Float = 24f,
    
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
    
    // Corner radius (in dp, stored as Float)
    val cornerRadiusSmall: Float = 4f,
    val cornerRadiusMedium: Float = 8f,
    val cornerRadiusLarge: Float = 16f,
    
    // Elevation (in dp, stored as Float)
    val elevationSmall: Float = 2f,
    val elevationMedium: Float = 4f,
    val elevationLarge: Float = 8f
)

