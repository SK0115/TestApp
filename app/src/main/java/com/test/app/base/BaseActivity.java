package com.test.app.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.test.app.App;
import com.test.app.R;
import com.test.app.util.StatusBarUtil;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.addActivity(this);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setStatusBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.color_login_bg));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.clearLastActivity();
    }
}
