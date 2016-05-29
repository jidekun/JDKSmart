package com.jidekun.jdk.jdksmart.activity;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.jidekun.jdk.jdksmart.R;
import com.jidekun.jdk.jdksmart.fragament.ContentFragment;
import com.jidekun.jdk.jdksmart.fragament.LeftMenutFragment;


public class HomeActivity extends SlidingFragmentActivity {

    public final String CONTENT = "fragment_content";
    public final String LEFT_MENU = "fragment_left_menu";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //无标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        //设置侧边栏
        setBehindContentView(R.layout.layout);
        SlidingMenu slidingMenu = getSlidingMenu();
        // 设置全屏触摸
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置侧边栏宽度


        //    slidingMenu.setBehindOffset(500);// 设置屏幕预留宽度
        //根据屏幕的宽度 设置不同分辨率下侧边栏的宽度,
        WindowManager wm = getWindowManager();
        //获得当前屏幕的宽
        int width = wm.getDefaultDisplay().getWidth();
        slidingMenu.setBehindOffset((int) (width*0.7));// 设置屏幕预留宽度
        initFragment();
    }

    //初始化碎片,用碎片替换掉主界面和侧滑菜单
    public void initFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //用碎片替换掉主页面,设置TAG
        ft.replace(R.id.home_content, new ContentFragment(), CONTENT);
        //用碎片替换掉测侧滑栏 ,设置TAG
        ft.replace(R.id.left_menu, new LeftMenutFragment(), LEFT_MENU);
        ft.commit();
    }
    //获得侧滑菜单Fragment对象

    public LeftMenutFragment getLeftMenuFragment() {
        //通过fragment的tag获得其对象
        LeftMenutFragment leftMenutFragment = (LeftMenutFragment) getFragmentManager().findFragmentByTag(LEFT_MENU);
        return leftMenutFragment;
    }
    //获得主界面碎片对象
    public ContentFragment getContentFragment() {
        //通过fragment的tag获得其对象
        ContentFragment contentFragment = (ContentFragment) getFragmentManager().findFragmentByTag(CONTENT);
        return contentFragment;
    }
}
