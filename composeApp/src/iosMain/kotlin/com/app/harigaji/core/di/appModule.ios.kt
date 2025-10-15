package com.app.harigaji.core.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import com.app.harigaji.core.database.DataBaseFactory
import com.app.harigaji.core.datastore.createDataStore
import com.app.harigaji.core.language.Localization
import org.koin.core.module.Module
import org.koin.dsl.module

actual val platFormModule: Module
    get() = module {

        single<HttpClientEngine> { Darwin.create() }
        single { createDataStore() }

        single<Localization> { Localization() }

        single { DataBaseFactory() }
    }