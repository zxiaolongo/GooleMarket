package com.demo.zxl.user.goolemarket.protocol;

import android.text.TextUtils;

import com.demo.zxl.user.goolemarket.utils.Constant;
import com.demo.zxl.user.goolemarket.utils.UIUitls;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HASEE.
 */

public abstract class BaseProtocol<T>{
    //获取数据
    public T getData(String url,int index,String params){
        //1.先拿缓存,如果缓存中的数据有效(缓存数据有没有过期),则展示缓存
        String json = getDataFromLocal(url,index,params);
        String result;
        if(!TextUtils.isEmpty(json)){
            //数据有意义,没有过期,解析展示
            result = json;
        }else{
            //2.如果数据过期了,则需要请求网络
            result = getDataFromNet(url,index,params);
        }
        return parseJson(result);
    }
    private String getDataFromNet(String url, int index,String params) {
        //Okhttp同步请求(自己手动开子线程)
        try {
            /*StringBuilder sb = new StringBuilder();
            for (String key: hashMap.keySet()) {
                String value = hashMap.get(key);
                sb.append(key+"="+value+"&");
            }*/
//            String paramsString = sb.toString();
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(2, TimeUnit.SECONDS)
                    .connectTimeout(2,TimeUnit.SECONDS)
                    .build();
//            http://127.0.0.1:8090/game?index=0  网络请求地址
            Request request = new Request.Builder()
                    .get()
                    .url(Constant.BASEURL+url+"?index="+index+params)
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            String json = response.body().string();
            if (!TextUtils.isEmpty(json)){
                //將json在本地进行缓存,此段json是那个页面请求缓存数据
                //url用于区分是那个页面的数据
                //index用于区分缓存的是那一页的数据
                writeToLocal(json,url,index,params);
                return json;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param json      需要缓存的json
     * @param url       用于区分缓存的是那个链接地址数据
     * @param index     用于区分缓存的是那个链接地址的那一页的数据
     */
    public void writeToLocal(String json, String url, int index,String params){
        FileWriter fileWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            //1.指定缓存方式文件
            File file = new File(UIUitls.getContext().getCacheDir(), url + index+params);
            //2.文件第一行写入此段json的有效时间戳
            fileWriter = new FileWriter(file.getAbsolutePath());
            bufferedWriter = new BufferedWriter(fileWriter);
            //获取服务器json时间
            long currentTime = System.currentTimeMillis();
            //此段json的有效时长有多久 5*60*1000
            //有效时间戳 = 获取json时间+有效时长
            long invalidTime = currentTime+5*60*1000;
            //写入有效时间戳
            bufferedWriter.write(invalidTime+"\r\n");
            //3.写入服务器返回的json字符串
            bufferedWriter.write(json.toCharArray());
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if (fileWriter!=null && bufferedWriter!=null){
                try {
                    fileWriter.close();
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //Okhttp异步请求(不用自己手动开子线程)
    private void okhttpAsync() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(2, TimeUnit.SECONDS)
                .connectTimeout(2,TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    //从本地缓存(sp,文件,数据库)中获取数据
    private String getDataFromLocal(String url, int index,String params) {
        try {
            //1.指定获取json文件路径
            File file = new File(UIUitls.getContext().getCacheDir(), url + index+params);
            //2.尝试读取文件中的第一行
            FileReader fileReader = new FileReader(file.getAbsolutePath());
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String lineOneStr = bufferedReader.readLine();
            long invalidTime = Long.parseLong(lineOneStr);
            long currentTime = System.currentTimeMillis();
            if (currentTime<invalidTime){
                //2.1 现在读取时间<有效时间戳(缓存有效),才可以获取缓存中的json
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine())!=null){
                    stringBuilder.append(temp);
                }
                return stringBuilder.toString();
            }
            //2.2 现在读取时间>有效时间戳(缓存过期),返回""字符串
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    //因为项目中每一个模块的json中的内容不一致,所以无法编写具体的javabean所以将此方法抽象
    public abstract T parseJson(String json);
}
