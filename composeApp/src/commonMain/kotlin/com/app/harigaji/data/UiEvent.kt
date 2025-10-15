package com.app.harigaji.data

import com.app.harigaji.core.auth.LoginData

sealed class UiEvent {
    data object Idle : UiEvent()
    
    sealed class Input : UiEvent() {
        data class EnterPhone(val value: String) : UiEvent()
        data class EnterOtp(val value: String) : UiEvent()
    }

    sealed class Auth : UiEvent() {
        data object RequestOtp : UiEvent()
        data object VerifyOtp : UiEvent()
        data object Logout : UiEvent()
        data class LoginSuccess(val loginData: LoginData?) : UiEvent()
    }

    sealed class Navigate : UiEvent() {
        data object HolderScreen : UiEvent()
        data object EnterOTP : UiEvent()
        data object SendOTP : UiEvent()
    }

    sealed class OtpAction : UiEvent() {
        data class OnEnterNumber(val number: Int?, val index: Int): UiEvent()
        data class OnChangeFieldFocused(val index: Int): UiEvent()
        data object OnKeyboardBack: UiEvent()
    }

    data class ShowSnackBar(val message: String) : UiEvent()
}
