package com.demo.zxl.user.goolemarket.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.demo.zxl.user.goolemarket.utils.UIUitls;
import com.demo.zxl.user.goolemarket.view.LoadingPage;

/**
 * Created by HASEE.
 */
public abstract class BaseFragment extends Fragment{
    private LoadingPage loadingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //加载失败view  加载为空view   加载中view    加载成功view
        loadingPage = new LoadingPage(UIUitls.getContext()) {
            @Override
            public ResultState onLoad() {
                //请求网络方法,而且请求结果必须是ResultState这个枚举中的3个对象之一
                //url
                //请求方式
                //请求参数
                //请求结果
                return onSubLoad();
            }

            @Override
            public View onCreateSuccessedView() {
                //将请求成功后(首页,应用,游戏)布局文件转换成view对象方法
                return onSubCreateSuccessedView();
            }
        };
        return loadingPage;
    }

    public void getData(){
        if (loadingPage!=null){
            loadingPage.show();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getData();
        super.onActivityCreated(savedInstanceState);
    }

    //(首页,应用,游戏) 请求过程有差异,所以此处无法统一处理,交由子类实现
    public abstract LoadingPage.ResultState onSubLoad();

    //(首页,应用,游戏)布局文件都有差异,所以在此处无法统一处理,交由子类实现
    public abstract View onSubCreateSuccessedView() ;
}
