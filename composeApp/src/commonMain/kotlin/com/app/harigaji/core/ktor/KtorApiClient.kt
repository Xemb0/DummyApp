package com.app.harigaji.core.ktor

import com.app.harigaji.data.MyAppError
import com.app.harigaji.data.MyResult
import com.app.harigaji.data.RemoteErrorWithCode
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.CancellationException
import kotlinx.serialization.SerializationException

class KtorApiClient (
    val httpClient: HttpClient
) {


    fun buildUrl(route: String, parameters: Map<String, Any> = emptyMap()): String {
        val fullUrl = if (route.startsWith("http://") || route.startsWith("https://")) {
            route
        } else {
            "$baseUrl/$route"
        }
        // Append query parameters if any.
        return if (parameters.isNotEmpty()) {
            fullUrl + "?" + parameters.entries.joinToString("&") {
                "${it.key}=${it.value}"
            }
        } else {
            fullUrl
        }
    }


    suspend inline fun <reified R : Any> get(
        route: String,
        parameters: Map<String, Any> = emptyMap(),
        header: Map<String, String> = emptyMap()
    ): MyResult<R, MyAppError> {
        return safeCall {
            httpClient.get {
                // Use our custom URL builder function.
                url(buildUrl(route, parameters))
                header.forEach { (key, value) ->
                    header(key, value)
                }
            }
        }
    }

    suspend inline fun <reified R : Any, reified B: Any> post(
        route: String,
        body: B,
        parameters: Map<String, Any> = emptyMap(),
        header: Map<String, String> = emptyMap()
    ): MyResult<R, MyAppError> {
        return safeCall {
            httpClient.post {
                url(buildUrl(route, parameters))
                parameters.forEach { (key, value) ->
                    parameter(key, value)
                }
                setBody(body)
                header.forEach { (key, value) ->
                    header(key, value)
                }
            }
        }
    }


    suspend inline fun <reified R> safeCall(
        execute: () -> HttpResponse
    ): MyResult<R, MyAppError> {
        val response = try {
            execute()
        } catch (e: UnresolvedAddressException) {
            e.printStackTrace()
            return MyResult.Error(
                RemoteErrorWithCode(MyAppError.Remote.NO_INTERNET_CONNECTION_ERROR)
            )
        } catch (e: SerializationException) {
            e.printStackTrace()
            return MyResult.Error(
                RemoteErrorWithCode(MyAppError.Remote.SERIALIZATION_ERROR)
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
            return MyResult.Error(
                RemoteErrorWithCode(MyAppError.Remote.UNKNOWN_ERROR)
            )
        }
        return responseToResult(response)
    }

    suspend inline fun<reified R> responseToResult(
        response: HttpResponse
    ): MyResult<R, MyAppError> {
        return when(response.status.value) {
            in 200..299 ->
                MyResult.Success(
                    response.body<R>()
                )
            in 300..399 -> MyResult.Error(
                RemoteErrorWithCode(MyAppError.Remote.REDIRECTION_ERROR, response.status.value)
            )
            in 400..499 -> MyResult.Error(
                RemoteErrorWithCode(MyAppError.Remote.CLIENT_ERROR, response.status.value)
            )
            in 500..599 -> MyResult.Error(
                RemoteErrorWithCode(MyAppError.Remote.SERVER_ERROR, response.status.value)
            )
            else -> MyResult.Error(
                RemoteErrorWithCode(MyAppError.Remote.UNKNOWN_ERROR, response.status.value)
            )
        }
    }

    val baseUrl = "https://api.harigaji.com"
}