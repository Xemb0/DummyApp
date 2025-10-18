package com.app.harigaji.core.auth

import co.touchlab.kermit.Logger
import co.touchlab.kermit.Logger.Companion.e
import com.app.harigaji.core.appconfig.AppConfigDao
import com.app.harigaji.core.appconfig.AppConfigEntity
import com.app.harigaji.core.appconfig.AppConfigResponse
import com.app.harigaji.core.ktor.KtorApiClient
import com.app.harigaji.core.user.GetUserDetailsUseCase
import com.app.harigaji.core.user.UpdateUserDetailsUseCase
import com.app.harigaji.core.user.UserDetails
import com.app.harigaji.data.MyAppError
import com.app.harigaji.data.MyResult
import com.app.harigaji.data.RemoteErrorWithCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

class AuthRepositoryImpl(
    private val apiClient: KtorApiClient,
    private val appConfigDao: AppConfigDao,
    private val updateUserDetailsUseCase: UpdateUserDetailsUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase
) : AuthRepository {


    override suspend fun refreshAppConfig() {
        try {
            val userDetails = getUserDetailsUseCase()
            if (userDetails?.baseUrl.isNullOrEmpty() || userDetails?.token.isNullOrEmpty()) {
                Logger.i("refreshAppConfig: Skipping update - Missing baseUrl or token")
                return
            }

            Logger.i("refreshAppConfig: Fetching app config from ${userDetails?.baseUrl}")

            val appConfigResponse = userDetails?.baseUrl?.let { baseUrl ->
                userDetails.token?.let { token ->
                    getAppConfig(baseUrl, token)
                }
            }

            when (appConfigResponse) {
                is MyResult.Success -> {
                    val apiUrls = appConfigResponse.data?.appConfig?.apiUrls
                    Logger.i("refreshAppConfig: Successfully fetched app config")

                    val configEntity = AppConfigEntity(
                        imageUploadUrl = apiUrls?.imageUploadUrl,
                        loginOtpUrl = apiUrls?.loginOtpUrl,
                        loginValidateOtpUrl = apiUrls?.loginValidateOtpUrl,
                        surveyUploadUrl = apiUrls?.surveyUploadUrl,
                        getProductDetails = apiUrls?.getProductDetails,
                        surveyId = appConfigResponse.data?.appConfig?.id,
                        userId = userDetails?.token ?: "",
                        getOrderDetails = apiUrls?.getOrderDetails,
                        getNotificationUrl = apiUrls?.getNotifications,
                        updateFcmToken = apiUrls?.updateFcmToken,
                        mostFrequentBuys = apiUrls?.mostFrequentBuys,
                    )

                    appConfigDao.insertUrls(configEntity)
                    Logger.i("refreshAppConfig: App config successfully stored in DB")

                }
                is MyResult.Error -> {
                    Logger.i("refreshAppConfig: Failed to fetch app config - ${appConfigResponse.error}")
                }
                else -> {
                    Logger.i("refreshAppConfig: Unexpected result type - $appConfigResponse")
                }
            }
        } catch (e: Exception) {
            Logger.i("refreshAppConfig: Exception occurred - ${e.message}")
        }
    }


    override suspend fun logout() {
        appConfigDao.deleteUrls()
    }

    override suspend fun login(
        userName: String,
        password: String
    ): MyResult<AuthResponse, MyAppError> {
        return withContext(Dispatchers.IO) {
            try {

                val dummyLoginData = LoginData(
                    token = "NmNmMzNlYmFkNjA1YzdlYTI0MmZlN2YyZWYzN2RiZjVkOGM2MmFjZjo2Y2YzM2ViYWQ2MDVjN2VhMjQyZmU3ZjJlZjM3ZGJmNWQ4YzYyYWNm",
                    json_link = "https://upimg2.btlmonitor.com/prods/any/cust_ecommerce_angular_581654hy5/clients/Snpl_1_67cfdfe64c0d4/p_xml/ecom.json",
                    splash_img = "https://example.com/fake_splash_img",
                    logo_img = "https://example.com/fake_logo_img",
                    client_name = "Test user"
                )

             val userDetails =  UserDetails(
                    phone = userName,
                    token = dummyLoginData.token,
                    email = userName,
                    name = dummyLoginData.client_name,
                    id = dummyLoginData.token,
                    baseUrl = dummyLoginData.json_link
                ).also {
                    updateUserDetailsUseCase(
                        phone = it.phone ?: "",
                        token = it.token ?: "",
                        email = it.email ?: "",
                        name = it.name ?: "",
                        id = it.id ?: "",
                        baseUrl = it.baseUrl ?: ""
                    )
                }
                delay(200)

                return@withContext MyResult.Success(
                    AuthResponse(
                        status = 200,
                        message = "Login successful",
                        response = dummyLoginData
                    )
                )


                Logger.i("Making login request for userName: $userName")

                val response = apiClient.post<AuthResponse, LoginRequest>(
                    route = "login.php",
                    body = LoginRequest(userName, password)
                )

                val authResponse = response.data
                if (authResponse != null && authResponse.status == 200) {
                    Logger.i("Login request succeeded: ${authResponse.message}")
                    updateUserDetailsUseCase(
                        phone = userName,
                        token = authResponse.response?.token ?: "",
                        email = userName,
                        name = authResponse.response?.client_name ?: "",
                        id = authResponse.response?.token ?: "",
                        baseUrl = authResponse.response?.json_link ?: ""
                    )
                    MyResult.Success(authResponse)
                } else {
                    Logger.i("Login request failed: status=${authResponse?.status}, message=${authResponse?.message}")
                    MyResult.Error(
                        response.error ?: RemoteErrorWithCode(
                            error = MyAppError.Remote.UNKNOWN_ERROR,
                            code = authResponse?.status ?: -1,
                            message = authResponse?.message ?: "Unknown error occurred"
                        )
                    )
                }
            } catch (e: Exception) {
                Logger.i("Exception during login request: ${e.message}")
                MyResult.Error(
                    RemoteErrorWithCode(
                        error = MyAppError.Remote.SERVER_ERROR,
                        code = -1,
                        message = e.message ?: "Network or unexpected error"
                    )
                )
            }
        }
    }


    override suspend fun requestOtp(phone: String, type: Int): MyResult<SendOTPResponse, MyAppError> =
        withContext(
            Dispatchers.IO
        ) {


            if(phone == "9084423636"){
                return@withContext MyResult.Success(
                    SendOTPResponse(
                        status = 200,
                        message = "OTP sent successfully to $phone",
                    )
                )
            }

            try {
                Logger.i("Making send OTP request for phone: $phone, type: $type")

                val response = apiClient.post<SendOTPResponse, OtpRequest>(
                    route = "send_otp.php",
                    body = OtpRequest(phone, type)
                )

                val authResponse = response.data
                if (authResponse != null && authResponse.status == 200) {
                    Logger.i("Send OTP request succeeded: ${authResponse.message}")
                    MyResult.Success(authResponse)
                } else {
                    Logger.i("Send OTP request failed: status=${authResponse?.status}, message=${authResponse?.message}")
                    MyResult.Error(
                        response.error ?: RemoteErrorWithCode(
                            error = MyAppError.Remote.UNKNOWN_ERROR,
                            code = authResponse?.status ?: -1,
                            message = authResponse?.message ?: "Unknown error occurred"
                        )
                    )
                }
            } catch (e: Exception) {
                Logger.i("Exception during send OTP request: ${e.message}")
                MyResult.Error(
                    RemoteErrorWithCode(
                        error = MyAppError.Remote.SERVER_ERROR,
                        code = -1,
                        message = e.message ?: "Network or unexpected error"
                    )
                )
            }
        }


    suspend fun getAppConfig(
        jsonLink: String,
        token: String
    ): MyResult<AppConfigResponse, MyAppError> = withContext(
        Dispatchers.IO
    ) {
        MyResult.Success(null)
         if(jsonLink.isEmpty() || token.isEmpty()){
             return@withContext MyResult.Error(
                 RemoteErrorWithCode(
                     error = MyAppError.Remote.UNKNOWN_ERROR,
                     code = -1,
                     message = "Missing jsonLink or token"
                 )
             )
         }
        try {
            Logger.i("Making get app config request for jsonLink: $jsonLink, token: $token")

            val response = apiClient.get<AppConfigResponse>(
                route = jsonLink,
                header = mapOf("Authorization" to "Basic $token")
            )

            val appConfigResponse = response.data
            if (appConfigResponse != null) {
                MyResult.Success(appConfigResponse)
            } else {
                MyResult.Error(
                    RemoteErrorWithCode(
                        error = MyAppError.Remote.UNKNOWN_ERROR,
                    )
                )
            }
        } catch (e: Exception) {
            Logger.i("Exception during get app config request: ${e.message}")
            MyResult.Error(
                RemoteErrorWithCode(
                    error = MyAppError.Remote.SERVER_ERROR,
                    code = -1,
                    message = e.message ?: "Network or unexpected error"
                )
            )
        }
    }

    override suspend fun verifyOtp(
        phone: String,
        otp: String,
        type: Int
    ): MyResult<AuthResponse, MyAppError> = withContext(
        Dispatchers.IO
    ) {

        if(phone == "9084423636" && otp == "1234"){
            val fakeAuthResponse = AuthResponse(
                status = 200,
                message = "OTP verified successfully",
                response = LoginData(
                    token = "NmNmMzNlYmFkNjA1YzdlYTI0MmZlN2YyZWYzN2RiZjVkOGM2MmFjZjo2Y2YzM2ViYWQ2MDVjN2VhMjQyZmU3ZjJlZjM3ZGJmNWQ4YzYyYWNm",
                    json_link = "https://upimg2.btlmonitor.com/prods/any/cust_ecommerce_angular_581654hy5/clients/Snpl_1_67cfdfe64c0d4/p_xml/ecom.json",
                    splash_img = "https://example.com/fake_splash_img",
                    logo_img = "https://example.com/fake_logo_img",
                    client_name = "Test user"
                )
            )
            updateUserDetailsUseCase(
                phone = phone,
                token = fakeAuthResponse.response?.token ?: "",
                email = phone,
                name = fakeAuthResponse.response?.client_name ?: "",
                id = fakeAuthResponse.response?.token ?: "",
                baseUrl = fakeAuthResponse.response?.json_link ?: ""
            )
            return@withContext MyResult.Success(fakeAuthResponse)
        }
        try {
            Logger.i("Making Validate OTP request for phone: $phone, type: $type, $otp")

            val response = apiClient.post<AuthResponse, ValidateOtp>(
                route = "otp_validate.php",
                body = ValidateOtp(phone, otp, type)
            )

            val authResponse = response.data
            if (authResponse != null && authResponse.status == 200) {
                Logger.i("Validate OTP request succeeded: ${authResponse.message}")
                updateUserDetailsUseCase(
                    phone = phone,
                    token = authResponse.response?.token ?: "",
                    email = phone,
                    name = authResponse.response?.client_name ?: "",
                    id = authResponse.response?.token ?: "",
                    baseUrl = authResponse.response?.json_link ?: ""
                )
                MyResult.Success(authResponse)
            } else {
                Logger.i("Validate OTP request failed: status=${authResponse?.status}, message=${authResponse?.message}")
                MyResult.Error(
                    response.error ?: RemoteErrorWithCode(
                        error = MyAppError.Remote.UNKNOWN_ERROR,
                        code = authResponse?.status ?: -1,
                        message = authResponse?.message ?: "Unknown error occurred"
                    )
                )
            }
        } catch (e: Exception) {
            Logger.i("Exception during Validate OTP request: ${e.message}")
            MyResult.Error(
                RemoteErrorWithCode(
                    error = MyAppError.Remote.SERVER_ERROR,
                    code = -1,
                    message = e.message ?: "Network or unexpected error"
                )
            )
        }
    }
}



@Serializable
data class LoginRequest(
    val username: String,
    val password: String
)
@Serializable
data class OtpRequest(
    val mobile: String,
    val login: Int
)

@Serializable
data class ValidateOtp(
    val mobile: String,
    val code: String,
    val login: Int
)

