package com.app.harigaji.about

sealed class PolicyUiEvent {
    data class ShowSnackBar(val message: String) : PolicyUiEvent()
}