// TokenDao.kt
package com.app.harigaji.user.domain

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.harigaji.data.UserProgressDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProgressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserProgressDetail)

    @Query("SELECT * FROM UserProgressDetail LIMIT 1")
    suspend fun getUser(): UserProgressDetail?

    @Query("DELETE FROM UserProgressDetail")
    suspend fun clearUsers()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateToken(token: UserProgressDetail)

    @Query("SELECT * FROM UserProgressDetail WHERE id = 1 LIMIT 1")
    fun getToken(): Flow<UserProgressDetail?>


    @Query("SELECT * FROM UserProgressDetail WHERE id = 1 LIMIT 1")
    suspend  fun getLoginToken(): UserProgressDetail?


    @Query("DELETE FROM UserProgressDetail")
    suspend fun deleteToken()


    @Query("DELETE FROM UserProgressDetail")
    suspend fun deleteUser()

    @Query("SELECT * FROM UserProgressDetail")
    fun getUserFlow():Flow<UserProgressDetail?>

}