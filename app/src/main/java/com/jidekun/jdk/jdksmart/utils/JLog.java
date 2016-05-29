package com.jidekun.jdk.jdksmart.utils;

import android.app.Activity;
import android.util.Log;

import com.jidekun.jdk.jdksmart.bean.NewsBean;

/**
 * Created by JDK on 2016/5/10.
 * 可以一键关闭Log日志
 * 通过SP
 */
public class JLog {
    /**
     * debug 为true状态下能打印log
     */
    private static final boolean DEBUG = true;

    /**
     * 打印ERROR级别Log
     *
     * @param TAG
     * @param msg
     */
    public static void e(String TAG, String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String TAG, int msg) {
        e(TAG, msg + "");
    }

    /**
     * 打印级别Log
     *
     * @param TAG
     * @param msg
     */
    public static void i(String TAG, String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    /**
     * 打印DEBUG级别Log
     *
     * @param TAG
     * @param msg
     */
    public static void d(String TAG, String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }



}
