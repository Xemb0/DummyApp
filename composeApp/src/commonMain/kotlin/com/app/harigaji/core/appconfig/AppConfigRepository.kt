package com.app.harigaji.core.appconfig


import kotlinx.coroutines.flow.Flow

interface AppConfigRepository {
    fun getAppConfig(): Flow<AppConfigEntity?>
    suspend fun saveAppConfig(appConfigEntity: AppConfigEntity)
    suspend fun deleteAll()
}