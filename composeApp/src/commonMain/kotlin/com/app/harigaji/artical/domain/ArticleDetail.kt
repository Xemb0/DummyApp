package com.app.harigaji.artical.domain

data class ArticleDetail(
    val id: Int,
    val title: String,
    val description: String,
    val date: String,
    val imageUrl: String?=null,
    val category: String, // e.g. "Trending", "Recent", "Saved"
    val readTime: String = "5 min read",
    val author: String = "HariGaji Team"
)