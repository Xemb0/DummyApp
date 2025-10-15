package com.app.harigaji.core.appconfig

import kotlinx.serialization.Serializable

@Serializable
data class AppConfigResponse(
    val appConfig: AppConfig
)

@Serializable
data class AppConfig(
    val apiUrls: ApiUrls,
    val id: Int
)


@Serializable
data class ApiUrls(
    val getProductDetails: String,
    val imageUploadUrl: String,
    val loginOtpUrl: String,
    val loginValidateOtpUrl: String,
    val surveyUploadUrl: String,
    val getOrderDetails: String,
    val getNotifications: String,
    val updateFcmToken: String,
    val mostFrequentBuys: String,
)