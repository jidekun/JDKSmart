package com.jidekun.jdk.jdksmart.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by JDK on 2016/5/13.
 */
public class NoSldingViewPager  extends ViewPager {
    public NoSldingViewPager(Context context) {
        this(context, null);
    }

    public NoSldingViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //强制父类不拦截子类的事件
        return false;
    }
}
