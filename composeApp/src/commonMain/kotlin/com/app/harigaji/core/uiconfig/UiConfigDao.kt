package com.app.harigaji.core.uiconfig

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UiConfigDao {
    @Query("SELECT * FROM ui_config WHERE id = 1")
    suspend fun getConfig(): UiConfigEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertConfig(config: UiConfigEntity)
    
    @Query("UPDATE ui_config SET " +
            "primaryColor = :primaryColor, " +
            "secondaryColor = :secondaryColor, " +
            "surfaceColor = :surfaceColor, " +
            "surfaceLowestColor = :surfaceLowestColor, " +
            "backgroundColor = :backgroundColor, " +
            "onSurfaceColor = :onSurfaceColor, " +
            "onSurfaceVariantColor = :onSurfaceVariantColor, " +
            "secondaryContainerColor = :secondaryContainerColor " +
            "WHERE id = 1")
    suspend fun updateColors(
        primaryColor: Long,
        secondaryColor: Long,
        surfaceColor: Long,
        surfaceLowestColor: Long,
        backgroundColor: Long,
        onSurfaceColor: Long,
        onSurfaceVariantColor: Long,
        secondaryContainerColor: Long
    )
    
    @Query("UPDATE ui_config SET " +
            "iconSizeSmall = :small, " +
            "iconSizeMedium = :medium, " +
            "iconSizeLarge = :large, " +
            "iconSizeExtraLarge = :extraLarge " +
            "WHERE id = 1")
    suspend fun updateIconSizes(
        small: Float,
        medium: Float,
        large: Float,
        extraLarge: Float
    )
    
    @Query("UPDATE ui_config SET " +
            "textSizeSmall = :small, " +
            "textSizeMedium = :medium, " +
            "textSizeLarge = :large, " +
            "textSizeExtraLarge = :extraLarge, " +
            "textSizeTitle = :title, " +
            "textSizeHeadline = :headline " +
            "WHERE id = 1")
    suspend fun updateTextSizes(
        small: Float,
        medium: Float,
        large: Float,
        extraLarge: Float,
        title: Float,
        headline: Float
    )
}

