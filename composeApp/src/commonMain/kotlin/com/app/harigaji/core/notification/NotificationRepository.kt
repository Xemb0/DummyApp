package com.app.harigaji.core.notification

import kotlinx.coroutines.flow.Flow
import com.app.harigaji.data.MyAppError
import com.app.harigaji.data.MyResult

interface NotificationRepository {

    suspend fun getNotificationList(): List<NotificationDetail>
    fun getNotificationFlow(): Flow<List<NotificationDetail>>
    suspend fun updateFcmToken(fcmToken: String): MyResult<Unit, MyAppError>
    suspend fun logout()
    suspend fun refreshNotification(): MyResult<Unit, MyAppError>

    suspend fun updateNotificationCount(count: Int)
    fun getPreviousNotificationCount(): Flow<Int>
}