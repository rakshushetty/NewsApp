package com.test.newsapp

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NewsData(
    var articles: List<Article>? = null,
    var status: String? = null
) : Parcelable

@Parcelize
data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
) : Parcelable

@Parcelize
data class Source(
    val id: String? = null,
    val name: String
) : Parcelable