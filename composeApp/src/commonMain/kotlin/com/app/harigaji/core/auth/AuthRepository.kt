package com.app.harigaji.core.auth

import com.app.harigaji.data.MyAppError
import com.app.harigaji.data.MyResult


interface AuthRepository {
    suspend fun requestOtp(phone: String,type: Int): MyResult<SendOTPResponse, MyAppError>
    suspend fun verifyOtp(phone: String, otp: String,type: Int): MyResult<AuthResponse, MyAppError>
    suspend fun refreshAppConfig()
    suspend fun logout()
    suspend fun login(userName: String, password: String) : MyResult<AuthResponse, MyAppError>

}
