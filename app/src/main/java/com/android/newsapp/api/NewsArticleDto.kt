package com.android.newsapp.api

/**
 * DTO - Data Transfer Object
 * */
data class NewsArticleDto(
    val title: String?,
    val url: String,
    val urlToImage: String?
)