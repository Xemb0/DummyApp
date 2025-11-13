package com.app.harigaji.core


import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import platform.UIKit.UIScreen


@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenWidth(): Float = with(LocalDensity.current) { LocalWindowInfo.current.containerSize.width.toDp().toPx() }



@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenHeight(): Float = with(LocalDensity.current) { LocalWindowInfo.current.containerSize.height.toDp().toPx() }

fun Int.pxToPoint(): Float = this / UIScreen.mainScreen.scale.toFloat()
