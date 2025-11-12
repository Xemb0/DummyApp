package com.app.harigaji.artical.domain

interface ArticleRepository {

    suspend fun refreshArticles()
    suspend fun getArticles(): List<ArticleDetail>
    suspend fun getArticlesByCategory(category: String): List<ArticleDetail>
}