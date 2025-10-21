package com.app.harigaji.core.shared

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.harigaji.core.appconfig.AppConfigEntity
import com.app.harigaji.core.auth.AuthScreenState
import com.app.harigaji.core.datastore.DataStoreRepositoryImpl
import com.app.harigaji.core.notification.NotificationDetail
import com.app.harigaji.core.notification.NotificationRepository
import com.app.harigaji.core.notification.NotificationRepositoryImpl
import com.app.harigaji.data.UiEvent
import com.mmk.kmpnotifier.notification.NotifierManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SharedViewModel(private val notificationRepository: NotificationRepository) : ViewModel() {

    private val _appConfig = MutableStateFlow<AppConfigEntity?>(null)
    val appConfig: StateFlow<AppConfigEntity?> = _appConfig.asStateFlow()


    private val _currentTab = MutableStateFlow(0)
    val currentTab: StateFlow<Int> = _currentTab.asStateFlow()
    private val _state = MutableStateFlow(AuthScreenState())
    val state: StateFlow<AuthScreenState> = _state.asStateFlow()

    private val _uiEvent: Channel<UiEvent> = Channel()
    val uiEvent = _uiEvent.receiveAsFlow()

    private val _listNotification = MutableStateFlow<List<NotificationDetail>>(emptyList())
    val listNotification: StateFlow<List<NotificationDetail>> = _listNotification.asStateFlow()

    private val _unreadNotificationCount = MutableStateFlow(0)
    val unreadNotificationCount: StateFlow<Int> = _unreadNotificationCount.asStateFlow()


    fun onEvent(event: UiEvent) {
        when (event) {
            is UiEvent.Input.EnterPhone -> {
                _state.update { it.copy(phone = event.value) }
            }
            else-> {}

        }
    }

    fun setCurrentTab(index: Int) {
        _currentTab.value = index
    }

    init {
//        viewModelScope.launch {
//            val token = NotifierManager.getPushNotifier().getToken()
//            println("my fcm ${NotifierManager.getPushNotifier().getToken()}")
//            updateFcmToken(token)
//        }
//        viewModelScope.launch {
//            notificationRepository.getNotificationFlow().collectLatest {
//                _listNotification.value = it
//            }
//        }
//        viewModelScope.launch {
//            combine(
//                notificationRepository.getNotificationFlow(),
//                notificationRepository.getPreviousNotificationCount()
//            ) { notifications, previousCount ->
//                Pair(notifications, previousCount)
//            }.collect { (notifications, previousCount) ->
//                _listNotification.value = notifications
//                println(" notifications: $notifications")
//                _unreadNotificationCount.value = (notifications.size - previousCount).coerceAtLeast(0)
//                println(" _unreadNotificationCount: ${_unreadNotificationCount.value} notificationsSize: ${notifications.size} previousCount: $previousCount")
//            }
//        }
//
//        viewModelScope.launch {
//            notificationRepository.refreshNotification()
//        }
    }

    fun refreshNotification() {
        viewModelScope.launch {
            notificationRepository.refreshNotification()
        }
    }

    fun markAllNotificationsAsRead() {
        viewModelScope.launch {
            notificationRepository.updateNotificationCount(listNotification.value.size)
        }
    }
    fun updateFcmToken(fcmToken: String?) {

        viewModelScope.launch {
            fcmToken?.let {
                val result = notificationRepository.updateFcmToken(it)
            }
        }

    }

}


