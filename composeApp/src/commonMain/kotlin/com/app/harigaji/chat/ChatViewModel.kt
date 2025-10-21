package com.app.harigaji.chat

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<UserMessageDetails>>(emptyList())
    val messages: StateFlow<List<UserMessageDetails>> = _messages

    init {
        loadDummyMessages()
    }
    private fun loadDummyMessages() {
        _messages.value = listOf(
            UserMessageDetails(
                id = 1,
                name = "HariGaji Admin",
                profilePic = "https://randomuser.me/api/portraits/men/1.jpg",
                sender = "Admin",
                message = listOf(
                    ChatMessage(1, "Hi there 👋, welcome to HariGaji!", "09:00 AM", false),
                    ChatMessage(2, "How can we assist you today?", "09:01 AM", false),
                    ChatMessage(3, "I need help checking my salary slip", "09:03 AM", true),
                    ChatMessage(
                        4,
                        "Sure! It’s available under the Payslip tab.",
                        "09:04 AM",
                        false
                    ),
                    ChatMessage(5, "Got it, thanks!", "09:05 AM", true),
                    ChatMessage(6, "Anytime 👍", "09:06 AM", false),
                    ChatMessage(7, "Also, when will next payout happen?", "09:08 AM", true),
                    ChatMessage(8, "Expected on 28th February.", "09:10 AM", false),
                ),
                time = "09:10 AM",
                date = "February, 2024"
            ),
            UserMessageDetails(
                id = 2,
                name = "HariGaji Admin",
                profilePic = "https://randomuser.me/api/portraits/women/1.jpg",
                sender = "Admin",
                message = listOf(
                    ChatMessage(9, "Salary updated: RM 5300", "10:15 AM", false),
                    ChatMessage(10, "Thank you, received notification.", "10:16 AM", true),
                    ChatMessage(11, "Would you like to view breakdown?", "10:17 AM", false),
                    ChatMessage(12, "Yes, please.", "10:18 AM", true),
                    ChatMessage(13, "Opening breakdown for you now...", "10:19 AM", false),
                ),
                time = "10:19 AM",
                date = "March, 2024"
            )
        )
    }
}