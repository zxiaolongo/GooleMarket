package com.demo.zxl.user.goolemarket.fragment;

import android.view.View;
import android.widget.TextView;

import com.demo.zxl.user.goolemarket.utils.UIUitls;
import com.demo.zxl.user.goolemarket.view.LoadingPage;


/**
 * Created by HASEE.
 */
public class HotFragment extends BaseFragment {
    @Override
    public LoadingPage.ResultState onSubLoad() {
        return LoadingPage.ResultState.RESULT_STATE_SUCCESSED;
    }

    @Override
    public View onSubCreateSuccessedView() {
        TextView textView = new TextView(UIUitls.getContext());
        textView.setText(getClass().getSimpleName());
        return textView;
    }
}
