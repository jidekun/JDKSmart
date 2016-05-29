package com.jidekun.jdk.jdksmart.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by JDK on 2016/5/10.
 * 单例Toast
 */
public class JDKToast {
    protected  static Toast toast;

    /**
     * @param context 传入上下文
     * @param msg     要显示的信息
     */
    public static void SingleToast(Context context, String msg) {
        if (toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(msg);
        toast.show();
    }

    /**
     * @param context 传入上下文
     * @param msg     要显示的信息
     */
    public static void SingleToast(Context context, int msg) {
        SingleToast(context,msg+"");
    }
    /**
     * @param context 传入上下文
     * @param msg     要显示的信息
     */
    public static void SingleToast(Context context, float msg) {
        SingleToast(context,msg+"");
    }

}
