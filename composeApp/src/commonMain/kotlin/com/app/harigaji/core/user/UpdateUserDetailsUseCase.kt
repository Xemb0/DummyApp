package com.app.harigaji.core.user

import com.app.harigaji.core.datastore.DataStoreRepository
import kotlinx.coroutines.flow.firstOrNull

class UpdateUserDetailsUseCase(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke(
        name: String? = null,
        email: String? = null,
        phone: String? = null,
        token: String? = null,
        id: String? = null,
        profilePic: String? = null,
        baseUrl: String? = null
    ) {
        val currentDetails = repository.getUserDetails().firstOrNull() ?: UserDetails(
            name = "",
            email = "",
            phone = "",
            token = "",
            id = "",
            baseUrl = "",
        )
        println("_______________Current user details: $currentDetails")

        val updatedDetails = currentDetails.copy(
            name = name ?: currentDetails.name,
            email = email ?: currentDetails.email,
            phone = phone ?: currentDetails.phone,
            token = token ?: currentDetails.token,
            id = id ?: currentDetails.id,
            baseUrl = baseUrl ?: currentDetails.baseUrl
            // profilePic = profilePic ?: currentDetails.profilePic
        )
        println("________________Updated user details: $updatedDetails")
        repository.saveUserDetails(updatedDetails)
        println("_____________after Updated user details: $updatedDetails")
    }
}
