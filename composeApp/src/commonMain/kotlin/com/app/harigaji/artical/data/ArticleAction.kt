package com.app.harigaji.artical.data


sealed class ArticleAction {
    object LoadArticles : ArticleAction()
    object RefreshArticles : ArticleAction()
}
