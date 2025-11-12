package com.app.harigaji.artical.data

import com.app.harigaji.artical.domain.ArticleDetail

data class ArticleState(
    val listArticleDetail: List<ArticleDetail> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)