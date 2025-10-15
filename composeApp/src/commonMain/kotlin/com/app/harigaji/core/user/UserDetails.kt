package com.app.harigaji.core.user

import com.app.harigaji.core.language.Language


data class UserDetails(
    val name: String?=null,
    val email: String?=null,
    val phone: String?=null,
    val token: String?=null,
    val id: String?=null,
    val baseUrl: String?=null,
    val language: String = Language.English.iso,
    val profilePic: String?=null
)