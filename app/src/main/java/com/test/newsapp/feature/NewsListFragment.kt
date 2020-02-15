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
import com.test.newsapp.ApiResults
import com.test.newsapp.Article
import com.test.newsapp.MainActivity
import com.test.newsapp.R
import com.test.newsapp.callbacks.ItemClickListener

/**
 * A simple [Fragment] subclass.
 */
const val DATA = "data"

class NewsListFragment : Fragment(R.layout.fragment_news_list), ItemClickListener<Article> {

    private val newsViewModel: NewsViewModel by viewModels()
    private lateinit var rvNews: RecyclerView
    private lateinit var rvAdapter: RvAdapter
    private lateinit var errorText: TextView
    private lateinit var viewFlipper: ViewFlipper
    private val dataList = ArrayList<Article>()

    companion object {
        fun newInstance(args: Bundle? = null) = NewsListFragment().apply { arguments = args }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvNews = view.findViewById(R.id.rvNews)
        errorText = view.findViewById(R.id.errorText)
        viewFlipper = view.findViewById(R.id.vfNews)

        rvAdapter = RvAdapter(dataList, this)
        rvNews.layoutManager = LinearLayoutManager(requireContext())
        rvNews.adapter = rvAdapter

        newsViewModel.newsData.observe(this) { apiResults ->
            when (apiResults) {
                is ApiResults.Success -> {
                    viewFlipper.displayedChild = 1
                    if (apiResults.data.articles != null) {
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
    }

    override fun onItemClicked(t: Article, pos: Int, tag: String?) {
        if (activity is MainActivity) {
            (activity as MainActivity).addFragment(
                NewsDetailsFragment.newInstance(Bundle().apply { putParcelable(DATA, t) }),
                NewsDetailsFragment::class.java.canonicalName,
                true
            )
        }
    }
}
