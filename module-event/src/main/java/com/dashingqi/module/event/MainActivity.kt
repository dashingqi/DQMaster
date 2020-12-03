package com.dashingqi.module.event

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    lateinit var mRecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRecyclerView =  findViewById(R.id.recyclerView)
        initData()

    }


    private fun initData(){
        val data = ArrayList<String>()

        for (i in 0..30){
            data.add("$i")
        }

        val adapter = MyActivityAdapter(data)
        mRecyclerView.adapter = adapter

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        ev?.let {
            if (it.action == MotionEvent.ACTION_DOWN) {
                Log.d(TAG, "dispatchTouchEvent: ")
            }

            if (window.superDispatchTouchEvent(it)) {
                Log.d(TAG, "dispatchTouchEvent 这里被调用了: ")
                return true
            }
        }
        return onTouchEvent(ev)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        Log.d(TAG, "Activity ---> : onTouchEvent")
        return super.onTouchEvent(event)
    }

    override fun onUserInteraction() {
        super.onUserInteraction()
    }
}