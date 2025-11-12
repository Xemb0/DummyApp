package com.app.harigaji.about

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val aboutModule = module {
    single<AboutRepository> { AboutRepositoryImpl() }
    viewModelOf(::AboutViewModel)
}
