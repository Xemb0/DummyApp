package com.app.harigaji.core.language

import kotlin.String

enum class Language(flag:String?=null, val displayName: String,val iso:String,) {
    English(iso = "en", displayName =  "English"),
    Hindi(iso = "hi", displayName =  "हिन्दी"),
}