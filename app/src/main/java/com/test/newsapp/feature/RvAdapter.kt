package com.test.newsapp.feature

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.test.newsapp.Article
import com.test.newsapp.R
import com.test.newsapp.callbacks.ItemClickListener
import com.test.newsapp.utils.Utils


/**
 * Adapter for listing all articles
 */
class RvAdapter(
    private val dataList: List<Article>,
    private val itemClickListener: ItemClickListener<Article>
) : RecyclerView.Adapter<NewsItemViewHolder>() {

    /**
     * Creates new view instance for viewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_new_view_holder, parent, false)
        return NewsItemViewHolder(view)
    }

    /**
     * Updates or adds new data to viewHolder
     */
    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        holder.bindData(dataList[position], itemClickListener)
    }

    /**
     * Returns total article count
     */
    override fun getItemCount(): Int {
        return dataList.size
    }
}


/**
 * ViewHolder class for showing article list
 */
class NewsItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    /**
     * Binds data of article
     */
    fun bindData(data: Article, itemClickListener: ItemClickListener<Article>) {

        itemView.apply {
            val imageView = findViewById<ImageView>(R.id.ivNews)
            imageView.setImageResource(R.color.imageBackGround)
            /*Updates the image of news*/
            Utils.setImageData(data.urlToImage, imageView)
            findViewById<TextView>(R.id.tvTitle).text = data.title
            findViewById<TextView>(R.id.tvDesc).text = data.description
            itemView.setOnClickListener {
                itemClickListener.onItemClicked(data, adapterPosition)
            }
        }
    }
}