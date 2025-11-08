package com.app.harigaji.core.uiconfig

import com.app.harigaji.core.database.MyAppDataBase

class UiConfigRepositoryImpl(
    private val database: MyAppDataBase
) : UiConfigRepository {
    
    override suspend fun getConfig(): UiConfigEntity {
        val config = database.uiConfigDao().getConfig()
        return if (config != null) {
            config
        } else {
            // Return default config with id=1 (not saved yet)
            UiConfigEntity(id = 0) // Use id=0 to indicate not saved
        }
    }
    
    override suspend fun saveConfig(config: UiConfigEntity) {
        database.uiConfigDao().insertConfig(config)
    }
    
    override suspend fun updateColors(config: UiConfigEntity) {
        database.uiConfigDao().updateColors(
            primaryColor = config.primaryColor,
            secondaryColor = config.secondaryColor,
            surfaceColor = config.surfaceColor,
            surfaceLowestColor = config.surfaceLowestColor,
            backgroundColor = config.backgroundColor,
            onSurfaceColor = config.onSurfaceColor,
            onSurfaceVariantColor = config.onSurfaceVariantColor,
            secondaryContainerColor = config.secondaryContainerColor
        )
    }
    
    
    override suspend fun updateTextSizes(config: UiConfigEntity) {
        database.uiConfigDao().updateTextSizes(
            small = config.baseTextSizeSmall,
            medium = config.baseTextSizeMedium,
            large = config.baseTextSizeLarge,
            extraLarge = config.baseTextSizeExtraLarge,
            title = config.baseTextSizeTitle,
            headline = config.baseTextSizeHeadline,
            multiplier = config.textSizeMultiplier
        )
    }
}

