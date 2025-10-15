package com.app.harigaji.core.notification

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


@Dao
interface NotificationDao {

    @Query("SELECT * FROM NotificationDetail ORDER BY notificationDateTime DESC")
    fun getAllNotificationsFlow(): Flow<List<NotificationDetail>>

    @Query("SELECT * FROM NotificationDetail WHERE notificationId = :id LIMIT 1")
    fun getNotificationByIdFlow(id: String): Flow<NotificationDetail?>

    @Query("SELECT * FROM NotificationDetail ORDER BY notificationDateTime DESC")
    suspend fun getAllNotifications(): List<NotificationDetail>

    @Query("SELECT * FROM NotificationDetail WHERE notificationId = :id LIMIT 1")
    suspend fun getNotificationById(id: String): NotificationDetail?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationDetail>)

    @Query("DELETE FROM NotificationDetail")
    suspend fun deleteAllNotifications()

    @Delete
    suspend fun deleteNotification(notification: NotificationDetail)

    // ✅ Combined operation inside a transaction
    @Transaction
    suspend fun replaceAllNotifications(notifications: List<NotificationDetail>) {
        deleteAllNotifications()
        insertNotifications(notifications)
    }

    @Query("SELECT COUNT(*) FROM NotificationDetail WHERE isRead = 0")
    fun getUnreadNotificationCount(): Flow<Int>

    @Query("UPDATE NotificationDetail SET isRead = 1 WHERE notificationId = :notificationId")
    suspend fun markNotificationAsRead(notificationId: String)

    @Query("UPDATE NotificationDetail SET isRead = true")
    suspend fun markAllNotificationsAsRead()
}
