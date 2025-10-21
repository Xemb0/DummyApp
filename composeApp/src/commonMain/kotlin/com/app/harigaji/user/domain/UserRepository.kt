package com.app.harigaji.user.domain

import com.app.harigaji.data.MyAppError
import com.app.harigaji.data.MyResult
import com.app.harigaji.data.UserProgressDetail
import kotlinx.coroutines.flow.Flow

interface UserRepository{
    suspend fun getProfile(): MyResult<UserProgressDetail, MyAppError>
    fun getUserProgressDetails(): Flow<UserProgressDetail?>
    suspend fun updateUserDetails(user: UserProgressDetail?)
    suspend fun deleteAll()
    suspend fun insertUserDetails(user: UserProgressDetail)

}