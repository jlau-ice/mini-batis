package com.carbon.utils;

import java.io.InputStream;

/**
 * 类路径资源加载
 */
public class Resources {

    private Resources() {
    }

    public static InputStream getResourceAsStream(String resource) {
        return ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
    }

}


