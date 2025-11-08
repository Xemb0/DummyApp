package com.app.harigaji.core.uiconfig.di

import com.app.harigaji.core.uiconfig.UiConfigRepository
import com.app.harigaji.core.uiconfig.UiConfigRepositoryImpl
import com.app.harigaji.core.uiconfig.UiConfigViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val uiConfigModule = module {
    single<UiConfigRepository> { UiConfigRepositoryImpl(get()) }
    viewModelOf(::UiConfigViewModel)
}

