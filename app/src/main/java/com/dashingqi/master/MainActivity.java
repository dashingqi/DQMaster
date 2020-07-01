package com.dashingqi.master;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dashingqi.annotation.BindView;
import com.dashingqi.annotation.Route;

@Route(value = "/test/test_activity")
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvText)
    TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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