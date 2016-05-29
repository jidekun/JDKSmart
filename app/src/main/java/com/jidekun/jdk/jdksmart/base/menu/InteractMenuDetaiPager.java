package com.jidekun.jdk.jdksmart.base.menu;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.jidekun.jdk.jdksmart.base.BaseMenuDetaiPager;

/**
 * Created by JDK on 2016/5/14.
 * 互动
 */
public class InteractMenuDetaiPager extends BaseMenuDetaiPager{
    public InteractMenuDetaiPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView tv = new TextView(activity);
        tv.setText("互动");
        return tv;
    }


}
