package com.demo.zxl.user.goolemarket.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.view.View;

import com.demo.zxl.user.goolemarket.global.MyApplication;


/**
 * Created by HASEE.
 */

public class UIUitls {
    public static Context getContext(){
        return MyApplication.getContext();
    }
    public static Thread getMainThread(){
        return MyApplication.getMainThread();
    }
    public static Handler getHandler(){
        return MyApplication.getHandler();
    }
    public static long getMainThreadId(){
        return MyApplication.getMainThreadId();
    }

    public static View inflate(int layoutId){
        return View.inflate(getContext(),layoutId,null);
    }
    public static Resources getResource(){
        return getContext().getResources();
    }
    public static String getString(int stringId){
        return getResource().getString(stringId);
    }
    public static String[] getStringArray(int stringArrayId){
        return getResource().getStringArray(stringArrayId);
    }

    public static void runOnMainThread(Runnable runnable){
//        if (Thread.currentThread() != getMainThread()) {
        if (Thread.currentThread().getId() != getMainThreadId()){
            getHandler().post(runnable);
        } else {
            runnable.run();
        }
    }

    //dp转换成px
    public static int dip2px(int dip){
        float density = getResource().getDisplayMetrics().density;
        return (int)(density*dip+0.5f);
    }

    //px转换成dp
    public static int px2dip(int px){
        float density = getResource().getDisplayMetrics().density;
        return (int)(px/density+0.5f);
    }
}
