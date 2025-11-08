package com.app.harigaji.core.di

import com.app.harigaji.artical.articleModule
import com.app.harigaji.chat.chatModule
import com.app.harigaji.core.appconfig.di.appConfigModule
import com.app.harigaji.core.auth.di.authModule
import com.app.harigaji.core.database.di.databaseModule
import com.app.harigaji.core.datastore.di.dataStoreModule
import com.app.harigaji.core.notification.di.notificationModule
import com.app.harigaji.core.uiconfig.di.uiConfigModule
import com.app.harigaji.core.user.di.userModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            listOf(
                platFormModule, appModule,
                chatModule,
                articleModule,
                authModule,notificationModule,
                dataStoreModule, databaseModule,
                notificationModule, appConfigModule, userModule,
                uiConfigModule
            )
        )
    }
}