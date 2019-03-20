package com.demo.zxl.user.goolemarket.bean;

/**
 * Created by HASEE.
 */
public class SubjectInfo {
    private String des;
    private String url;

    public SubjectInfo(String des, String url) {
        this.des = des;
        this.url = url;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
