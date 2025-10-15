package com.app.harigaji.core.notification

import GenericResponse
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.app.harigaji.core.appconfig.AppConfigDao
import com.app.harigaji.core.datastore.DataStoreRepository
import com.app.harigaji.core.ktor.KtorApiClient
import com.app.harigaji.data.MyAppError
import com.app.harigaji.data.MyResult
import com.app.harigaji.data.RemoteErrorWithCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

class NotificationRepositoryImpl(
    private val apiClient: KtorApiClient,
    private val userRepository: DataStoreRepository,
    private val appConfigDao: AppConfigDao,
    private val notificationDao:NotificationDao
    ) : NotificationRepository {
    override suspend fun getNotificationList(): List<NotificationDetail> {
       return notificationDao.getAllNotifications()
    }

    override fun getNotificationFlow(): Flow<List<NotificationDetail>> {
       return notificationDao.getAllNotificationsFlow()
    }

    override suspend fun logout(){
        notificationDao.deleteAllNotifications()
    }
    override suspend fun updateFcmToken(fcmToken: String): MyResult<Unit, MyAppError> = withContext(Dispatchers.IO) {
        try {
            println("Updating FCM token...")

            val url = appConfigDao.getUpdateFcmUrl()
            val token = appConfigDao.getAccessToken()

            println("FCM Token Update URL: $url")
            println("Token: $token")

            val body = UpdateFcmTokenRequest(fcm_token = fcmToken)

            val response = apiClient.post<GenericResponse, UpdateFcmTokenRequest>(
                route = url,
                header = mapOf("Authorization" to "Basic $token"),
                body = body
            )

            val responseData = response.data
            if (responseData?.status == 200) {
                println("FCM token updated successfully")
                MyResult.Success(Unit)
            } else {
                println("FCM update failed: status=${responseData?.status}, message=${responseData?.message}")
                MyResult.Error(
                    RemoteErrorWithCode(
                        error = MyAppError.Remote.UNKNOWN_ERROR,
                        code = responseData?.status ?: -1,
                        message = responseData?.message ?: "Unknown error occurred"
                    )
                )
            }
        } catch (e: Exception) {
            println("Exception during FCM token update: ${e.message}")
            MyResult.Error(
                RemoteErrorWithCode(
                    error = MyAppError.Remote.SERVER_ERROR,
                    code = -1,
                    message = e.message ?: "Network or unexpected error"
                )
            )
        }
    }

     override suspend fun updateNotificationCount(count: Int) {
         userRepository.updateNotificationCount(count)

    }
     override fun getPreviousNotificationCount(): Flow<Int> {
        return userRepository.getPreviousNotificationCount()
    }

    override suspend fun refreshNotification(): MyResult<Unit, MyAppError> = withContext(Dispatchers.IO) {
        try {
            println("Fetching Notification details...")

            val url = appConfigDao.getNotificationUrl()
            val token = userRepository.getAccessToken()

            println("Notification Details URL: $url")
            println("Token: $token")

            val response = apiClient.post<NotificationResponse, Unit>(
                route = url,
                header = mapOf("Authorization" to "Basic $token"),
                body = Unit
            )

            val notificationsResponse = response.data
            if (notificationsResponse != null && notificationsResponse.status == 200) {

                val notificationDetailsList = notificationsResponse.response.notificationDetails.map { notification ->
                    NotificationDetail(
                        notificationId = notification.notificationId,
                        notificationType = notification.notificationType,
                        teamId = notification.teamId,
                        notificationTitle = notification.notificationTitle,
                        notificationText = notification.notificationText,
                        notificationDateTime = notification.notificationDateTime,
                        dateTimeMili = notification.dateTimeMili,
                        color = notification.color,
                        icon = notification.icon
                    )
                }
                notificationDao.replaceAllNotifications(notificationDetailsList)

                println("Inserted ${notificationDetailsList.size} notifications into DB.")

                MyResult.Success(Unit)
            } else {
                println("Notification request failed: status=${notificationsResponse?.status}, message=${notificationsResponse?.message}")
                MyResult.Error(
                    RemoteErrorWithCode(
                        error = MyAppError.Remote.UNKNOWN_ERROR,
                        code = notificationsResponse?.status ?: -1,
                        message = notificationsResponse?.message ?: "Unknown error occurred"
                    )
                )
            }
        } catch (e: Exception) {
            println("Exception during Notification request: ${e.message}")
            MyResult.Error(
                RemoteErrorWithCode(
                    error = MyAppError.Remote.SERVER_ERROR,
                    code = -1,
                    message = e.message ?: "Network or unexpected error"
                )
            )
        }
    }


}

