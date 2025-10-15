package com.app.harigaji.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.harigaji.core.appconfig.AppConfigDao
import com.app.harigaji.core.appconfig.AppConfigEntity

@Database(entities = [
    AppConfigEntity::class] ,version = 1)
@TypeConverters(Converters::class)
@ConstructedBy(DatabaseConstructor::class)

abstract class MyAppDataBase : RoomDatabase() {
    abstract fun appConfigDao(): AppConfigDao

    companion object {
        const val DB_NAME = "ds_database.db"
    }
}