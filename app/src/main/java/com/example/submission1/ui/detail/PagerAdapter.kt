@file:Suppress("DEPRECATION")

package com.example.submission1.ui.detail

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.submission1.R

class PagerAdapter(private val context: Context, fm: FragmentManager, data: Bundle) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var fragmenBundle : Bundle
    init {
        fragmenBundle = data
    }
    @StringRes
    private val Tab_Titles = intArrayOf(R.string.tab1, R.string.tab2)
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment? = null
        when(position){
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmenBundle
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(Tab_Titles[position])
    }

}