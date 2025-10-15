package com.app.harigaji.core.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.app.harigaji.core.appconfig.AppConfigRepository
import com.app.harigaji.core.appconfig.AppConfigRepositoryImpl
import com.app.harigaji.core.auth.AuthRepository
import com.app.harigaji.core.auth.AuthRepositoryImpl
import com.app.harigaji.core.database.DataBaseFactory
import com.app.harigaji.core.database.MyAppDataBase
import com.app.harigaji.core.datastore.DataStoreRepository
import com.app.harigaji.core.datastore.DataStoreRepositoryImpl
import com.app.harigaji.core.ktor.HttpClientFactory
import com.app.harigaji.core.ktor.KtorApiClient
import com.app.harigaji.core.shared.SharedViewModel
import com.app.harigaji.core.user.UpdateUserDetailsUseCase

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platFormModule : Module

val appModule = module {
    single { HttpClientFactory.buildHttpClient(get()) }



    single { Firebase.firestore }



    viewModelOf(::SharedViewModel).bind()

    single { KtorApiClient(get()) }


    singleOf(::UpdateUserDetailsUseCase)





}