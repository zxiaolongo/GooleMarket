package com.demo.zxl.user.goolemarket.bean;

import java.util.List;

/**
 * Created by HASEE.
 */
public class CategoryInfo {
    public String title;
    public List<CategoryInfoItem> infos;

    public class CategoryInfoItem{
        public String url1;
        public String url2;
        public String url3;
        public String name1;
        public String name2;
        public String name3;
    }
}
