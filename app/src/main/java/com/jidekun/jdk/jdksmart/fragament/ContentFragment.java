package com.jidekun.jdk.jdksmart.fragament;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;


import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jidekun.jdk.jdksmart.R;
import com.jidekun.jdk.jdksmart.activity.HomeActivity;
import com.jidekun.jdk.jdksmart.base.BaseFragment;
import com.jidekun.jdk.jdksmart.base.BasePager;
import com.jidekun.jdk.jdksmart.base.iml.GovPager;
import com.jidekun.jdk.jdksmart.base.iml.HomePager;
import com.jidekun.jdk.jdksmart.base.iml.NewsPager;
import com.jidekun.jdk.jdksmart.base.iml.SettingPager;
import com.jidekun.jdk.jdksmart.base.iml.SmartPager;
import com.jidekun.jdk.jdksmart.ui.NoSldingViewPager;

import java.util.ArrayList;


/**
 * Created by JDK on 2016/5/12.
 * 主界面的内容
 */
public class ContentFragment extends BaseFragment implements NoSldingViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {


    private RadioGroup rg;
    /**
     * 五个填界面
     */
    public ArrayList<BasePager> list;
    private MyPagerAdapter myPagerAdapter;
    public NoSldingViewPager vp;


    @Override
    public View initView() {
        View view = View.inflate(mActivity, R.layout.fragment_content, null);
        return view;

    }

    /**
     * 初始化数据
     */
    @Override
    public void initDate() {
        list = new ArrayList<>();
        list.add(new HomePager(mActivity));
        list.add(new NewsPager(mActivity));
        list.add(new SmartPager(mActivity));
        list.add(new GovPager(mActivity));
        list.add(new SettingPager(mActivity));
        myPagerAdapter = new MyPagerAdapter();
        vp.setAdapter(myPagerAdapter);
        vp.setCurrentItem(0, false);
        //设置RadioGroup的监听
        rg.setOnCheckedChangeListener(this);
        //设置Viewpager的监听
        vp.addOnPageChangeListener(this);
        //设置侧滑菜单为滑动
        setSlidingFiling(false);
        //默认初始化第一个页面的数据
        BasePager basePager = list.get(0);
        basePager.initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        //找到界面上的ViewPager
        vp = (NoSldingViewPager) rootView.findViewById(R.id.vp_home);
        //找到界面上的RadioGroup
        rg = (RadioGroup) rootView.findViewById(R.id.rg_home);

        return rootView;
    }

    /**
     * 当Viewpager 页面变化时
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        BasePager basePager = list.get(position);
        //只有当条目被选中时,才初始化对应的数据
        basePager.initData();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    /**
     * 当RadioGroup 的条目被选时的回调
     *
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                vp.setCurrentItem(0, false);
                //JDKToast.SingleToast(mActivity,"主页"+checkedId);
                setSlidingFiling(false);
                break;
            case R.id.rb_news:
                vp.setCurrentItem(1, false);
                //JDKToast.SingleToast(mActivity,"新闻"+checkedId);
                setSlidingFiling(true);
                break;
            case R.id.rb_smart:
                vp.setCurrentItem(2, false);
                // JDKToast.SingleToast(mActivity,"多彩"+checkedId);
                setSlidingFiling(true);
                break;
            case R.id.rb_gov:
                vp.setCurrentItem(3, false);
                //JDKToast.SingleToast(mActivity,"政府"+checkedId);
                setSlidingFiling(true);
                break;
            case R.id.rb_setting:
                vp.setCurrentItem(4, false);
                // JDKToast.SingleToast(mActivity,"设置"+checkedId);
                //当是设置界面时 slidingMenu不滑动
                // 设置不滑动
                setSlidingFiling(false);
                break;
        }
    }

    /**
     * 设置SlidingMenu是否能滑开
     */
    public void setSlidingFiling(boolean isFiling) {
        HomeActivity homeActivity = (HomeActivity) getActivity();
        SlidingMenu slidingMenu = homeActivity.getSlidingMenu();
        if (isFiling) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }else{
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }


    }

    /**
     * viewpager数据适配器
     */
    class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BasePager basePager = list.get(position);
            View view = basePager.view;
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            BasePager basePager = list.get(position);
            View view = basePager.view;
            container.removeView(view);
        }
    }
}
