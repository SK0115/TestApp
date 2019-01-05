package com.test.app.module.main;

import com.test.app.base.BasePresenter;
import com.test.app.base.BaseView;

public interface MainContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {
    }
}
