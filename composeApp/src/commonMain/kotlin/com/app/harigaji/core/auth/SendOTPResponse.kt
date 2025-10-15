package com.app.harigaji.core.auth

import kotlinx.serialization.Serializable

@Serializable
data class SendOTPResponse(
    val message: String,
    val status: Int
)