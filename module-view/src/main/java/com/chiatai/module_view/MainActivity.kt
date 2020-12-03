package com.chiatai.module_view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.chiatai.module_view.tablayout.TabLayoutActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.jumpToTabLayout).setOnClickListener {
            Intent(this,TabLayoutActivity::class.java).apply {
                startActivity(this)
            }
        }

    }
}