package com.demo.zxl.user.goolemarket.bean;

import java.util.List;

/**
 * Created by HASEE.
 */

public class HomeInfo {

    private List<String> picture;
    private List<AppInfo> list;

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public List<AppInfo> getList() {
        return list;
    }

    public void setList(List<AppInfo> list) {
        this.list = list;
    }

    public static class AppInfo {
        /**
         * id : 1525490
         * name : 有缘网
         * packageName : com.youyuan.yyhl
         * iconUrl : app/com.youyuan.yyhl/icon.jpg
         * stars : 4
         * size : 3876203
         * downloadUrl : app/com.youyuan.yyhl/com.youyuan.yyhl.apk
         * des : 产品介绍：有缘是时下最受大众单身男女亲睐的婚恋交友软件。有缘网专注于通过轻松、
         */

        private int id;
        private String name;
        private String packageName;
        private String iconUrl;
        private float stars;
        private int size;
        private String downloadUrl;
        private String des;

        //在详情界面额外添加的字段
        private String downloadNum;
        private String version;
        private String date;
        private String author;

        private List<String> screen;
        private List<Safe> safe;

        public class Safe{
            private String safeUrl;
            private String safeDesUrl;
            private String safeDes;
            private int safeDesColor;

            public String getSafeUrl() {
                return safeUrl;
            }

            public void setSafeUrl(String safeUrl) {
                this.safeUrl = safeUrl;
            }

            public String getSafeDesUrl() {
                return safeDesUrl;
            }

            public void setSafeDesUrl(String safeDesUrl) {
                this.safeDesUrl = safeDesUrl;
            }

            public String getSafeDes() {
                return safeDes;
            }

            public void setSafeDes(String safeDes) {
                this.safeDes = safeDes;
            }

            public int getSafeDesColor() {
                return safeDesColor;
            }

            public void setSafeDesColor(int safeDesColor) {
                this.safeDesColor = safeDesColor;
            }
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public void setIconUrl(String iconUrl) {
            this.iconUrl = iconUrl;
        }

        public float getStars() {
            return stars;
        }

        public void setStars(float stars) {
            this.stars = stars;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getDownloadUrl() {
            return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
            this.downloadUrl = downloadUrl;
        }

        public String getDes() {
            return des;
        }

        public void setDes(String des) {
            this.des = des;
        }

        public String getDownloadNum() {
            return downloadNum;
        }

        public void setDownloadNum(String downloadNum) {
            this.downloadNum = downloadNum;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public List<String> getScreen() {
            return screen;
        }

        public void setScreen(List<String> screen) {
            this.screen = screen;
        }

        public List<Safe> getSafe() {
            return safe;
        }

        public void setSafe(List<Safe> safe) {
            this.safe = safe;
        }
    }
}
