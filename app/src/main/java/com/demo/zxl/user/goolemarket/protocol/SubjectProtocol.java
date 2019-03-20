package com.demo.zxl.user.goolemarket.protocol;

import com.demo.zxl.user.goolemarket.bean.SubjectInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by HASEE.
 */
public class SubjectProtocol extends BaseProtocol<List<SubjectInfo>>{
    @Override
    public List<SubjectInfo> parseJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json,new TypeToken<List<SubjectInfo>>(){}.getType());
    }
}
