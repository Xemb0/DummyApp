package com.app.harigaji.core.datastore.di

import com.app.harigaji.core.datastore.DataStoreRepository
import com.app.harigaji.core.datastore.DataStoreRepositoryImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataStoreModule = module {
    singleOf(::DataStoreRepositoryImpl).bind<DataStoreRepository>()
}