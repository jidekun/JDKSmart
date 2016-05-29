package com.jidekun.jdk.jdksmart.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.jidekun.jdk.jdksmart.R;
import com.jidekun.jdk.jdksmart.utils.PrefUtils;

/**
 * 欢迎界面
 */
public class WelcomActivity extends Activity {

    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcom);
        iv = (ImageView) findViewById(R.id.iv_splash);
        // 旋转动画
        RotateAnimation animRotate = new RotateAnimation(
                0,
                360,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f
        );
        // 动画时间
        animRotate.setDuration(1000);
        // 保持动画结束状态
        animRotate.setFillAfter(true);
        // 缩放动画
        ScaleAnimation animScale = new ScaleAnimation(
                0,
                1,
                0,
                1,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f
        );
        animScale.setDuration(1000);
        // 保持动画结束状态
        animScale.setFillAfter(true);
        // 渐变动画
        AlphaAnimation animAlpha = new AlphaAnimation(0, 1);
        animAlpha.setDuration(2000);// 动画时间
        animAlpha.setFillAfter(true);// 保持动画结束状态
        // 动画集合
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(animRotate);
        set.addAnimation(animScale);
        set.addAnimation(animAlpha);
        // 启动动画
        iv.startAnimation(set);

        //设置动画执行状态监听
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
            //当动画执行结束的时候,选择跳转页面
            @Override
            public void onAnimationEnd(Animation animation) {
                // 如果是第一次进入, 跳新手引导
                // 否则跳主页面
                //使用封装sp的工具类
                boolean isFirstEnter = PrefUtils.getBoolean(WelcomActivity.this, "is_first_enter", true);
                Intent intent;
                if (isFirstEnter) {
                    // 新手引导
                    intent = new Intent(getApplicationContext(),
                            FirstEnter.class);
                } else {
                    // 主页面
                    intent = new Intent(getApplicationContext(),
                            HomeActivity.class);
                }
                startActivity(intent);
                // 结束当前页面
                finish();
            }
        });
    }
}
