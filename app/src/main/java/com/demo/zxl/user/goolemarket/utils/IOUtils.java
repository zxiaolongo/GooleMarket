package com.demo.zxl.user.goolemarket.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by HASEE.
 */

public class IOUtils {
    public static void closeStream(Closeable... closeables){
        //由于流的关闭方法都是实现了Closeable 这个接口 所以传入的参数就是Closeable的数组
        if(closeables != null && closeables.length>0){
            for(Closeable closeable:closeables){
                if(closeable!=null){
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
