package com.app.harigaji.presentation.tabs.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.data.UserProgressDetail
import com.app.harigaji.presentation.ScreenHeader
import harigaji.composeapp.generated.resources.Res
import harigaji.composeapp.generated.resources.ic_curve_back
import harigaji.composeapp.generated.resources.ic_envelop
import org.jetbrains.compose.resources.painterResource
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun ForgetPassScreen(
    paddingValues: PaddingValues,
    userProgressDetail: UserProgressDetail?,
    onPrevious: () -> Unit = {},
    onResetClick: (String) -> Unit = {},
) {

    var initialEmail by remember { mutableStateOf(TextFieldValue(userProgressDetail?.email?:"")) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
      bottomBar = {
          Button(
              onClick = {
                  if(initialEmail.text.isNotEmpty()){
                      onResetClick(initialEmail.text)
                  }
              },
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(bottom = paddingValues.calculateBottomPadding())
                  .padding(horizontal = 16.dp)
                  .height(56.dp),
              colors = ButtonDefaults.buttonColors(
                  containerColor = MaterialTheme.colorScheme.secondary
              ),
              shape = CircleShape
          ) {
              Text(
                  text = "Reset Password",
                  fontSize = 16.sp,
                  fontWeight = FontWeight.Medium
              )
              Spacer(modifier = Modifier.width(8.dp))
              Icon(
                  imageVector = Icons.Default.ArrowForward,
                  contentDescription = null,
                  modifier = Modifier.size(20.dp)
              )
          }
      },
        topBar = {
            ScreenHeader(
                title = "Forget Password",
                onBackClick = onPrevious,
                paddingValues = paddingValues
            )
        },
    ) { pv ->
        Box(
            modifier = Modifier
                .padding(pv)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Title and subtitle
                Text(
                    text = "Forget Password",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Enter you email address and we will send you code",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(32.dp))
                CustomTextField(
                    modifier = Modifier,
                    trailingIcon = {

                    },
                    label = "Email Address",
                    leadingIconRes = Res.drawable.ic_envelop,
                    value = initialEmail,
                    onValueChange ={
                        initialEmail = it
                    },
                )
            }
    }
}
}