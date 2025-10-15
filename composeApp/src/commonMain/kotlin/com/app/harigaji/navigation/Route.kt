package com.app.harigaji.navigation
import kotlinx.serialization.Serializable

@Serializable
sealed class Route {
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
        data object GetStarted : Route()
        @Serializable
        data object Profile : Route()
}