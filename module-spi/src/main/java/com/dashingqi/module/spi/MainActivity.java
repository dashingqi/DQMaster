package com.dashingqi.module.spi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dashingqi.module.spi.intyerface.Play;

import java.util.Iterator;
import java.util.ServiceLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loadInterface = findViewById(R.id.load_interface);
        loadInterface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMethod();
            }
        });
    }

    /**
     * SPI技术
     * Service Provider Interface
     * 查找接口实现的服务 多模块开发的时候
     */
    private void loadMethod() {
        ServiceLoader<Play> load = ServiceLoader.load(Play.class);
        Iterator<Play> iterator = load.iterator();
        while (iterator.hasNext()) {
            iterator.next().play();
        }
    }
}
