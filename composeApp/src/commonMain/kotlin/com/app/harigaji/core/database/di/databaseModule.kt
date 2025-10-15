package com.app.harigaji.core.database.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.app.harigaji.core.database.DataBaseFactory
import org.koin.dsl.module

val databaseModule = module {
    single {
        get<DataBaseFactory>().createDatabase()
            .setDriver(BundledSQLiteDriver())
            .fallbackToDestructiveMigration(
                dropAllTables = true
            )
            .build()
    }
}