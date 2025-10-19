package com.app.harigaji.core.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import com.app.harigaji.core.datastore.DataStoreRepository
import com.app.harigaji.core.language.Language
import com.app.harigaji.core.language.Localization
import com.app.harigaji.data.UserProgressDetail


data class UserUiState(
    val name: String="",
    val email: String="",
    val phone: String="",
    val profilePic: String? = null,
    val language: String = Language.English.iso
)

class UserViewModel (
    private val userRepository: DataStoreRepository,
    private val localization: Localization,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> get() = _uiState

    private val _currentLang = MutableStateFlow("")
    val currentLang: StateFlow<String?> get() = _currentLang

    private val _userProgressDetail = MutableStateFlow<UserProgressDetail?>(null)
    val userProgressDetail: StateFlow<UserProgressDetail?> get() = _userProgressDetail

    val userDetails: StateFlow<UserDetails> = userRepository.getUserDetails()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = UserDetails("", "", "", "", "")
        )

    private val _isUserLoggedIn  = MutableStateFlow<Boolean?>(null)
    val isUserLoggedIn: StateFlow<Boolean?> = _isUserLoggedIn.asStateFlow()



    init {
        viewModelScope.launch {
            userRepository.getCurrentLanguage().collectLatest { currLang->
                println("Current Language viewmodel: $currLang")
                _currentLang.value = currLang
            }
        }
        viewModelScope.launch {
        userRepository.getUserDetails().collectLatest { user ->
            _isUserLoggedIn.value = user.token?.isNotEmpty() ?: false
            _userProgressDetail.value = _userProgressDetail.value?.copy(
                name = user.name.orEmpty(),
                email = user.email.orEmpty(),
                phone = user.phone.orEmpty(),
                profilePic = user.profilePic.orEmpty(),
                token = user.token.orEmpty(),
                id = user.id.orEmpty(),
                baseUrl = user.baseUrl.orEmpty(),
                language = user.language.orEmpty(),
            )
        }

    }

    }

    val profilePic: StateFlow<String> = userRepository.getProfilePicFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = ""
        )



    fun setCurrentLanguage(language: String) {
        viewModelScope.launch {
            userRepository.saveCurrentLanguage(language)
            localization.applyLanguage(language)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }


    fun saveUserDetails(userDetails: UserDetails) {
        viewModelScope.launch {
            userRepository.saveUserDetails(userDetails)
        }
    }


    fun saveProfilePic(profilePic: String) {
        viewModelScope.launch {
            userRepository.saveProfilePic(profilePic)
        }
    }


    fun saveUserName(userName: String) {
        viewModelScope.launch {
            userRepository.saveUserName(userName)
        }
    }

    fun saveUserEmail(userEmail: String) {
        viewModelScope.launch {
            userRepository.saveUserEmail(userEmail)
        }
    }

    fun saveUserPhone(userPhone: String) {
        viewModelScope.launch {
            userRepository.saveUserPhone(userPhone)
        }
    }
}