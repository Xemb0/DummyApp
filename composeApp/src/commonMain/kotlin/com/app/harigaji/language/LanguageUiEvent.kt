package com.app.harigaji.language

sealed class LanguageUiEvent {
    data class ShowSnackBar(val message: String) : LanguageUiEvent()
    data class LanguageChanged(val language: String) : LanguageUiEvent()
}