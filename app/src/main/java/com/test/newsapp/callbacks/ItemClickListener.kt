package com.test.newsapp.callbacks

interface ItemClickListener<T : Any> {
    fun onItemClicked(t: T, pos: Int, tag: String? = null)
}