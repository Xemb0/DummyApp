package com.app.harigaji.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

actual class DataBaseFactory(private val context: Context) {
    actual fun createDatabase(): RoomDatabase.Builder<MyAppDataBase> {
        val appContext = context.applicationContext
        val dbFile = appContext.getDatabasePath(MyAppDataBase.DB_NAME)

        return Room.databaseBuilder(
            context = appContext,
            name = dbFile.absolutePath
        )
    }
}