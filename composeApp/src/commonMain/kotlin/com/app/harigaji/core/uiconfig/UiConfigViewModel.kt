package com.app.harigaji.core.uiconfig

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UiConfigViewModel(
    private val repository: UiConfigRepository
) : ViewModel() {
    
    private val _uiConfig = MutableStateFlow<UiConfigEntity?>(null)
    val uiConfig: StateFlow<UiConfigEntity?> = _uiConfig.asStateFlow()
    
    var isControlPanelVisible by mutableStateOf(false)
        private set
    
    init {
        loadConfig()
    }
    
    fun loadConfig() {
        viewModelScope.launch {
            val config = repository.getConfig()
            if (config.id == 0) {
                // First time, save default config with id=1
                val defaultConfig = UiConfigEntity(id = 1)
                repository.saveConfig(defaultConfig)
                _uiConfig.value = defaultConfig
            } else {
                _uiConfig.value = config
            }
        }
    }
    
    fun toggleControlPanel() {
        isControlPanelVisible = !isControlPanelVisible
    }
    
    fun updateConfig(config: UiConfigEntity) {
        viewModelScope.launch {
            repository.saveConfig(config)
            _uiConfig.value = config
        }
    }
    
    fun updateColors(config: UiConfigEntity) {
        viewModelScope.launch {
            repository.updateColors(config)
            val updatedConfig = repository.getConfig()
            _uiConfig.value = updatedConfig
        }
    }
    
    fun updatePadding(config: UiConfigEntity) {
        viewModelScope.launch {
            repository.updatePadding(config)
            val updatedConfig = repository.getConfig()
            _uiConfig.value = updatedConfig
        }
    }
    
    fun updateIconSizes(config: UiConfigEntity) {
        viewModelScope.launch {
            repository.updateIconSizes(config)
            val updatedConfig = repository.getConfig()
            _uiConfig.value = updatedConfig
        }
    }
    
    fun updateTextSizes(config: UiConfigEntity) {
        viewModelScope.launch {
            repository.updateTextSizes(config)
            val updatedConfig = repository.getConfig()
            _uiConfig.value = updatedConfig
        }
    }
}

