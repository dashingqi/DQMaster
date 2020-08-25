package com.dashingqi.master;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dashingqi.annotation.BindView;
import com.dashingqi.annotation.Route;
import com.dashingqi.annotation.Test;

@Route(value = "/test/test_activity")
@Test
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvText)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
        ButterKnife.bind(this);
        mTextView.setText("hei,ha");
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouteUtils.getInstance().jumpToActivity(MainActivity.this, "/test/test_activity");
            }
        });

    }
}