package com.app.harigaji.data

sealed interface MyAppError {
    enum class Remote : MyAppError {
        CLIENT_ERROR,
        REDIRECTION_ERROR,
        SERVER_ERROR,
        NO_INTERNET_CONNECTION_ERROR,
        SERIALIZATION_ERROR,
        UNKNOWN_ERROR,
    }

    enum class Local : MyAppError {
        DATABASE_ERROR,
        FILE_ERROR,
        UNKNOWN_ERROR,
        NOT_FOUND_ERROR,
        UNKNOWN
    }

}


data class RemoteErrorWithCode(
    val error: MyAppError,
    val code: Int?=999,
    val message: String?=""
): MyAppError