package com.app.harigaji

import android.app.Application
import com.app.harigaji.core.di.initKoin
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext

class MyApp : Application() {
        override fun onCreate() {
            super.onCreate()
            FirebaseApp.initializeApp(this)
            initKoin {
                androidContext(this@MyApp)
            }
        }
}