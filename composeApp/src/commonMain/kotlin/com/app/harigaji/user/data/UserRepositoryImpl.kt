package com.app.harigaji.user.data

import com.app.harigaji.data.MyAppError
import com.app.harigaji.data.MyResult
import com.app.harigaji.data.UserProgressDetail
import com.app.harigaji.user.domain.UserProgressDao
import com.app.harigaji.user.domain.UserRepository
import kotlinx.coroutines.flow.Flow
import org.jetbrains.compose.resources.Resource

class UserProgressRepositoryImpl(
    private val userDao: UserProgressDao
) : UserRepository {


    override suspend fun updateUserDetails(user: UserProgressDetail?) {
        if(user == null) {
            return
        }
        userDao.insertUser(user)
    }
    override suspend fun insertUserDetails(user: UserProgressDetail) {
        userDao.insertUser(user)
    }

    override fun getUserProgressDetails(): Flow<UserProgressDetail?> {
        return userDao.getUserFlow()
    }

    override suspend fun getProfile(): MyResult<UserProgressDetail, MyAppError> {
        return try {
            val user = userDao.getUser()
            if (user != null) {
                MyResult.Success(user)
            } else {
                MyResult.Error(
                    MyAppError.Local.NOT_FOUND_ERROR
                )
            }
        } catch (e: Exception) {
            MyResult.Error(
                MyAppError.Local.UNKNOWN_ERROR
            )
        }
    }

    override suspend fun deleteAll() {
        userDao.deleteUser()
    }

}