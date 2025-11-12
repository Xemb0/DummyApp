package com.app.harigaji.artical.di

import com.app.harigaji.artical.data.ArticleRepositoryImpl
import com.app.harigaji.artical.presentation.ArticleViewModel
import com.app.harigaji.artical.domain.ArticleRepository
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val articleModule  = module {
    viewModelOf(::ArticleViewModel)
    singleOf(::ArticleRepositoryImpl) bind ArticleRepository::class

}