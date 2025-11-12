package com.app.harigaji.dialog

data class DialogConfig(
    val type: DialogType = DialogType.Success,
    val title: String,
    val message: String,
    val confirmText: String = "Done",
    val cancelText: String? = null,
    val onConfirm: () -> Unit = {},
    val onCancel: () -> Unit = {},
    val onDismiss: () -> Unit = {}
)
