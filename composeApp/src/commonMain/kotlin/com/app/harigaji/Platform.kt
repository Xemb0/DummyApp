package com.app.harigaji

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform