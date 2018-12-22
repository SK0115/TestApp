package com.test.app.module.main;

import android.net.wifi.WifiInfo;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.test.app.R;
import com.test.app.activity.MainActivity;
import com.test.app.base.BaseFragment;
import com.test.app.util.CommonUtil;

import butterknife.BindView;

public class MainFragment extends BaseFragment implements MainContract.View {

    private final String TAG = "MainFragment";
    private MainActivity mActivity;
    private MainContract.Presenter mPresenter;

    @BindView(R.id.iv_has_connect)
    ImageView mConnectWifiImage;
    @BindView(R.id.tv_has_connect_name)
    TextView mConnectWifiName;
    @BindView(R.id.tv_has_connect_ip)
    TextView mConnectWifiIP;

    public static MainFragment getInstance() {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initView() {
        if (!CommonUtil.checkIsNull(mPresenter)) {
            WifiInfo wifiInfo = mPresenter.getConnectWifiInfo();
            if (!CommonUtil.checkIsNull(wifiInfo)) {
                mConnectWifiName.setText(wifiInfo.getSSID());
            }
        }
    }

    @Override
    protected void initData() {
        mActivity = (MainActivity) getActivity();
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
