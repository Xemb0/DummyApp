package com.app.harigaji.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun DialogHost() {
    val dialogConfig by DialogManager.dialogState.collectAsState()

    dialogConfig?.let { config ->
        AppDialog(
            config = config,
            onDismiss = { DialogManager.hideDialog() }
        )
    }
}