package com.app.harigaji.core.uiconfig

interface UiConfigRepository {
    suspend fun getConfig(): UiConfigEntity
    suspend fun saveConfig(config: UiConfigEntity)
    suspend fun updateColors(config: UiConfigEntity)
    suspend fun updatePadding(config: UiConfigEntity)
    suspend fun updateIconSizes(config: UiConfigEntity)
    suspend fun updateTextSizes(config: UiConfigEntity)
}

