package com.demo.zxl.user.goolemarket.protocol;

import com.demo.zxl.user.goolemarket.bean.CategoryInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by HASEE.
 */
public class CategoryProtocol extends BaseProtocol<List<CategoryInfo>>{
    @Override
    public List<CategoryInfo> parseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json,new TypeToken<List<CategoryInfo>>(){}.getType());
    }
}
