package com.demo.zxl.user.goolemarket.utils;

/**
 * Created by HASEE.
 */

public class Constant {
    //固定不变主机名
    public static final String BASEURL = "http://127.0.0.1:8090/";
    public static final String IMG = BASEURL+"image?name=";
    //从头开始下地址
    public static final String DOWNLOADURL = BASEURL+"download?name=";
    //断点续传下地址    占位符
    public static final String CONTINUEDOWNLOADURL = BASEURL+"download?name=%s&range=%d";
}
