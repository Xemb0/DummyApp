package com.app.harigaji.core.appconfig

import kotlinx.coroutines.flow.Flow

class AppConfigRepositoryImpl(
    private val appConfigDao: AppConfigDao,
): AppConfigRepository {


    override  fun getAppConfig(): Flow<AppConfigEntity?> {
        return appConfigDao.getAppConfig()
    }

    override suspend fun saveAppConfig(appConfigEntity: AppConfigEntity) {
        appConfigDao.insertUrls(appConfigEntity)
    }

    override suspend fun deleteAll() {
        appConfigDao.deleteUrls()
    }


}