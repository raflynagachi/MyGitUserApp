package com.dicoding.picodiploma.mygituserapp.viewmodel

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.picodiploma.mygituserapp.R
import com.dicoding.picodiploma.mygituserapp.view.FollowerFragment
import com.dicoding.picodiploma.mygituserapp.view.FollowingFragment

class ViewPagerDetailAdapter(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf(
        FollowerFragment(),
        FollowingFragment()
    )

    private val tabTitles = intArrayOf(
        R.string.followertxt,
        R.string.followingtxt

    )

    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(tabTitles[position])
    }

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }
}