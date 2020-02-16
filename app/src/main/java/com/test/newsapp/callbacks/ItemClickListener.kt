package com.test.newsapp.callbacks

/**
 * Call back for item click in recyclerView
 */
interface ItemClickListener<T : Any> {
    /**
     * On Item clicked
     *
     * @param t item data
     * @param pos Position of item
     * @param tag Tag
     */
    fun onItemClicked(t: T, pos: Int, tag: String? = null)
}