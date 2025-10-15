package com.app.harigaji.core.appconfig

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AppConfigDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUrls(urls: AppConfigEntity)

    @Query("SELECT * FROM app_config LIMIT 1")
    fun getAppConfig(): Flow<AppConfigEntity?>

    @Query("SELECT imageUploadUrl FROM app_config  ")
    suspend fun getImageUploadUrl(  ): String

    @Query("SELECT surveyUploadUrl FROM app_config")
    suspend fun getSurveyUploadUrl(  ): String

    @Query("SELECT userId FROM app_config ")
    suspend fun getAccessToken(): String

    @Query("SELECT * FROM app_config LIMIT 1")
    suspend fun getAllUrls(): AppConfigEntity?

    @Query("DELETE FROM app_config")
    suspend fun deleteUrls()

    @Query("SELECT getNotificationUrl FROM app_config")
    suspend fun getNotificationUrl(): String


    @Query("SELECT appVersion FROM app_config  ")
    suspend fun getCurrentAppVersion(  ): String

    @Query("SELECT updateFcmToken FROM app_config")
    suspend fun getUpdateFcmUrl(): String

    @Query("SELECT mostFrequentBuys FROM app_config")
    suspend fun getMostFrequentBuysUrl(): String



}