package com.app.harigaji.data

sealed class MyResult<D,E:MyAppError>(
    val data: D?=null,
    val error: MyAppError?=null
) {
    class Success<D>(
         data: D?
    ): MyResult<D, MyAppError>(data)
    class Error<D>(
         error: MyAppError?
    ): MyResult<D, MyAppError>(null, error)
}