package com.app.harigaji.core.utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackBarEvent(
    val message: String,
    val action: SnackBarAction? = null
)

data class SnackBarAction(
    val name: String,
    val action: () -> Unit // No suspend, as we want this to be callable synchronously
)

object SnackBarController {

    private val _events = Channel<SnackBarEvent>(Channel.BUFFERED) // Buffered to avoid backpressure
    val events = _events.receiveAsFlow()

    fun sendEvent(event: SnackBarEvent) {
        _events.trySend(event).isSuccess // Try to send without blocking
    }
}
