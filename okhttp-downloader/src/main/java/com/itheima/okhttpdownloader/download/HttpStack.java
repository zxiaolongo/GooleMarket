package com.itheima.okhttpdownloader.download;

import java.io.InputStream;

/**
 * HttpStack for Download!
 */

public interface HttpStack {

    InputStream download(String downloadUrl);

    void close();

    long getContentLength();
}
