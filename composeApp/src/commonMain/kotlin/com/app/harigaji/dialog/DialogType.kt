package com.app.harigaji.dialog

sealed class DialogType {
    object Success : DialogType()
    object Error : DialogType()
    object Warning : DialogType()
    object Info : DialogType()
}

