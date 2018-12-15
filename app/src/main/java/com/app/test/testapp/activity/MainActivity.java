package com.app.test.testapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.test.testapp.R;
import com.app.test.testapp.util.CommonUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CommonUtil.getDeviceSupportAbis();
        CommonUtil.getCpuArchitecture();
    }

}
