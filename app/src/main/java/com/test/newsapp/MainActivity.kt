package com.test.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.test.newsapp.feature.HomeFragment

/**
 * Main activity class
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFragment(
            HomeFragment.newInstance(),
            HomeFragment::class.java.canonicalName
        )
    }

    /**
     * Adds a given fragment to the activity
     *
     * @param fragment fragment instance to be added
     * @param tag tag for fragment reference
     * @param isAddToBackStack is this fragment to be added to backStack
     */
    fun addFragment(fragment: Fragment, tag: String?, isAddToBackStack: Boolean = false) {
        supportFragmentManager.beginTransaction().apply {
            this.add(
                R.id.activityContainer,
                fragment,
                tag
            )
            if (isAddToBackStack) {
                addToBackStack(tag)
            }
        }.commitAllowingStateLoss()
    }
}
