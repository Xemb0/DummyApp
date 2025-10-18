package com.app.harigaji.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.harigaji.core.appconfig.AppConfigDao
import com.app.harigaji.core.appconfig.AppConfigEntity
import com.app.harigaji.core.notification.NotificationDao
import com.app.harigaji.core.notification.NotificationDetail

@Database(entities = [
    AppConfigEntity::class,NotificationDetail::class] ,version = 1)
@TypeConverters(Converters::class)
@ConstructedBy(DatabaseConstructor::class)

abstract class MyAppDataBase : RoomDatabase() {
    abstract fun appConfigDao(): AppConfigDao

    abstract fun NotificationDao(): NotificationDao

    companion object {
        const val DB_NAME = "ds_database.db"
    }
}