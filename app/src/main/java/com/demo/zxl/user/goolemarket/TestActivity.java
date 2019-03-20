package com.demo.zxl.user.goolemarket;

import android.app.Activity;
import android.os.Bundle;

import com.demo.zxl.user.goolemarket.bean.HomeInfo;
import com.demo.zxl.user.goolemarket.protocol.HomeProtocol;
import com.demo.zxl.user.goolemarket.utils.LogUtils;


/**
 * Created by HASEE.
 */

public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogUtils.e("","error message..............");
        LogUtils.w("","warn message...........");
        LogUtils.i("","info message.......");
        LogUtils.d("","debug message..........");
        LogUtils.v("","verbose message.........");

        new Thread(){
            @Override
            public void run() {
                HomeProtocol homeProtocol = new HomeProtocol();
                HomeInfo home = homeProtocol.getData("home", 0,"");
                LogUtils.i("","home.getList() = "+home.getList());
            }
        }.start();
    }
}
