package com.app.harigaji.core.database

import androidx.room.RoomDatabase

expect class DataBaseFactory {
    fun createDatabase(): RoomDatabase.Builder<MyAppDataBase>
}