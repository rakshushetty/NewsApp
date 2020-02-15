package com.test.newsapp.feature


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.test.newsapp.R

/**
 * A simple [Fragment] subclass.
 */
class SavedNewsFragment : Fragment(R.layout.fragment_saved_news) {

    companion object {
        fun newInstance(args: Bundle? = null) = SavedNewsFragment().apply { arguments = args }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
