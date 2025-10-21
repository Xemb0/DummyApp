package com.app.harigaji.core.user.di

import com.app.harigaji.core.database.MyAppDataBase
import com.app.harigaji.core.user.GetUserDetailsUseCase
import com.app.harigaji.core.user.UpdateUserDetailsUseCase
import com.app.harigaji.core.user.UserViewModel
import com.app.harigaji.user.data.UserProgressRepositoryImpl
import com.app.harigaji.user.domain.UpdateUserProgressDetailsUseCase
import com.app.harigaji.user.domain.UserRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val userModule = module {
    viewModelOf(::UserViewModel).bind()
    singleOf(::GetUserDetailsUseCase)
    singleOf(::UpdateUserDetailsUseCase)
    singleOf(::UpdateUserProgressDetailsUseCase)
    singleOf(::UserProgressRepositoryImpl).bind<UserRepository>()

    single { get<MyAppDataBase>().userProgressDao()  }
}