package com.jidekun.jdk.jdksmart.activity;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.internal.$Gson$Types;
import com.jidekun.jdk.jdksmart.R;
import com.jidekun.jdk.jdksmart.utils.PrefUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class FirstEnter extends Activity {

    private ViewPager vp;
    //图片对象集合
    private ArrayList<ImageView> drawList;
    private FirstEnterPagerAdapter fepa;
    private LinearLayout ll;
    //红点
    private ImageView iv;
    //2点之间的宽度
    private int pointWidth;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_first_enter);
        iv = (ImageView) findViewById(R.id.iv_firstenter_red_point);
        vp = (ViewPager) findViewById(R.id.vp_firsenter);
        ll = (LinearLayout) findViewById(R.id.ll_firstenter);
        tv = (TextView) findViewById(R.id.tv_firsenter);
        //初始化数据
        initDate();
        fepa = new FirstEnterPagerAdapter();
        //设置适配器
        vp.setAdapter(fepa);
        //监听视图树绘制是否结束,获得小点的宽度
        iv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pointWidth = ll.getChildAt(1).getLeft() - ll.getChildAt(0).getLeft();
                iv.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        //设置viewpager滑动监听
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //获得红红点布局参数
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv.getLayoutParams();
                //viewpager移动时 红点的maging
                int width = (int) (positionOffset * pointWidth) + pointWidth * position;
                layoutParams.leftMargin = width;
                iv.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int position) {
                if (position == drawList.size() - 1) {
                    tv.setVisibility(View.VISIBLE);
                } else {
                    tv.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        //设置按钮的点击事件
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下次不进入新手引导页
                PrefUtils.setBoolean(FirstEnter.this, "is_first_enter", false);
                startActivity(new Intent(FirstEnter.this, HomeActivity.class));
                finish();
            }
        });

    }

    private void initDate() {
        //图片数组
        int draw[] = new int[]{
                R.drawable.guide_1,
                R.drawable.guide_2,
                R.drawable.guide_3
        };
        drawList = new ArrayList<>();
        ImageView iv;
        ImageView ii;

        //初始化数据
        for (int i = 0; i < draw.length; i++) {
            iv = new ImageView(this);
            iv.setBackgroundResource(draw[i]);
            //添加图片
            drawList.add(iv);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            ii = new ImageView(this);
            ii.setImageResource(R.drawable.ic_fiber_manual_record_black_24dp);
            if (i > 0) {
                layoutParams.leftMargin = 10;
            }
            ii.setLayoutParams(layoutParams);
            //添加圆点
            ll.addView(ii);
        }
    }


    class FirstEnterPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return drawList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ImageView iv = drawList.get(position);
            vp.removeView(iv);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = drawList.get(position);
            vp.addView(iv);
            return iv;
        }
    }



}

