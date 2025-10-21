package com.app.harigaji.navigation
import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

        companion object

        @Serializable
        data object SendOTP : Route()
        @Serializable
        data class OfferPage(val listOfProducts: List<String>? = null) : Route()
        @Serializable
        data class EnterOTP(val mobile: String) : Route()
        @Serializable
        data object ScreenDemo : Route()
        @Serializable
        data class SubmitOTP(val mobile: String) : Route()
        @Serializable
        data object Home : Route()
        @Serializable
        data object HolderScreen : Route()
        @Serializable
        data object HomeTab : Route()
        @Serializable
        data object WishList : Route()
        @Serializable
        data object Notification : Route()
        @Serializable
        data object CartTab : Route()
        @Serializable
        data class CategoryWiseItem(val categoryName: String) : Route()
        @Serializable
        data class ProductDetail(val productId: String) : Route()
        @Serializable
        data object LoyaltyTab : Route()
        @Serializable
        data object OrderSuccess : Route()
        @Serializable
        data object CheckOutScreen : Route()
        @Serializable
        data object MyOrderTab : Route()
        @Serializable
        data object NavGraph : Route()
        @Serializable
        data object Splash : Route()

        @Serializable
        data object Login : Route()
        @Serializable
        data object Settings : Route()

        @Serializable
        data class ClockIn(val clockInTime:Long,val isClockedIn: Boolean) : Route()
        @Serializable
        data object GetStarted : Route()
        @Serializable
        data object ProfileEdit : Route()


        @Serializable
        data object ForgetPassword : Route()
        @Serializable
        data object WithdrawalRequest : Route()

        @Serializable
        data object PrivacyPolicy : Route()

        @Serializable
        data object TermsAndConditions : Route()

        @Serializable
        data object LanguageSelection : Route()

        @Serializable
        data object Article : Route()

        @Serializable
        data class Chat(val id: Int) : Route()

        @Serializable
        data object ChangePassword : Route()

        @Serializable
        data class Verification(val emailID: String?=null,val label:String,val buttonText:String) : Route()
}