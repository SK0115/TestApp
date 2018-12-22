package com.test.app.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.test.app.R;
import com.test.app.base.BaseActivity;
import com.test.app.module.main.MainFragment;
import com.test.app.module.main.MainPresenter;
import com.test.app.util.CommonUtil;

public class MainActivity extends BaseActivity {

    private MainFragment mMainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.testapp_main_container);
        if (CommonUtil.checkIsNull(mMainFragment)) {
            mMainFragment = MainFragment.getInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.testapp_main_container, mMainFragment);
            transaction.commit();
        }
        new MainPresenter(MainActivity.this, mMainFragment);
    }

}
