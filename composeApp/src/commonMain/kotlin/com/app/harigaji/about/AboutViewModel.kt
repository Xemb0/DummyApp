package com.app.harigaji.about

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AboutViewModel(
    private val repository: AboutRepository
) : ViewModel() {

    private val _policyState = MutableStateFlow(PolicyState())
    val policyState: StateFlow<PolicyState> = _policyState.asStateFlow()

    private val _termsAndConditionsState = MutableStateFlow(TermsAndConditionsState())
    val termsAndConditionsState: StateFlow<TermsAndConditionsState> = _termsAndConditionsState.asStateFlow()


    private val _uiEvent = Channel<PolicyUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun loadPrivacyPolicy() {
        viewModelScope.launch {
            _policyState.update { it.copy(isLoading = true, error = null) }

            repository.getPrivacyPolicySections().fold(
                onSuccess = { sections ->
                    _policyState.update {
                        it.copy(
                            isLoading = false,
                            sections = sections
                        )
                    }
                },
                onFailure = { error ->
                    _policyState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to load privacy policy"
                        )
                    }
                    _uiEvent.send(
                        PolicyUiEvent.ShowSnackBar(
                            error.message ?: "Failed to load privacy policy"
                        )
                    )
                }
            )
        }
    }

    fun loadTermsAndConditions() {
        viewModelScope.launch {
            _termsAndConditionsState.update { it.copy(isLoading = true, error = null) }

            repository.getTermsAndConditionsSections().fold(
                onSuccess = { sections ->
                    _termsAndConditionsState.update {
                        it.copy(
                            isLoading = false,
                            termsAndConditions = sections
                        )
                    }
                },
                onFailure = { error ->
                    _termsAndConditionsState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Failed to load terms and conditions"
                        )
                    }
                    _uiEvent.send(
                        PolicyUiEvent.ShowSnackBar(
                            error.message ?: "Failed to load terms and conditions"
                        )
                    )
                }
            )
        }
    }
}