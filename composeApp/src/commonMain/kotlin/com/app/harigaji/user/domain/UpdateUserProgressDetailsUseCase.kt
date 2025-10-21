package com.app.harigaji.user.domain
import com.app.harigaji.data.UserProgressDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

class UpdateUserProgressDetailsUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(
        status: String? = null,
        id: String? = null,
        balance: Double? = null,
        firstName: String? = null,
        lastName: String? = null,
        email: String? = null,
        token: String? = null,
        baseUrl: String? = null,
        phone: String? = null,
        language: String? = null,
        profilePic: String? = null,
    ) = withContext(Dispatchers.IO) {

        var currentUser = userRepository.getUserProgressDetails().first()

        if (currentUser == null) {
            val newUser = UserProgressDetail(
                email = email ?: "",
                token = token ?: "",
                baseUrl = baseUrl ?: "",
                firstName = firstName?:"",
                lastName = lastName?:"",
                phone = phone?:"",
                totalBalance = balance?:0.0,
                language = language?:"",
                profilePic =profilePic?:"",
            )
            userRepository.insertUserDetails(newUser)
            println("RadarFlow: Inserted new user: $newUser")

            currentUser = userRepository.getUserProgressDetails().first()
        }

        val updatedUser = currentUser?.copy(
            email = email ?: currentUser.email,
            token = token ?: currentUser.token,
            baseUrl = baseUrl ?: currentUser.baseUrl,
            firstName = firstName ?: currentUser.firstName,
            lastName = lastName ?: currentUser.lastName,
            phone = phone ?: currentUser.phone,
            totalBalance = balance ?: currentUser.totalBalance,
            language = language ?: currentUser.language,
            profilePic = profilePic ?: currentUser.profilePic,
        )

        userRepository.updateUserDetails(updatedUser)
        println("RadarFlow: Updated user: $updatedUser")
    }
}
