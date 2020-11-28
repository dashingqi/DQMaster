package com.chiatai.module_view.tablayout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chiatai.module_view.R

/**
 * @author : zhangqi
 * @time : 11/18/20
 * desc :
 */
class TabLayoutFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.fragment_tab_layout, container, false)
    }
}