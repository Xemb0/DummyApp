package com.app.harigaji.core.appconfig.di

import com.app.harigaji.core.appconfig.AppConfigRepository
import com.app.harigaji.core.appconfig.AppConfigRepositoryImpl
import com.app.harigaji.core.auth.AuthRepository
import com.app.harigaji.core.auth.AuthRepositoryImpl
import com.app.harigaji.core.database.MyAppDataBase
import com.app.harigaji.core.datastore.DataStoreRepository
import com.app.harigaji.core.datastore.DataStoreRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appConfigModule = module {

    singleOf(::DataStoreRepositoryImpl).bind<DataStoreRepository>()
    singleOf(::AuthRepositoryImpl).bind<AuthRepository>()
    singleOf(::AppConfigRepositoryImpl).bind<AppConfigRepository>()

    single { get<MyAppDataBase>().appConfigDao() }

}