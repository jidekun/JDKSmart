package com.jidekun.jdk.jdksmart.base.iml;

import android.app.Activity;

import com.jidekun.jdk.jdksmart.activity.HomeActivity;
import com.jidekun.jdk.jdksmart.base.BaseMenuDetaiPager;
import com.jidekun.jdk.jdksmart.base.BasePager;
import com.jidekun.jdk.jdksmart.base.menu.InteractMenuDetaiPager;
import com.jidekun.jdk.jdksmart.base.menu.NewsMenuDetaiPager;
import com.jidekun.jdk.jdksmart.base.menu.PhotosMenuDetaiPager;
import com.jidekun.jdk.jdksmart.base.menu.TopicMenuDetaiPager;
import com.jidekun.jdk.jdksmart.bean.NewsBean;
import com.jidekun.jdk.jdksmart.fragament.LeftMenutFragment;
import com.jidekun.jdk.jdksmart.global.GlobalConstants;
import com.jidekun.jdk.jdksmart.net.Net;


import java.util.ArrayList;


/**
 * Created by JDK on 2016/5/13.
 */
public class NewsPager extends BasePager {

    public NewsBean nnewsBean;
    private ArrayList<BaseMenuDetaiPager> menuList;

    /**
     * 构造方法获得activity 可以当上下文
     *
     * @param activity
     */
    public NewsPager(Activity activity) {
        super(activity);
    }

    @Override
    public void initData() {


        //找到标题栏的默认文字
        mTextview.setText("新闻");
        //通过Net类访问网络,获得数据
        Net.getInstance(mActivity).getServiceData(GlobalConstants.CATEGORY_URL, NewsBean.class, new Net.OnGetNewsPagerNet() {
            @Override
            public void onGetData(Object newsBean) {


                nnewsBean = (NewsBean) newsBean;
                menuList = new ArrayList<>();
                menuList.add(new NewsMenuDetaiPager(mActivity, nnewsBean.data.get(0)));
                menuList.add(new TopicMenuDetaiPager(mActivity));
                menuList.add(new PhotosMenuDetaiPager(mActivity));
                menuList.add(new InteractMenuDetaiPager(mActivity));
                BaseMenuDetaiPager baseMenuDetaiPager = menuList.get(0);
                //初始化第一个View
                 mFrameLayout.removeAllViews();
                mFrameLayout.addView(baseMenuDetaiPager.mRootView);
                HomeActivity homeActivity = (HomeActivity) mActivity;
                //获得侧滑栏fragment的对象
                LeftMenutFragment leftMenuFragment = homeActivity.getLeftMenuFragment();
                //将数据传递过去
                leftMenuFragment.addNewsData(nnewsBean.data);
            }

            @Override
            public void onFailure() {

            }


        });
    }

    //左侧滑栏选择第几个界面,总共四个
    public void setViewData(int position) {
        mTextview.setText(nnewsBean.data.get(position).title);
        mFrameLayout.removeAllViews();
        mFrameLayout.addView(menuList.get(position).mRootView);
    }
}
