package com.demo.zxl.user.goolemarket.fragment;


import android.view.View;

import com.demo.zxl.user.goolemarket.view.LoadingPage;


/**
 * Created by HASEE.
 */
public class AppFragment extends BaseFragment {
    @Override
    public LoadingPage.ResultState onSubLoad() {
        return LoadingPage.ResultState.RESULT_STATE_EMPTY;
    }

    @Override
    public View onSubCreateSuccessedView() {
        return null;
    }
}
