package com.app.harigaji.language

import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val languageModule = module {
    single<LanguageRepository> { LanguageRepositoryImpl() }
    viewModelOf(::LanguageViewModel)
}
