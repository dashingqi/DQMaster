package com.chiatai.module.glide;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ImageView mImageView;

    private final String imgUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1597745383673&di=63b536612dcc9e2a60d8d72b375c8163&imgtype=0&src=http%3A%2F%2Fa2.att.hudong.com%2F36%2F48%2F19300001357258133412489354717.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.imgView);
        findViewById(R.id.btnLoadImg).setOnClickListener(view -> {
            Glide.with(MainActivity.this).load(imgUrl).into(mImageView);
        });
    }
}
