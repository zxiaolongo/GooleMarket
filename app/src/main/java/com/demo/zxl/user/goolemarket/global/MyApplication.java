package com.demo.zxl.user.goolemarket.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * Created by HASEE.
 */

public class MyApplication extends Application {
    //上下文对象
    private static Context ctx;
    //handler对象
    private static Handler handler;
    //主线程对象
    private static Thread mainThread;
    //主线程id
    private static long mainThreadId;

    @Override
    public void onCreate() {
        super.onCreate();
        //加sdk的时候

        //1.初始化上下文,让所有的页面都可以使用
        ctx = this;
        handler = new Handler();
        mainThread = Thread.currentThread();
        mainThreadId = Thread.currentThread().getId();
    }
    public static Context getContext(){
        return ctx;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static Thread getMainThread() {
        return mainThread;
    }

    public static long getMainThreadId() {
        return mainThreadId;
    }
}
