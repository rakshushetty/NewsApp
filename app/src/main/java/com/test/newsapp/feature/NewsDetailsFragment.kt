package com.test.newsapp.feature


import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.test.newsapp.Article
import com.test.newsapp.R
import com.test.newsapp.utils.Utils

/**
 * A simple [Fragment] subclass.
 */
class NewsDetailsFragment : Fragment(R.layout.fragment_news_details) {

    companion object {
        fun newInstance(args: Bundle? = null) = NewsDetailsFragment().apply { arguments = args }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val article = arguments?.getParcelable<Article>(DATA)

            initView(view, article)
        }
    }

    private fun initView(view: View, article: Article?) {
        view.apply {
            findViewById<TextView>(R.id.tvDetailedTitle).text = article?.title
            findViewById<TextView>(R.id.tvDetailedDesc).text = article?.content
            Utils.setImageData(article?.urlToImage, findViewById(R.id.ivDetail))
        }
    }
}
