package com.app.harigaji.core.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.app.harigaji.core.datastore.DataStoreRepository
import com.app.harigaji.core.user.UserDetails
import com.app.harigaji.data.MyAppError
import com.app.harigaji.data.MyResult
import com.app.harigaji.data.RemoteErrorWithCode
import com.app.harigaji.data.UiEvent


data class AuthScreenState(
    val phone: String = "",
    val isLoading: Boolean = false,
    val user: UserDetails? = null,
    val error: String? = null,
    val otp: List<Int?> = (1..4).map { null },
    val focusedIndex: Int? = null,
    val isOTPValid: Boolean? = null
)

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val userRepository: DataStoreRepository,
) : ViewModel() {



init {
    viewModelScope.launch {
        authRepository.refreshAppConfig()
    }
}

    private val _state = MutableStateFlow(AuthScreenState())
    val state: StateFlow<AuthScreenState> = _state.asStateFlow()

    private val _uiEvent: Channel<UiEvent> = Channel()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.Input.EnterPhone -> {
                _state.update { it.copy(phone = event.value) }
            }


            is UiEvent.OtpAction.OnChangeFieldFocused -> {
                _state.update { it.copy(
                    focusedIndex = event.index
                ) }
            }
            is UiEvent.OtpAction.OnEnterNumber -> {
                enterNumber(event.number, event.index)
            }
            UiEvent.OtpAction.OnKeyboardBack -> {
                onKeyboardBack()
            }

            is UiEvent.Auth.RequestOtp -> {
                requestOtp()
            }

            is UiEvent.Auth.VerifyOtp -> {
                verifyOtp()
            }

            else -> Unit
        }
    }

    private fun onKeyboardBack() {
        val previousIndex = getPreviousFocusedIndex(state.value.focusedIndex) ?: return
        _state.update {
            it.copy(
                otp = it.otp.mapIndexed { index, number ->
                    if (index == previousIndex) null else number
                },
                focusedIndex = previousIndex
            )
        }
    }

    private fun enterNumber(number: Int?, index: Int) {

        fun isOTPValid(): Boolean {
            val otp = _state.value.otp.mapNotNull { it?.toString() }.joinToString("")
            return otp.length == 4
        }
        val newCode = state.value.otp.mapIndexed { currentIndex, currentNumber ->
            if(currentIndex == index) {
                number
            } else {
                currentNumber
            }
        }
        val wasNumberRemoved = number == null
        _state.update { it.copy(
            otp = newCode,
            focusedIndex = if(wasNumberRemoved || it.otp.getOrNull(index) != null) {
                it.focusedIndex
            } else {
                getNextFocusedTextFieldIndex(
                    currentCode = it.otp,
                    currentFocusedIndex = it.focusedIndex
                )
            },
            isOTPValid = if(newCode.none { it == null }) {
                isOTPValid()
            } else null
        ) }
        if(isOTPValid()){
            _state.update { it.copy(isLoading = true) }
            verifyOtp()
        }
    }

    private fun getPreviousFocusedIndex(currentIndex: Int?): Int? {
        return currentIndex?.minus(1)?.coerceAtLeast(0)
    }

    private fun getNextFocusedTextFieldIndex(
        currentCode: List<Int?>,
        currentFocusedIndex: Int?
    ): Int? {
        if(currentFocusedIndex == null) {
            return null
        }

        if(currentFocusedIndex == 3) {
            return currentFocusedIndex
        }

        return getFirstEmptyFieldIndexAfterFocusedIndex(
            code = currentCode,
            currentFocusedIndex = currentFocusedIndex
        )
    }

    private fun getFirstEmptyFieldIndexAfterFocusedIndex(
        code: List<Int?>,
        currentFocusedIndex: Int
    ): Int {
        code.forEachIndexed { index, number ->
            if(index <= currentFocusedIndex) {
                return@forEachIndexed
            }
            if(number == null) {
                return index
            }
        }
        return currentFocusedIndex
    }

    private fun requestOtp() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val result = authRepository.requestOtp(_state.value.phone, type = 1)
            println("Request OTP result: $result")
            _state.update { it.copy(isLoading = false) }
            when (result) {
                is MyResult.Success -> {
                    _uiEvent.send(UiEvent.ShowSnackBar("OTP sent successfully"))
                    _uiEvent.send(UiEvent.Navigate.EnterOTP)
                }
                is MyResult.Error -> handleError(MyAppError.Remote.CLIENT_ERROR, "Error sending OTP")
            }
        }
    }

     fun verifyOtp() {
        viewModelScope.launch {
            val otp = _state.value.otp.mapNotNull { it?.toString() }.joinToString("")
            if (otp.length != 4) {
                _uiEvent.send(UiEvent.ShowSnackBar("Please enter a valid 4-digit OTP"))
                return@launch
            }

            _state.update { it.copy(isLoading = true) }
            val result = authRepository.verifyOtp(_state.value.phone, otp, type = 1)
            println("Verify OTP result: $result")
            _state.update { it.copy(isLoading = false) }

            when (result) {
                is MyResult.Success -> {
                    authRepository.refreshAppConfig()
                        _uiEvent.send(UiEvent.ShowSnackBar("${result.data?.response?.client_name} logged in successfully"))
                        _uiEvent.send(UiEvent.Navigate.HolderScreen)
                    _uiEvent.send(UiEvent.Auth.LoginSuccess(result.data?.response))

                }
                is MyResult.Error -> handleError(
                    MyAppError.Remote.CLIENT_ERROR
                , "Error verifying OTP")
            }
        }
    }

    private suspend fun handleError(error: MyAppError, defaultMessage: String) {
        val message = when (error) {
            is RemoteErrorWithCode -> error.message ?: defaultMessage
            is MyAppError.Remote -> when (error) {
                MyAppError.Remote.UNKNOWN_ERROR -> "Unknown error"
                MyAppError.Remote.SERVER_ERROR -> "Server error"
                MyAppError.Remote.CLIENT_ERROR -> "Unauthorized"
                MyAppError.Remote.REDIRECTION_ERROR -> "Forbidden"
                MyAppError.Remote.NO_INTERNET_CONNECTION_ERROR -> "No internet connection"
                MyAppError.Remote.SERIALIZATION_ERROR -> "Serialization error"
            }
            is MyAppError.Local -> when (error) {
                MyAppError.Local.UNKNOWN_ERROR -> "Unknown error"
                MyAppError.Local.DATABASE_ERROR -> "Database error"
                MyAppError.Local.FILE_ERROR -> "File error"
                MyAppError.Local.NOT_FOUND_ERROR -> "Not found error"
                MyAppError.Local.UNKNOWN -> "Unknown error"
            }
        }
        _uiEvent.send(UiEvent.ShowSnackBar(message))
    }
}
