package com.jidekun.jdk.jdksmart.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by JDK on 2016/5/16.
 */
public class TopNewsViewPager extends ViewPager {
    public TopNewsViewPager(Context context) {
        super(context);
    }

    public TopNewsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN :


                break;
            case MotionEvent.ACTION_MOVE :


                break;
        }
        //请求所有父控件不拦截
        getParent().requestDisallowInterceptTouchEvent(true);

        return super.dispatchTouchEvent(ev);


    }
}
