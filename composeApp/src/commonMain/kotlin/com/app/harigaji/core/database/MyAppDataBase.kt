package com.app.harigaji.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.harigaji.core.appconfig.AppConfigDao
import com.app.harigaji.core.appconfig.AppConfigEntity
import com.app.harigaji.core.notification.NotificationDao
import com.app.harigaji.core.notification.NotificationDetail
import com.app.harigaji.core.uiconfig.UiConfigDao
import com.app.harigaji.core.uiconfig.UiConfigEntity
import com.app.harigaji.data.UserProgressDetail
import com.app.harigaji.user.domain.UserProgressDao

@Database(entities = [AppConfigEntity::class,NotificationDetail::class, UserProgressDetail::class, UiConfigEntity::class] ,version = 7)
@TypeConverters(Converters::class)
@ConstructedBy(DatabaseConstructor::class)

abstract class MyAppDataBase : RoomDatabase() {
    abstract fun appConfigDao(): AppConfigDao

    abstract fun NotificationDao(): NotificationDao

    abstract fun userProgressDao(): UserProgressDao
    
    abstract fun uiConfigDao(): UiConfigDao

    companion object {
        const val DB_NAME = "ds_database.db"
    }
}