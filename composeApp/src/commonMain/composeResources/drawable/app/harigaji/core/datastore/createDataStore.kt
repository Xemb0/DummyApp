package com.app.harigaji.core.datastore
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual fun createDataStore(context: Any?): DataStore<Preferences> {
    require(
        value = context is Context,
        lazyMessage = { "Context object is required." }
    )
    return MyAppDataStore.getDataStore(
        producePath = {
            context.filesDir
                .resolve(DATA_STORE_FILE_NAME)
                .absolutePath
        }
    )
}