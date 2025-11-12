package com.app.harigaji.artical.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.harigaji.artical.data.ArticleAction
import com.app.harigaji.artical.data.ArticleState
import com.app.harigaji.artical.domain.ArticleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ArticleViewModel(
    private val repository: ArticleRepository
) : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ArticleState())
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                loadArticles()
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Companion.WhileSubscribed(5_000L),
            initialValue = ArticleState()
        )

    fun onAction(action: ArticleAction) {
        when (action) {
            is ArticleAction.LoadArticles -> loadArticles()
            is ArticleAction.RefreshArticles -> refreshArticles()
        }
    }

    private fun loadArticles() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, error = null) }
                val articles = repository.getArticles()

                _state.update {
                    it.copy(
                        listArticleDetail = articles,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to load articles. Please try again."
                    )
                }
            }
        }
    }

    private fun refreshArticles() {
        viewModelScope.launch {
            try {
                // Set loading state (keep existing articles visible during refresh)
                _state.update { it.copy(isLoading = true, error = null) }
                // Get fresh articles from repository
                val articles = repository.getArticles()

                // Update state with new articles
                _state.update {
                    it.copy(
                        listArticleDetail = articles,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: Exception) {
                // Handle error
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Failed to refresh articles. Please try again."
                    )
                }
            }
        }
    }
}