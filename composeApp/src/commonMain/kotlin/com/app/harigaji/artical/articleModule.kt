package com.app.harigaji.artical

import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val articleModule  = module {
    viewModelOf(::ArticlesViewModel)
}