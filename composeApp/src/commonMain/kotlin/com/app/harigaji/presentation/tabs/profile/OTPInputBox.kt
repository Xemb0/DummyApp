package com.app.harigaji.presentation.tabs.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OTPInputBox(
    otp: String,
    onOtpChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    otpLength: Int = 6,
    otpBoxSize: Dp = 58.dp,
    otpBoxSpacing: Dp = 6.dp,
    otpBoxCornerRadius: Dp = 16.dp,
    otpBoxBorderWidth: Dp = 2.dp,
    otpBoxBackgroundColor: Color = MaterialTheme.colorScheme.surface,
    otpBoxBorderColor: Color = Color.Gray.copy(alpha = 0.8f),
    otpTextColor: Color = Color.Black,
    otpTextSize: TextUnit = 24.sp,
    cursorColor: Color = Color.Blue,
    fontFamily: FontFamily? = null,
    enabled: Boolean = true
) {
    BasicTextField(
        value = otp,
        cursorBrush = Brush.sweepGradient(listOf(cursorColor, cursorColor)),
        onValueChange = { otpValue ->
            val filteredInput = otpValue.filter { it.isDigit() }
            if (filteredInput.length <= otpLength) {
                onOtpChange(filteredInput)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        enabled = enabled,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            repeat(otpLength) { index ->
                val number = otp.getOrNull(index)?.toString() ?: ""
                val isFilled = index < otp.length

                val currentBorderColor =
                    if (isFilled) otpBoxBorderColor else otpBoxBorderColor.copy(alpha = 0.2f)
                val currentBackgroundColor =
                    if (isFilled) otpBoxBackgroundColor else otpBoxBackgroundColor.copy(alpha = 0.4f)
                val currentTextColor =
                    if (isFilled) otpTextColor else otpTextColor.copy(alpha = 0.4f)

                Box(
                    modifier = Modifier
                        .padding(horizontal = otpBoxSpacing)
                        .size(otpBoxSize)
                        .clip(RoundedCornerShape(otpBoxCornerRadius))
                        .background(currentBackgroundColor)
                        .border(
                            otpBoxBorderWidth,
                            currentBorderColor,
                            RoundedCornerShape(otpBoxCornerRadius)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = number,
                        fontSize = otpTextSize,
                        fontFamily = fontFamily,
                        color = currentTextColor,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
@Composable
fun OTPSection(
    otp: String,
    onOtpChange: (String) -> Unit,
    otpTimeout: Int,
    onResendOtp: () -> Unit,
    onNotMyNumberClick: () -> Unit,
    resetOtpTimeout: () -> Unit,
    isError: String? = null,
    modifier: Modifier = Modifier,
    // OTP Box customization
    otpLength: Int = 4,
    otpBoxSize: Dp = 58.dp,
    otpBoxSpacing: Dp = 6.dp,
    otpBoxCornerRadius: Dp = 16.dp,
    otpBoxBackgroundColor: Color = Color(0xFFE8F5E9).copy(alpha = 0.5f),
    otpBoxBorderColor: Color = Color.Gray.copy(alpha = 0.1f),
    otpTextColor: Color = Color.Black,
    otpTextSize: TextUnit = 24.sp,
    cursorColor: Color = Color.Blue,
    // Text customization
    didntReceiveText: String = "Didn't receive Code?",
    resendOtpText: String = "Resend Code",
    textColor: Color = Color.Black,
    timerColor: Color = Color.Gray.copy(alpha = 0.8f),
    resendColor: Color = Color.Green,
    errorColor: Color = Color.Red,
    fontSize: TextUnit = 14.sp,
    fontFamily: FontFamily? = null
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // OTP Input
        OTPInputBox(
            otp = otp,
            onOtpChange = onOtpChange,
            otpLength = otpLength,
            otpBoxSize = otpBoxSize,
            otpBoxSpacing = otpBoxSpacing,
            otpBoxCornerRadius = otpBoxCornerRadius,
            otpBoxBackgroundColor = otpBoxBackgroundColor,
            otpBoxBorderColor = otpBoxBorderColor,
            otpTextColor = otpTextColor,
            otpTextSize = otpTextSize,
            cursorColor = cursorColor,
            fontFamily = fontFamily
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Resend OTP Section
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 36.dp)
        ) {
            if (otpTimeout != 0) {
                Text(
                    text = "00:${if (otpTimeout < 10) "0$otpTimeout" else otpTimeout}",
                    color = timerColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize,
                    fontFamily = fontFamily
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = didntReceiveText,
                    color = textColor.copy(alpha = .5f),
                    modifier = Modifier.clickable { onNotMyNumberClick() },
                    fontSize = fontSize,
                    fontWeight = FontWeight.Bold,
                    fontFamily = fontFamily
                )
                Spacer( modifier = Modifier.width(8.dp))
                Text(
                    text = resendOtpText,
                    color = if(otpTimeout == 0) resendColor.copy(alpha = .4f) else resendColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = fontSize,
                    fontFamily = fontFamily,
                    modifier = Modifier.clickable {
                        if(otpTimeout == 0){
                        onResendOtp()
                        resetOtpTimeout()
                        }
                    }
                )
            }
        }

        // Error Message
        Spacer(modifier = Modifier.height(8.dp))

        if (isError != null) {
            Text(
                text = isError,
                fontSize = 12.sp,
                fontFamily = fontFamily,
                color = errorColor,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        } else {
            Text(
                text = "",
                fontSize = 16.sp,
                fontFamily = fontFamily,
                color = errorColor,
                fontWeight = FontWeight.Bold
            )
        }
    }
}