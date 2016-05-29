package com.jidekun.jdk.jdksmart.utils;

import android.content.Context;

/**
 * Created by JDK on 2016/5/14.
 */
public class CacheUtils {
    /**
     * 将Json缓存至本地
     */
    public static void setCache(Context context, String url, String json) {
        PrefUtils.setString(context, url, json);
    }

    /**
     * 从本地取出缓存
     *
     * @return Json字符串
     */
    public static String getCache(Context context, String url) {
        String string = PrefUtils.getString(context, url, null);
        return string;
    }
}
