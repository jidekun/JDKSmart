package com.jidekun.jdk.jdksmart.base.iml;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.jidekun.jdk.jdksmart.R;
import com.jidekun.jdk.jdksmart.base.BasePager;
import com.jidekun.jdk.jdksmart.utils.JLog;

/**
 * Created by JDK on 2016/5/13.
 */
public class SmartPager extends BasePager{
    /**
     * 构造方法获得activity 可以当上下文
     *
     * @param activity
     */
    public SmartPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {

        //找到标题栏的文字
        mTextview.setText("生活");
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
