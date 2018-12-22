package com.test.app.module.main;

import android.content.Context;
import android.net.wifi.WifiInfo;

import com.test.app.util.CommonUtil;

public class MainPresenter implements MainContract.Presenter {

    private final String TAG = "MainPresenter";
    private Context mContext;
    private MainContract.View mView;

    public MainPresenter(Context context, MainContract.View view) {
        mContext = context;
        mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void onDetach() {
    }

    @Override
    public WifiInfo getConnectWifiInfo() {
        return CommonUtil.getCurrentConnectingWIFI(mContext);
    }
}
