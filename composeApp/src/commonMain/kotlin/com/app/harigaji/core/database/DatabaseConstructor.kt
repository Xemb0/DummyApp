package com.app.harigaji.core.database

import androidx.room.RoomDatabaseConstructor

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object DatabaseConstructor: RoomDatabaseConstructor<MyAppDataBase> {
    override fun initialize(): MyAppDataBase
}