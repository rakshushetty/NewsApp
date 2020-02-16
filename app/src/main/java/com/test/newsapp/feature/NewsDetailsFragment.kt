package com.test.newsapp.feature


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.test.newsapp.Article
import com.test.newsapp.R
import com.test.newsapp.utils.SQLiteManager

/**
 * A simple [Fragment] subclass.
 */
class NewsDetailsFragment : Fragment(R.layout.fragment_news_details) {

    private var isSaveButtonEnabled = false

    companion object {
        fun newInstance(args: Bundle? = null) = NewsDetailsFragment().apply { arguments = args }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val article = arguments?.getParcelable<Article>(DATA)
            isSaveButtonEnabled = arguments?.getBoolean(IS_SAVED_DATA, false) ?: false
            if (article != null) {
                initView(view, article)
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initView(view: View, article: Article) {
        view.apply {
            val webView = findViewById<WebView>(R.id.wvNews)
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    findViewById<ProgressBar>(R.id.pbLoadingDetails).visibility = View.GONE
                }
            }
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(article.url)
            val btn = findViewById<Button>(R.id.btnSave)
            btn.text =
                if (isSaveButtonEnabled) getString(R.string.save) else getString(R.string.delete)
            btn.setOnClickListener {
                val db = SQLiteManager.newInstance(it.context)
                performDbOperation(db, article)
            }
        }
    }

    private fun View.performDbOperation(db: SQLiteManager, article: Article) {
        if (isSaveButtonEnabled) {
            val status = db.addToDb(article)
            Toast.makeText(
                requireContext(),
                if (status >= 0) context.getString(R.string.successfully_stored) else
                    context.getString(R.string.already_stored),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val status = db.deleteItem(article)
            Toast.makeText(
                requireContext(),
                if (status >= 0) context.getString(R.string.deleted) else context.getString(R.string.something_wrong),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
