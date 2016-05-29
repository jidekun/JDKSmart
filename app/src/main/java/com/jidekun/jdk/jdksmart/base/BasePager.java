package com.jidekun.jdk.jdksmart.base;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import android.widget.SlidingDrawer;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jidekun.jdk.jdksmart.R;
import com.jidekun.jdk.jdksmart.activity.HomeActivity;
import com.jidekun.jdk.jdksmart.utils.JDKToast;

/**
 * Created by JDK on 2016/5/13.
 */
public abstract class BasePager {
    public Activity mActivity;
    public View view;
    /**
     * 主界面碎片上的帧布局
     */
    public FrameLayout mFrameLayout;
    /**
     * 主界面碎片上的标题
     */
    public TextView mTextview;
    /**
     * 主界面碎片上按钮
     */
    public ImageButton mImageView;

    /**
     * 构造方法获得activity 可以当上下文
     */
    public BasePager(Activity activity) {
        mActivity = activity;
        view = initView();
    }

    /**
     * 初始化视图
     */
    public View initView() {
        View view = View.inflate(mActivity, R.layout.basepager, null);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.fl_basepager);
        mTextview = (TextView) view.findViewById(R.id.tv_basepager_title);
        mImageView = (ImageButton) view.findViewById(R.id.iv_basepager_title);
        //设置图标的点击事件
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity homeActivity = (HomeActivity) mActivity;
                SlidingMenu slidingMenu = homeActivity.getSlidingMenu();
                slidingMenu.toggle();
            }
        });

        return view;
    }

    /**
     * 初始化数据
     */
    public abstract void initData();

}
