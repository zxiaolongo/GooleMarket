package com.demo.zxl.user.goolemarket.protocol;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by HASEE.
 */
public class RecommendProtocol extends BaseProtocol<List<String>>{
    @Override
    public List<String> parseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json,new TypeToken<List<String>>(){}.getType());
    }
}
