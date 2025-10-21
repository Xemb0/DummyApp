
package com.app.harigaji.chat


data class ChatMessage(
    val id: Int,
    val message: String,
    val time: String,
    val isSent: Boolean,
    val avatarUrl: String? = null
)

data class UserMessageDetails(
    val id: Int = 0,
    val name: String? = null,
    val profilePic: String? = null,
    val sender: String,
    val message: List<ChatMessage>,
    val time: String,
    val date: String?
)