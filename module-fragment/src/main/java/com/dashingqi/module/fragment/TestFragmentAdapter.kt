package com.dashingqi.module.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


/**
 * @author : zhangqi
 * @time : 2020/9/7
 * desc :
 */
class TestFragmentAdapter(manager: FragmentManager, var fragments: ArrayList<Fragment> = ArrayList(), var titles: ArrayList<String> = ArrayList()) : FragmentPagerAdapter(manager) {
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
    }
}