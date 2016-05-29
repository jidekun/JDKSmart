package com.jidekun.jdk.jdksmart.base;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by JDK on 2016/5/12.
 */
public abstract class BaseFragment extends Fragment {

    public Activity mActivity;

    //创建fragment
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    //创建fragment的布局
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = initView();
        return view;
    }

    //当activity的oncreate执行结束的回调方法
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化数据
        initDate();
    }

    //初始化布局 子类实现
    public abstract View initView();

    //初始化数据 子类实现
    public abstract void initDate();
}
