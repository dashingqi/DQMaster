package com.chiatai.module_view.tablayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chiatai.module_view.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TabLayoutActivity : AppCompatActivity() {

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    val mFragments = ArrayList<Fragment>()
    val mTitles = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout)
        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager)

        configTabLayout()

    }


    private fun configTabLayout() {
        mFragments.add(TabLayoutFragment())
        mFragments.add(TabLayoutFragment())
        mFragments.add(TabLayoutFragment())
        mFragments.add(TabLayoutFragment())
        mFragments.add(TabLayoutFragment())
        mFragments.add(TabLayoutFragment())

        mFragments.forEach { _ ->
            mTitles.add("标题一")
        }
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return mFragments.size
            }
            override fun createFragment(position: Int): Fragment {
                return mFragments[position]
            }
        }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = mTitles[position]
        }.attach()
    }
}