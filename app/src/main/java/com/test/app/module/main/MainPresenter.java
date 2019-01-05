package com.test.app.module.main;

import android.content.Context;

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
}
