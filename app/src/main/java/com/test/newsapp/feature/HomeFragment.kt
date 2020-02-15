package com.test.newsapp.feature


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.test.newsapp.R

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var vpAdapter: VpAdapater
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private val tabItemsText = arrayOf("NEWS", "SAVED")

    companion object {
        fun newInstance(args: Bundle? = null) = HomeFragment().apply { arguments = args }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tabLayout = view.findViewById(R.id.tlNews)
        viewPager = view.findViewById(R.id.vpNews)
        vpAdapter = VpAdapater(childFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        viewPager.adapter = vpAdapter

        for (tabIndex in 0 until tabLayout.tabCount) {
            tabLayout.getTabAt(tabIndex)?.text = tabItemsText[tabIndex]
        }
    }

}
