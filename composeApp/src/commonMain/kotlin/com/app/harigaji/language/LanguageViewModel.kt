package com.app.harigaji.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LanguageViewModel(
    private val repository: LanguageRepository
) : ViewModel() {

    private val _languageState = MutableStateFlow(LanguageState())
    val languageState: StateFlow<LanguageState> = _languageState.asStateFlow()

    private val _uiEvent = Channel<LanguageUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onLanguageSelected(language: String) {
        viewModelScope.launch {
            _languageState.update { it.copy(isLoading = true, error = null) }

            repository.saveLanguagePreference(language).fold(
                onSuccess = {
                    _languageState.update {
                        it.copy(
                            isLoading = false,
                            selectedLanguage = language
                        )
                    }
                    _uiEvent.send(LanguageUiEvent.LanguageChanged(language))
                },
                onFailure = { error ->
                    _languageState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to save language preference"
                        )
                    }
                    _uiEvent.send(
                        LanguageUiEvent.ShowSnackBar(
                            error.message ?: "Failed to save language preference"
                        )
                    )
                }
            )
        }
    }

    fun loadCurrentLanguage() {
        viewModelScope.launch {
            _languageState.update { it.copy(isLoading = true) }

            repository.getLanguagePreference().fold(
                onSuccess = { language ->
                    _languageState.update {
                        it.copy(
                            isLoading = false,
                            selectedLanguage = language
                        )
                    }
                },
                onFailure = { error ->
                    _languageState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to load language preference"
                        )
                    }
                }
            )
        }
    }
}