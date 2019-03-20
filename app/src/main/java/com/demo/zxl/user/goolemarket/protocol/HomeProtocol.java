package com.demo.zxl.user.goolemarket.protocol;

import com.demo.zxl.user.goolemarket.bean.HomeInfo;
import com.google.gson.Gson;

/**
 * Created by HASEE.
 * 发送首页请求,在此类中,只需要管理首页json的解析即可
 */

public class HomeProtocol extends BaseProtocol<HomeInfo>{
    @Override
    public HomeInfo parseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, HomeInfo.class);
    }
}
