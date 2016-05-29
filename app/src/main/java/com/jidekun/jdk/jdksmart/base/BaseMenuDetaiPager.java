package com.jidekun.jdk.jdksmart.base;

import android.app.Activity;
import android.view.View;

/**
 * Created by JDK on 2016/5/14.
 * 作为侧滑菜单所选择的新闻页面中的四个子页面的父类
 */
public abstract class BaseMenuDetaiPager {
    //上下文
    public Activity activity;
    //根布局
    public final View mRootView;

    public BaseMenuDetaiPager(Activity activity) {
        this.activity = activity;
        mRootView = initView();

    }

    /**
     * 初始化View,由子类自己去实现
     * @return
     */
    public abstract View initView();

    public  void initData(){}
}
