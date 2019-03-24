package com.itheima.okhttpdownloader.download;

import android.util.Log;


public class L {
    private static final String TAG = "OkhttpDownloader";
    private static boolean isDebug = true;
    public static void d(String msg){
        if(isDebug){
            Log.d(TAG,msg);
        }
    }
    public static void e(String msg){
        if(isDebug){
            Log.e(TAG,msg);
        }
    }
}
