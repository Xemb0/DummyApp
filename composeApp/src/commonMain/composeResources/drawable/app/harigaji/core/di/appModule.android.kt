package com.app.harigaji.core.di

import androidx.activity.ComponentActivity
import com.app.harigaji.core.database.DataBaseFactory
import com.app.harigaji.core.datastore.createDataStore
import com.app.harigaji.core.language.Localization
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platFormModule: Module
    get() = module {

        single<Localization> { Localization(androidContext()) }

        single<HttpClientEngine> { OkHttp.create() }

        single { createDataStore(androidApplication()) }

        single { DataBaseFactory(androidApplication()) }


    }

