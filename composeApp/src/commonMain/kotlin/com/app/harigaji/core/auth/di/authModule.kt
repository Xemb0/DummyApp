package com.app.harigaji.core.auth.di

import com.app.harigaji.core.auth.AuthViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule = module {
    viewModelOf(::AuthViewModel).bind()
}