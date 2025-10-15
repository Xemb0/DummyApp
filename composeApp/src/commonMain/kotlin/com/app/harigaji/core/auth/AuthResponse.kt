package com.app.harigaji.core.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val status: Int?,
    val message: String?,
    val response: LoginData? = null
)



@Serializable
data class LoginData(
   val token: String = "",
    val json_link: String = "",
   val splash_img: String = "",
   val logo_img: String = "",
   val client_name: String = "",
)

@Serializable
data class Response(
    val client_name: String,
    val json_link: String,
    val logo_img: String,
    val splash_img: String,
    val token: String
)