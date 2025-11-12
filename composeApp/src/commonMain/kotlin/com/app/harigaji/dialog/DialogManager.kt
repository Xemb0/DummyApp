package com.app.harigaji.dialog

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object DialogManager {
    private val _dialogState = MutableStateFlow<DialogConfig?>(null)
    val dialogState: StateFlow<DialogConfig?> = _dialogState.asStateFlow()

    fun showDialog(config: DialogConfig) {
        _dialogState.value = config
    }

    fun hideDialog() {
        _dialogState.value = null
    }
}