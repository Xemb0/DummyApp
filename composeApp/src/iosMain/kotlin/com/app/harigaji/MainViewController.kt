package com.app.harigaji

import androidx.compose.ui.window.ComposeUIViewController
import com.app.harigaji.core.di.initKoin

fun MainViewController(

) = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }