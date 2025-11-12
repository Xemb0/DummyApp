package com.app.harigaji.language

interface LanguageRepository {
    suspend fun saveLanguagePreference(language: String): Result<Unit>
    suspend fun getLanguagePreference(): Result<String>
}
