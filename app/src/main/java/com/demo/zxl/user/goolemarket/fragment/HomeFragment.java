package com.demo.zxl.user.goolemarket.fragment;

import android.view.View;

import com.demo.zxl.user.goolemarket.view.LoadingPage;


/**
 * Created by HASEE.
 */
public class HomeFragment extends BaseFragment {

    @Override
    public LoadingPage.ResultState onSubLoad() {
        //请求过程中失败,失败枚举对象状态码
        return LoadingPage.ResultState.RESULT_STATE_ERROR;
    }

    @Override
    public View onSubCreateSuccessedView() {
        return null;
    }
    //将4套大家都有的布局效果封装在父类中统一处理
}
