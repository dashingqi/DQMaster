package com.dashingqi.master;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.dashingqi.annotation.Test;

@Test
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}