package com.test.newsapp.feature


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.test.newsapp.Article
import com.test.newsapp.MainActivity
import com.test.newsapp.R
import com.test.newsapp.callbacks.ItemClickListener
import com.test.newsapp.utils.SQLiteManager

/**
 * A simple [Fragment] subclass.
 */
class SavedNewsFragment : Fragment(R.layout.fragment_news_list), ItemClickListener<Article> {

    private lateinit var rvNews: RecyclerView
    private lateinit var rvAdapter: RvAdapter
    private lateinit var errorText: TextView
    private lateinit var viewFlipper: ViewFlipper
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val dataList = ArrayList<Article>()

    companion object {
        fun newInstance(args: Bundle? = null) = SavedNewsFragment().apply { arguments = args }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvNews = view.findViewById(R.id.rvNews)
        errorText = view.findViewById(R.id.errorText)
        viewFlipper = view.findViewById(R.id.vfNews)

        rvAdapter = RvAdapter(dataList, this)
        rvNews.layoutManager = LinearLayoutManager(requireContext())
        rvNews.adapter = rvAdapter
        swipeRefreshLayout = view.findViewById(R.id.srNews)

        loadData(view.context)
        swipeRefreshLayout.setOnRefreshListener {
            loadData(requireContext())
        }
    }

    private fun loadData(context: Context) {
        errorText.text = getString(R.string.loading)
        viewFlipper.displayedChild = 0
        val articleList = SQLiteManager.newInstance(context).getAllData()
        if (articleList.isNotEmpty()) {
            viewFlipper.displayedChild = 1
            dataList.clear()
            dataList.addAll(articleList)
            rvAdapter.notifyDataSetChanged()
        } else {
            errorText.text = getString(R.string.saved_list_empty)
        }
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onItemClicked(t: Article, pos: Int, tag: String?) {
        if (activity is MainActivity) {
            (activity as MainActivity).addFragment(
                NewsDetailsFragment.newInstance(Bundle().apply {
                    putParcelable(DATA, t)
                    putBoolean(IS_SAVED_DATA, false)
                }),
                NewsDetailsFragment::class.java.canonicalName,
                true
            )
        }
    }
}
