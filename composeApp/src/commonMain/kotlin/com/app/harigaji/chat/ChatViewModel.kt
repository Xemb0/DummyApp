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
                    ChatMessage(4, "Sure! It’s available under the Payslip tab.", "09:04 AM", false),
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
            ),
            // --- 20 More Dummy Chat Threads Below ---
            UserMessageDetails(
                id = 3,
                name = "HariGaji Support",
                profilePic = "https://randomuser.me/api/portraits/men/2.jpg",
                sender = "Support",
                message = listOf(
                    ChatMessage(14, "Good morning! Need any assistance today?", "09:15 AM", false),
                    ChatMessage(15, "Morning! Can I change my bank account?", "09:17 AM", true),
                    ChatMessage(16, "Yes, under Profile → Bank Details.", "09:19 AM", false),
                    ChatMessage(17, "Thanks, done ✅", "09:21 AM", true),
                ),
                time = "09:21 AM",
                date = "March, 2024"
            ),
            UserMessageDetails(
                id = 4,
                name = "HariGaji Admin",
                profilePic = "https://randomuser.me/api/portraits/women/2.jpg",
                sender = "Admin",
                message = listOf(
                    ChatMessage(18, "Reminder: Salary cycle closes on 25th.", "11:00 AM", false),
                    ChatMessage(19, "Noted, will check before that.", "11:02 AM", true),
                ),
                time = "11:02 AM",
                date = "March, 2024"
            ),
            UserMessageDetails(
                id = 5,
                name = "HariGaji HR",
                profilePic = "https://randomuser.me/api/portraits/men/3.jpg",
                sender = "HR",
                message = listOf(
                    ChatMessage(20, "Hi, please upload your new IC copy.", "03:15 PM", false),
                    ChatMessage(21, "Sure, uploading now.", "03:17 PM", true),
                    ChatMessage(22, "Received! Thank you.", "03:18 PM", false),
                ),
                time = "03:18 PM",
                date = "March, 2024"
            ),
            UserMessageDetails(
                id = 6,
                name = "HariGaji Bot",
                profilePic = "https://randomuser.me/api/portraits/lego/1.jpg",
                sender = "Bot",
                message = listOf(
                    ChatMessage(23, "Your attendance for today has been marked ✅", "05:00 PM", false),
                    ChatMessage(24, "Nice!", "05:01 PM", true),
                ),
                time = "05:01 PM",
                date = "March, 2024"
            ),
            UserMessageDetails(
                id = 7,
                name = "HariGaji Admin",
                profilePic = "https://randomuser.me/api/portraits/women/3.jpg",
                sender = "Admin",
                message = listOf(
                    ChatMessage(25, "Do you want to participate in this month’s survey?", "01:00 PM", false),
                    ChatMessage(26, "Sure, send the link.", "01:02 PM", true),
                    ChatMessage(27, "Here you go: forms.harigaji.com/survey", "01:03 PM", false),
                ),
                time = "01:03 PM",
                date = "March, 2024"
            ),
            UserMessageDetails(
                id = 8,
                name = "HariGaji Admin",
                profilePic = "https://randomuser.me/api/portraits/men/4.jpg",
                sender = "Admin",
                message = listOf(
                    ChatMessage(28, "Your leave request has been approved ✅", "10:00 AM", false),
                    ChatMessage(29, "Great, thank you!", "10:02 AM", true),
                ),
                time = "10:02 AM",
                date = "April, 2024"
            ),
            UserMessageDetails(
                id = 9,
                name = "HariGaji Payroll",
                profilePic = "https://randomuser.me/api/portraits/women/4.jpg",
                sender = "Payroll",
                message = listOf(
                    ChatMessage(30, "This month’s payslip is available.", "09:10 AM", false),
                    ChatMessage(31, "Awesome, downloading now.", "09:12 AM", true),
                ),
                time = "09:12 AM",
                date = "April, 2024"
            ),
            UserMessageDetails(
                id = 10,
                name = "HariGaji Admin",
                profilePic = "https://randomuser.me/api/portraits/men/5.jpg",
                sender = "Admin",
                message = listOf(
                    ChatMessage(32, "We’ve updated our Terms of Service.", "06:00 PM", false),
                    ChatMessage(33, "Okay, I’ll review it.", "06:02 PM", true),
                ),
                time = "06:02 PM",
                date = "April, 2024"
            ),
            UserMessageDetails(
                id = 11,
                name = "HariGaji Support",
                profilePic = "https://randomuser.me/api/portraits/women/5.jpg",
                sender = "Support",
                message = listOf(
                    ChatMessage(34, "Can I help you with something?", "09:40 AM", false),
                    ChatMessage(35, "Yes, my salary seems incorrect.", "09:41 AM", true),
                    ChatMessage(36, "Let’s verify that for you.", "09:42 AM", false),
                ),
                time = "09:42 AM",
                date = "April, 2024"
            ),
            UserMessageDetails(
                id = 12,
                name = "HariGaji HR",
                profilePic = "https://randomuser.me/api/portraits/men/6.jpg",
                sender = "HR",
                message = listOf(
                    ChatMessage(37, "Performance reviews start next week.", "02:00 PM", false),
                    ChatMessage(38, "Thanks for the heads-up!", "02:01 PM", true),
                ),
                time = "02:01 PM",
                date = "April, 2024"
            ),
            UserMessageDetails(
                id = 13,
                name = "HariGaji Admin",
                profilePic = "https://randomuser.me/api/portraits/women/6.jpg",
                sender = "Admin",
                message = listOf(
                    ChatMessage(39, "Holiday on 1st May: Labour Day 🎉", "11:00 AM", false),
                    ChatMessage(40, "Nice! Thanks for the info 😄", "11:01 AM", true),
                ),
                time = "11:01 AM",
                date = "April, 2024"
            ),
            UserMessageDetails(
                id = 14,
                name = "HariGaji Payroll",
                profilePic = "https://randomuser.me/api/portraits/men/7.jpg",
                sender = "Payroll",
                message = listOf(
                    ChatMessage(41, "Bonus added to this cycle.", "08:30 AM", false),
                    ChatMessage(42, "Wow, thanks!", "08:31 AM", true),
                ),
                time = "08:31 AM",
                date = "May, 2024"
            ),
            UserMessageDetails(
                id = 15,
                name = "HariGaji Admin",
                profilePic = "https://randomuser.me/api/portraits/women/7.jpg",
                sender = "Admin",
                message = listOf(
                    ChatMessage(43, "System maintenance tonight 11 PM–1 AM.", "05:30 PM", false),
                    ChatMessage(44, "Got it, will log out before that.", "05:31 PM", true),
                ),
                time = "05:31 PM",
                date = "May, 2024"
            ),
            UserMessageDetails(
                id = 16,
                name = "HariGaji Bot",
                profilePic = "https://randomuser.me/api/portraits/lego/2.jpg",
                sender = "Bot",
                message = listOf(
                    ChatMessage(45, "New payslip available for download.", "07:00 AM", false),
                    ChatMessage(46, "Downloaded ✅", "07:02 AM", true),
                ),
                time = "07:02 AM",
                date = "May, 2024"
            ),
            UserMessageDetails(
                id = 17,
                name = "HariGaji Admin",
                profilePic = "https://randomuser.me/api/portraits/men/8.jpg",
                sender = "Admin",
                message = listOf(
                    ChatMessage(47, "Please confirm your attendance for tomorrow’s meeting.", "06:00 PM", false),
                    ChatMessage(48, "Confirmed ✅", "06:01 PM", true),
                ),
                time = "06:01 PM",
                date = "May, 2024"
            ),
            UserMessageDetails(
                id = 18,
                name = "HariGaji Support",
                profilePic = "https://randomuser.me/api/portraits/women/8.jpg",
                sender = "Support",
                message = listOf(
                    ChatMessage(49, "We noticed an issue with your claim form.", "03:00 PM", false),
                    ChatMessage(50, "Oh, what’s wrong?", "03:02 PM", true),
                    ChatMessage(51, "Attachment missing — please reupload.", "03:03 PM", false),
                ),
                time = "03:03 PM",
                date = "May, 2024"
            ),
            UserMessageDetails(
                id = 19,
                name = "HariGaji HR",
                profilePic = "https://randomuser.me/api/portraits/men/9.jpg",
                sender = "HR",
                message = listOf(
                    ChatMessage(52, "Your probation review is scheduled for Monday.", "11:15 AM", false),
                    ChatMessage(53, "Thanks, noted.", "11:16 AM", true),
                ),
                time = "11:16 AM",
                date = "May, 2024"
            ),
            UserMessageDetails(
                id = 20,
                name = "HariGaji Admin",
                profilePic = "https://randomuser.me/api/portraits/women/9.jpg",
                sender = "Admin",
                message = listOf(
                    ChatMessage(54, "Please verify your contact number.", "09:00 AM", false),
                    ChatMessage(55, "Done ✅", "09:02 AM", true),
                ),
                time = "09:02 AM",
                date = "May, 2024"
            ),
            UserMessageDetails(
                id = 21,
                name = "HariGaji Admin",
                profilePic = "https://randomuser.me/api/portraits/men/10.jpg",
                sender = "Admin",
                message = listOf(
                    ChatMessage(56, "Welcome back! Anything we can help with today?", "10:00 AM", false),
                    ChatMessage(57, "All good for now, thanks!", "10:01 AM", true),
                ),
                time = "10:01 AM",
                date = "May, 2024"
            )
        )
    }
}