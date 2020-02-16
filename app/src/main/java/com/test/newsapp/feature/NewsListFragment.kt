package com.test.newsapp.feature


import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.test.newsapp.ApiResults
import com.test.newsapp.Article
import com.test.newsapp.MainActivity
import com.test.newsapp.R
import com.test.newsapp.callbacks.ItemClickListener

/**
 * Fragment to list all the articles, on tap of any article, it will navigate to webView to show in detail
 */
const val DATA = "data"
const val IS_SAVED_DATA = "is_saved_data"

class NewsListFragment : Fragment(R.layout.fragment_news_list), ItemClickListener<Article> {

    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var rvNews: RecyclerView
    private lateinit var rvAdapter: RvAdapter
    private lateinit var errorText: TextView
    private lateinit var viewFlipper: ViewFlipper
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private val dataList = ArrayList<Article>()

    companion object {
        fun newInstance(args: Bundle? = null) = NewsListFragment().apply { arguments = args }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvNews = view.findViewById(R.id.rvNews)
        errorText = view.findViewById(R.id.errorText)
        viewFlipper = view.findViewById(R.id.vfNews)
        swipeRefreshLayout = view.findViewById(R.id.srNews)

        rvAdapter = RvAdapter(dataList, this)
        rvNews.layoutManager = LinearLayoutManager(requireContext())
        rvNews.adapter = rvAdapter

        /*Observe liveDate to update screeen*/
        newsViewModel.newsData.observe(this) { apiResults ->
            when (apiResults) {
                is ApiResults.Success -> {
                    viewFlipper.displayedChild = 1
                    if (apiResults.data.articles != null) {
                        dataList.clear()
                        dataList.addAll(apiResults.data.articles!!)
                        rvAdapter.notifyDataSetChanged()
                    }
                }
                is ApiResults.Error -> {
                    viewFlipper.displayedChild = 0
                    errorText.text = getString(R.string.oops_something_went_wrong)
                    println(apiResults.error)
                }
            }
        }
        newsViewModel.getData()
        swipeRefreshLayout.setOnRefreshListener {
            newsViewModel.getData()
        }
    }

    /**
     * on Any article clicked, This will navigate to details screen
     *
     * @param t article data
     * @param pos position of data
     * @param tag tag
     */
    override fun onItemClicked(t: Article, pos: Int, tag: String?) {
        if (activity is MainActivity) {
            (activity as MainActivity).addFragment(
                NewsDetailsFragment.newInstance(Bundle().apply {
                    putParcelable(DATA, t)
                    putBoolean(IS_SAVED_DATA, true)
                }),
                NewsDetailsFragment::class.java.canonicalName,
                true
            )
        }
    }
}
