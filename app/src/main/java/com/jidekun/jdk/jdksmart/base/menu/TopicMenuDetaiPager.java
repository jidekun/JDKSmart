package com.jidekun.jdk.jdksmart.base.menu;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.jidekun.jdk.jdksmart.base.BaseMenuDetaiPager;

/**
 * Created by JDK on 2016/5/14.
 * 主题
 */
public class TopicMenuDetaiPager extends BaseMenuDetaiPager {
    public TopicMenuDetaiPager(Activity activity) {
        super(activity);
    }

    @Override
    public View initView() {
        TextView tv = new TextView(activity);
        tv.setText("主题");
        return tv;
    }


}
