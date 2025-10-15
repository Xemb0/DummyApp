package com.app.harigaji.core.user.di

import com.app.harigaji.core.user.UserViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val userModule = module {
    viewModelOf(::UserViewModel).bind()
}