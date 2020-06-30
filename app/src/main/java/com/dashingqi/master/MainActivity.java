package com.dashingqi.master;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.dashingqi.annotation.BindView;
import com.dashingqi.annotation.Test;

@Test
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvText)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityViewBinding.bind(this);
        mTextView.setText("hei,ha");
    }
}