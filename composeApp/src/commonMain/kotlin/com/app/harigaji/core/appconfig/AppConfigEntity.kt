package com.app.harigaji.core.appconfig

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "app_config")
data class AppConfigEntity(
    @PrimaryKey(autoGenerate = false)
    val id : Int = 0,
    val userId: String? = null,
    val surveyId: Int? = 0,
    val appVersion: String? = null,
    val releaseDate: String? = null,
    val surveyUploadUrl: String? = null,
    val imageUploadUrl: String? = null,
    val getProductDetails: String? = null,
    val offlineDropdownApi: String? = null,
    val loginValidateOtpUrl: String? = null,
    val loginOtpUrl: String? = null,
    val getOrderDetails: String? = null,
    val getNotificationUrl: String?= null,
    val updateFcmToken: String?=null,
    val mostFrequentBuys: String?=null,
)

