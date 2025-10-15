package com.app.harigaji.core.user

import com.app.harigaji.core.datastore.DataStoreRepository
import kotlinx.coroutines.flow.firstOrNull

class GetUserDetailsUseCase(
    private val repository: DataStoreRepository
) {
    suspend operator fun invoke() = repository.getUserDetails().firstOrNull()
}
