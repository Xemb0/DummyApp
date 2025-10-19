package com.app.harigaji.core.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun extract12HourTime(epochMillis: Long): String {
    val instant = Instant.fromEpochMilliseconds(epochMillis)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    val localTime = localDateTime.time

    val hour12 = when {
        localTime.hour == 0 -> 12
        localTime.hour > 12 -> localTime.hour - 12
        else -> localTime.hour
    }

    val amPm = if (localTime.hour >= 12) "PM" else "AM"

    // Manual string formatting to avoid any conflicts
    val hourStr = if (hour12 < 10) "0$hour12" else hour12.toString()
    val minuteStr = if (localTime.minute < 10) "0${localTime.minute}" else localTime.minute.toString()
    val secondStr = if (localTime.second < 10) "0${localTime.second}" else localTime.second.toString()

    return "$hourStr:$minuteStr:$secondStr $amPm"
}