package com.app.harigaji.language

data class LanguageState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedLanguage: String? = null
)
