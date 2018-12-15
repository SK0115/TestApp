package com.test.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.app.R;
import com.test.app.util.CommonUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CommonUtil.getDeviceSupportAbis();
        CommonUtil.getCpuArchitecture();
    }

}
