package com.test.newsapp.feature

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

const val NEWS_LIST = 0
const val SAVED_NEWS = 1
const val SCREEN_COUNT = 2

/**
 * Adapter class for showing saved and current news items
 */
class VpAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    /**
     * Returns fragment instance for the given position
     *
     * @param position position of tab
     */
    override fun getItem(position: Int): Fragment {
        return when (position) {
            NEWS_LIST -> NewsListFragment.newInstance()
            SAVED_NEWS -> SavedNewsFragment.newInstance()
            else -> throw IllegalStateException("UnKnown state $position")
        }
    }

    /**
     * Returns total number of tabs
     */
    override fun getCount(): Int {
        return SCREEN_COUNT
    }
}