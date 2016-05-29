package com.jidekun.jdk.jdksmart.base.menu;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jidekun.jdk.jdksmart.R;
import com.jidekun.jdk.jdksmart.activity.HomeActivity;
import com.jidekun.jdk.jdksmart.base.BaseMenuDetaiPager;
import com.jidekun.jdk.jdksmart.base.tab.TabDetailPager;
import com.jidekun.jdk.jdksmart.bean.NewsBean;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

/**
 * Created by JDK on 2016/5/14.
 */
public class NewsMenuDetaiPager extends BaseMenuDetaiPager implements ViewPager.OnPageChangeListener {


    private static ViewPager vp;
    //新闻页面下的数据集合
    private NewsBean.NewsMenuBean children;
    private ArrayList<TabDetailPager> tabList;
    private TabPageIndicator tabpageindicator;
    private ArrayList<NewsBean.NewsMenuTab> newsMenuTabs;
    private ImageView iv;
    private LinearLayout ll;

    /**
     * 返回viewpager的对象
     *
     * @return
     */
    public static ViewPager getViewPager() {
        return vp;
    }


    /**
     * 构造方法
     *
     * @param activity
     * @param children
     */
    public NewsMenuDetaiPager(Activity activity, NewsBean.NewsMenuBean children) {
        super(activity);
        this.children = children;
        newsMenuTabs = children.children;
        initData();
    }


    @Override
    public View initView() {
        ll = (LinearLayout) View.inflate(activity, R.layout.newsmenudetaipager, null);
        vp = (ViewPager) ll.findViewById(R.id.vp_newsmenu);
        //指示器
        tabpageindicator = (TabPageIndicator) ll.findViewById(R.id.vpindicator);
        iv = (ImageView) ll.findViewById(R.id.iv_vpindicator);

        return ll;
    }

    @Override
    public void initData() {
        //初始化加载叶签
        tabList = new ArrayList<>();
        for (int i = 0; i < newsMenuTabs.size(); i++) {
            //将activity和NewsBean.NewsMenuTab对象传递过去
            TabDetailPager tabdetail = new TabDetailPager(activity, newsMenuTabs.get(i));
            tabList.add(tabdetail);
        }
        vp.setAdapter(new VPAdapter());
        //指示器和viewpager绑定
        tabpageindicator.setViewPager(vp);
        TabDetailPager tabDetailPager = tabList.get(0);
        tabDetailPager.initData();
        //设置viepager的监听


        vp.addOnPageChangeListener(this);


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击切换到下一个页面
                int currentItem = vp.getCurrentItem();
                currentItem++;
                vp.setCurrentItem(currentItem);
            }
        });

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //选中哪条 哪条才加载数据
        TabDetailPager tabDetailPager = tabList.get(position);
        tabDetailPager.initData();

        HomeActivity homeActivity = (HomeActivity) activity;
        SlidingMenu slidingMenu = homeActivity.getSlidingMenu();
        if (position == 0) {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        } else {
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    //vp的适配器
    class VPAdapter extends PagerAdapter {
        //指定指示器显示的文字
        @Override
        public CharSequence getPageTitle(int position) {
            return newsMenuTabs.get(position).title;
        }

        @Override
        public int getCount() {
            return tabList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            TabDetailPager tabDetailPager = tabList.get(position);

            View mRootView = tabDetailPager.mRootView;
            container.addView(mRootView);
            tabDetailPager.initData();
            return mRootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            System.out.println("销毁"+position);
            container.removeView((View) object);
        }


    }
}
