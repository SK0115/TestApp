package com.test.app.module.main;

import android.net.wifi.WifiInfo;

import com.test.app.base.BasePresenter;
import com.test.app.base.BaseView;

public interface MainContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
        /**
         * 获取当前连接的wifi信息
         * 
         * @return
         */
        WifiInfo getConnectWifiInfo();
    }
}
