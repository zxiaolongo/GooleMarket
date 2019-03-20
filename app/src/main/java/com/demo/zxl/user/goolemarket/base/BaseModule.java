package com.demo.zxl.user.goolemarket.base;

import android.content.Context;
import android.view.View;

/**
 * Created by HASEE.
 */

public abstract class BaseModule<T>{
    private Context mCtx;
    public View view;

    public BaseModule(Context ctx){
        this.mCtx = ctx;
        view = initView();
    }
    //因为在父类中不知道详情4个拆分出来模块的具体布局效果
    public abstract View initView();
    //因为在父类中不知道详情4个拆分出来模块的具体数据也不知道控件有哪些,所以提供一个给控件填充数据方法
    public abstract void bindData(T t);
}
