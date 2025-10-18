package com.app.harigaji.core.notification.di

import com.app.harigaji.core.database.DataBaseFactory
import com.app.harigaji.core.database.MyAppDataBase
import com.app.harigaji.core.notification.NotificationRepository
import com.app.harigaji.core.notification.NotificationRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val notificationModule = module {
    singleOf(::NotificationRepositoryImpl).bind<NotificationRepository>()
    single { get<MyAppDataBase>().NotificationDao()  }
}