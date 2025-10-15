package com.app.harigaji.core.database

import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual class DataBaseFactory {
    actual fun createDatabase(): RoomDatabase.Builder<MyAppDataBase> {
        val dbFile = documentDirectory() + "/${MyAppDataBase.DB_NAME}"
        return Room.databaseBuilder<MyAppDataBase>(
            name = dbFile
        )
    }
    @OptIn(ExperimentalForeignApi::class)
    private fun documentDirectory(): String {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        return requireNotNull(documentDirectory?.path)
    }
}