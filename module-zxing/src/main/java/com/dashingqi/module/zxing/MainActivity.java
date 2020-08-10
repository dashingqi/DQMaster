package com.dashingqi.module.zxing;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Button mBtnGetQRCode;
    private ImageView mIvQRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnGetQRCode = findViewById(R.id.getQRCode);
        mIvQRCode = findViewById(R.id.ivQRCode);
        mBtnGetQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = QRCodeUtils.createQRCode("weixin://wxpay/bizpayurl?pr=50nrWYf", 200, 200);
                if (bitmap != null) {
                    mIvQRCode.setImageBitmap(bitmap);
                }
            }
        });
    }
}
