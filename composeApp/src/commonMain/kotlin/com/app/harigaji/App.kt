package com.app.harigaji

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.app.harigaji.navigation.NavScreen
import com.app.harigaji.theme.LightColorTheme
import com.app.harigaji.theme.MyMaterialTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import harigaji.composeapp.generated.resources.Res

@Composable
@Preview
fun App() {
    MyMaterialTheme {
        NavScreen()
    }
}