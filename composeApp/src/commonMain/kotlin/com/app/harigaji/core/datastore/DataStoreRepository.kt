package com.app.harigaji.core.datastore

import com.app.harigaji.core.user.UserDetails
import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    val selectedLanguage : Flow<String>
    suspend fun isUserLoggedInFlow(): Flow<Boolean>
    fun getUserDetails(): Flow<UserDetails>
    suspend fun saveUserDetails(userDetails: UserDetails)
    suspend fun isUserLoggedIn(): Boolean
    suspend fun logout()
    suspend fun getAccessToken(): String
    fun getAccessTokenFlow(): Flow<String>
    suspend fun saveAccessToken(token: String)
    suspend fun getUserId(): String
    suspend fun getUserName(): String
    suspend fun getUserEmail(): String
    suspend fun getUserPhone(): String
    fun getProfilePicFlow(): Flow<String>
    suspend fun saveProfilePic(profilePic: String)
    suspend fun saveUserName(userName: String)
    suspend fun saveUserEmail(userEmail: String)
    suspend fun saveUserPhone(userPhone: String)
    suspend fun saveUserId(userId: String)
    suspend fun isNotificationShownToday(): Boolean
    suspend fun isNotificationShownTodayFlow(): Flow<Boolean>
    suspend fun updateNotificationShownStatus(isShown: Boolean)
    suspend fun saveCurrentLanguage(language: String)
    fun getCurrentLanguage(): Flow<String>
    fun getPreviousNotificationCount() : Flow<Int>
    suspend fun updateNotificationCount(count: Int)
}
