package com.demo.zxl.user.goolemarket.protocol;

import com.demo.zxl.user.goolemarket.bean.HomeInfo;
import com.google.gson.Gson;

/**
 * Created by HASEE.
 */
public class DetailProtocol extends BaseProtocol<HomeInfo.AppInfo>{
    @Override
    public HomeInfo.AppInfo parseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, HomeInfo.AppInfo.class);
    }
}
