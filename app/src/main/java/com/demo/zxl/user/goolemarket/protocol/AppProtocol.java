package com.demo.zxl.user.goolemarket.protocol;

import android.util.Log;

import com.demo.zxl.user.goolemarket.bean.HomeInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by HASEE.
 */
public class AppProtocol extends BaseProtocol<List<HomeInfo.AppInfo>>{
    @Override
    public List<HomeInfo.AppInfo> parseJson(String json) {
        Log.i("*****",json);
        try {
            Gson gson = new Gson();
            return gson.fromJson(json,new TypeToken<List<HomeInfo.AppInfo>>(){}.getType());
        }catch (Exception e){
            return null;
        }

    }
}
