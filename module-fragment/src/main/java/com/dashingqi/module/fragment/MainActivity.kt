package com.dashingqi.module.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val fragments = ArrayList<Fragment>()

    private val mTitles = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for (index in 0 until 3) {
            fragments.add(TestFragment())
            mTitles.add("title$index")
            tabLayout.newTab().text = mTitles[index]
        }
        viewPager.adapter = TestFragmentAdapter(supportFragmentManager, fragments, mTitles)
        tabLayout.setupWithViewPager(viewPager)
    }
}
