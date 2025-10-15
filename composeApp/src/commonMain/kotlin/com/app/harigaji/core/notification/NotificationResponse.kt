package com.app.harigaji.core.notification

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@kotlinx.serialization.Serializable
data class NotificationResponse(
    val status: Int,
    val message: String,
    val response: ListNotificationDetails
)
@kotlinx.serialization.Serializable
data class ListNotificationDetails(
    val notificationDetails: List<NotificationDetail>
)

@kotlinx.serialization.Serializable
@Entity
data class NotificationDetail(
    @PrimaryKey(autoGenerate = false)
    val notificationId: String,
    val notificationType: Int,
    val teamId: String,
    val notificationTitle: String,
    val notificationText: String,
    val notificationDateTime: String,
    val dateTimeMili: Long = 0L,
    val color: String?=null,
    val icon: String?=null,
    val imageIfAny: String?=null,
    val isRead: Boolean = false,
)

@Serializable
data class UpdateFcmTokenRequest(
    val fcm_token: String
)