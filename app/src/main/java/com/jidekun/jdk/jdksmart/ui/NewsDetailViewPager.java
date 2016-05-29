package com.jidekun.jdk.jdksmart.ui;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.jidekun.jdk.jdksmart.base.menu.NewsMenuDetaiPager;

/**
 * Created by JDK on 2016/5/17.
 */
public class NewsDetailViewPager extends ViewPager {

    private int downX;
    private int downY;

    public NewsDetailViewPager(Context context) {
        super(context);
    }

    public NewsDetailViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        getParent().requestDisallowInterceptTouchEvent(true);
        //获得上级Viewpager对象
        ViewPager vp = NewsMenuDetaiPager.getViewPager();
        //获得上级viewpager当前选中的item
        int newsItem = vp.getCurrentItem();
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();

                int deleteX = downX - moveX;
                int deleteY = downY - moveY;
                //如果是左右移动
                if (Math.abs(deleteX) > Math.abs(deleteY)) {
                    if (newsItem == 0) {
                        if (getCurrentItem() == 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        } else if (getCurrentItem() == getAdapter().getCount() - 1) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    } else {
                        if (getCurrentItem() == 0) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        } else if (getCurrentItem() == getAdapter().getCount() - 1) {
                            getParent().requestDisallowInterceptTouchEvent(false);
                        }
                    }
                } else { //如果是上下移动
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
