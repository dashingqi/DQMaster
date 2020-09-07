package com.dashingqi.module.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager

/**
 * @author : zhangqi
 * @time : 2020/9/7
 * desc :
 */
class TestFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (container is ViewPager) {
            Log.e("TestFragment", "is ViewPager")
        }
        return inflater.inflate(R.layout.fragment_test_layout, container, false)
    }
}