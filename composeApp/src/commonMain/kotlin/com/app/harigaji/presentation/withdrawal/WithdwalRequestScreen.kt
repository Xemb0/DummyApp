package com.app.harigaji.presentation.withdrawal


import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.harigaji.data.UserProgressDetail
import com.app.harigaji.presentation.MyTopBar
import com.app.harigaji.presentation.tabs.profile.CustomTextField
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun WithdrawalRequestScreen(
    paddingValues: PaddingValues,
    userProgressDetail: UserProgressDetail?,
    onPrevious: () -> Unit = {},
    onNext: () -> Unit = {}
) {


    val listBankType = listOf(
        "Select Bank",
        "Access Bank",
        "Citibank",
        "Diamond Bank",
        "Ecobank Nigeria",
        "Fidelity Bank Nigeria",
        "First Bank of Nigeria",
        "First City Monument Bank",
        "Guaranty Trust Bank",
        "Heritage Bank Plc",
        "Keystone Bank Limited",
        "Providus Bank Plc",
        "Polaris Bank",
        "Stanbic IBTC Bank Nigeria Limited",
        "Standard Chartered Bank Nigeria Ltd",
        "Sterling Bank Plc",
        "Suntrust Bank Nigeria Limited",
        "Union Bank of Nigeria Plc",
        "United Bank for Africa Plc",
        "Unity Bank Plc",
        "Wema Bank Plc",
        "Zenith Bank Plc"
    )

    var amount by remember { mutableStateOf(TextFieldValue( "")) }
    var bankName by remember { mutableStateOf("Select Bank") }
    var bankAccountName by remember { mutableStateOf(TextFieldValue("")) }
    var bankAccountNumber by remember { mutableStateOf(TextFieldValue("")) }



    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            Button(
                onClick = {
                    onNext()
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
                    text = "Next",
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
            MyTopBar(
                title = "Withdrawal Request",
                onLeadingClick = onPrevious,
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
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {


                    // First Name Field
                    CustomTextField(
                        label = "Amount Max: ${userProgressDetail?.totalBalance}",
                        value = amount,
                        onValueChange = { amount = it },
                        textColor = MaterialTheme.colorScheme.onSurface
                    )


                    CustomDropDownBox(
                        dropDownList = listBankType,
                        label = "Bank Type",
                        errorString = "Bank name is required",
                        selectedReason = bankName,
                        reasonError = false,
                        isEnable = true,
                        color = MaterialTheme.colorScheme.surface,
                        onReasonSelected = {
                            bankName = it
                        }
                    )

                    // Email Field
                    CustomTextField(
                        label = "Bank Account Name",
                        value = bankAccountName,
                        onValueChange = { bankAccountName = it },
                        textColor = MaterialTheme.colorScheme.onSurface
                    )

                    CustomTextField(
                        label = "Bank Account Number",
                        value = bankAccountNumber,
                        onValueChange = { bankAccountNumber = it },
                        textColor = MaterialTheme.colorScheme.onSurface
                    )



                    Spacer(modifier = Modifier.height(124.dp))
                }
            }
        }
    }
}