package com.app.harigaji.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.app.harigaji.core.language.Language
import com.app.harigaji.core.user.UserDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.io.IOException

class DataStoreRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : DataStoreRepository {

    companion object {
        val TIMESTAMP_KEY = longPreferencesKey(name = "saved_timestamp")
        val ACCESS_TOKEN_KEY = stringPreferencesKey(name = "access_token")
        val USER_ID_KEY = stringPreferencesKey(name = "user_id")
        val USER_NAME_KEY = stringPreferencesKey(name = "user_name")
        val USER_EMAIL_KEY = stringPreferencesKey(name = "user_email")
        val USER_PHONE_KEY = stringPreferencesKey(name = "user_phone")
        val PROFILE_PIC_KEY = stringPreferencesKey(name = "profile_pic")
        val IS_USER_LOGGED_IN_KEY = booleanPreferencesKey("is_user_logged_in")
        val BASE_URL_KEY = stringPreferencesKey(name = "base_url")
        val LANGUAGE_KEY = stringPreferencesKey(name = "language")
        val IS_NOTIFICATION_SHOWN_TODAY_KEY = booleanPreferencesKey(name = "is_notification_shown_today")
        val NOTIFICATION_COUNT_KEY = intPreferencesKey(name = "notification_count")
    }

    override fun getUserDetails(): Flow<UserDetails> {
        return dataStore.data.map { preferences ->
            UserDetails(
                name = preferences[USER_NAME_KEY] ?: "",
                email = preferences[USER_EMAIL_KEY] ?: "",
                phone = preferences[USER_PHONE_KEY] ?: "",
                token = preferences[ACCESS_TOKEN_KEY] ?: "",
                id = preferences[USER_ID_KEY] ?: "",
                baseUrl = preferences[BASE_URL_KEY] ?: "",
                profilePic = preferences[PROFILE_PIC_KEY] ?: ""

            )
        }
    }

    override suspend fun saveUserDetails(userDetails: UserDetails) {
        dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = userDetails.name?: ""
            preferences[USER_EMAIL_KEY] = userDetails.email?: ""
            preferences[USER_PHONE_KEY] = userDetails.phone?: ""
            preferences[ACCESS_TOKEN_KEY] = userDetails.token?: ""
            preferences[USER_ID_KEY] = userDetails.id?: ""
            preferences[BASE_URL_KEY] = userDetails.baseUrl?: ""
            preferences[PROFILE_PIC_KEY] = userDetails.profilePic?: ""
        }
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return  dataStore.data.firstOrNull()?.get(IS_USER_LOGGED_IN_KEY) ?: false
    }
    override suspend fun isUserLoggedInFlow(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY].isNullOrBlank().not()
        }
    }

    override suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear() // Clears all stored data
        }
    }

    override suspend fun getAccessToken(): String {
        return dataStore.data.firstOrNull()?.get(ACCESS_TOKEN_KEY) ?: ""
    }

    override fun getAccessTokenFlow(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[ACCESS_TOKEN_KEY] ?: ""
        }
    }

    override suspend fun saveAccessToken(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN_KEY] = token
        }
    }

    override suspend fun getUserId(): String {
        return dataStore.data.firstOrNull()?.get(USER_ID_KEY) ?: ""
    }

    override suspend fun getUserName(): String {
        return dataStore.data.firstOrNull()?.get(USER_NAME_KEY) ?: ""
    }

    override suspend fun getUserEmail(): String {
        return dataStore.data.firstOrNull()?.get(USER_EMAIL_KEY) ?: ""
    }

    override suspend fun getUserPhone(): String {
        return dataStore.data.firstOrNull()?.get(USER_PHONE_KEY) ?: ""
    }

    override fun getProfilePicFlow(): Flow<String> {
        return dataStore.data.map { preferences ->
            preferences[PROFILE_PIC_KEY] ?: ""
        }
    }

    override suspend fun saveProfilePic(profilePic: String) {
        dataStore.edit { preferences ->
            preferences[PROFILE_PIC_KEY] = profilePic
        }
    }

    override suspend fun saveUserName(userName: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME_KEY] = userName
        }
    }

    override suspend fun saveUserEmail(userEmail: String) {
        dataStore.edit { preferences ->
            preferences[USER_EMAIL_KEY] = userEmail
        }
    }

    override suspend fun saveUserPhone(userPhone: String) {
        dataStore.edit { preferences ->
            preferences[USER_PHONE_KEY] = userPhone
        }
    }

    override suspend fun saveUserId(userId: String) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    override suspend fun isNotificationShownToday(): Boolean {
        return dataStore.data.firstOrNull()?.get(IS_NOTIFICATION_SHOWN_TODAY_KEY) ?: false
    }

    override suspend fun updateNotificationShownStatus(isShown: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_NOTIFICATION_SHOWN_TODAY_KEY] = isShown
        }
    }

    override suspend fun isNotificationShownTodayFlow(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[IS_NOTIFICATION_SHOWN_TODAY_KEY] ?: false
        }
    }

    override suspend fun saveCurrentLanguage(language: String) {
        dataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language
        }
        println("Language saved: $language")

    }

    override fun getCurrentLanguage(): Flow<String> {
        return dataStore.data.map { preferences ->
            val lang = preferences[LANGUAGE_KEY] ?: Language.English.iso
            println("Language fetched: $lang")
            lang
        }
    }

    override fun getPreviousNotificationCount(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[NOTIFICATION_COUNT_KEY]?.toInt() ?: 0
        }
    }

    override suspend fun updateNotificationCount(count: Int) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATION_COUNT_KEY] = count
        }
    }

    override val selectedLanguage: Flow<String> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                // Emit an empty Preferences instance so the flow can continue
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val lang = preferences[LANGUAGE_KEY] ?: Language.English.iso
            println("Language fetched: $lang")
            lang
        }



}