package com.demo.zxl.user.goolemarket.utils;

import android.util.Log;

/**
 * Created by HASEE.
 */

public class LogUtils {
    //提供一个管理日志开启或者关闭的变量
    public static boolean isOpen = false;
    //设定日志的级别
    public static final int LEVEL_VERBOES = 5;//打印所有日志
    public static final int LEVEL_DEBUG = 4;//打印小于等于4级别日志
    public static final int LEVEL_INF0 = 3;//打印小于等于3级别日志
    public static final int LEVEL_WRAN = 2;//打印小于等于2级别日志
    public static final int LEVEL_ERROR = 1;//打印小于等于1级别日志

    public static int current_level = LEVEL_VERBOES;

    public static void e(String tag,String message){
        if (isOpen){
            if (current_level>=LEVEL_ERROR){
                Log.e(tag,message);
            }
        }
    }
    public static void w(String tag,String message){
        if (isOpen){
            if (current_level>=LEVEL_WRAN){
                Log.w(tag,message);
            }
        }
    }
    public static void i(String tag,String message){
        if (isOpen){
            if (current_level>=LEVEL_INF0){
                Log.i(tag,message);
            }
        }
    }
    public static void d(String tag,String message){
        if (isOpen){
            if (current_level>=LEVEL_DEBUG){
                Log.d(tag,message);
            }
        }
    }
    public static void v(String tag,String message){
        if (isOpen){
            if (current_level>=LEVEL_VERBOES){
                Log.v(tag,message);
            }
        }
    }
}
