package com.jidekun.jdk.jdksmart.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jidekun.jdk.jdksmart.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by JDK on 2016/5/17.
 */
public class RefreshListView extends ListView implements AbsListView.OnScrollListener {
    //下拉刷新
    private static final int XIALA = 0;
    //松开刷新
    private static final int SONGKAI = 1;
    //刷新
    private static final int SHUAXIN = 2;
    //当前状态
    int currenState = XIALA;
    boolean isLoaderMore = false;
    private View inflate, footView;
    private int mHeight, mFootHeight;

    private TextView tvTitle, tvTitlefoot, tvTime;
    private ImageView ivArrow;
    private ProgressBar pbProgress;

    private RotateAnimation animUp;
    private RotateAnimation animDown;
    private int startY;
    private ProgressBar footProgress;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHeaderView();
        initFooterView();
        initAnim();
        setCurrentTime();

    }

    /**
     * 初始化头布局
     */
    private void initHeaderView() {
        inflate = View.inflate(getContext(), R.layout.pull_to_refresh, null);
        this.addHeaderView(inflate);
        tvTitle = (TextView) inflate.findViewById(R.id.tv_title);
        tvTime = (TextView) inflate.findViewById(R.id.tv_time);
        ivArrow = (ImageView) inflate.findViewById(R.id.iv_arrow);
        pbProgress = (ProgressBar) inflate.findViewById(R.id.pb_loading);
        inflate.measure(0, 0);
        //头布局测量后的高
        mHeight = inflate.getMeasuredHeight();
        //隐藏头布局
        inflate.setPadding(0, -mHeight, 0, 0);
    }

    /**
     * 初始化脚布局
     */
    private void initFooterView() {
        footView = View.inflate(getContext(), R.layout.pull_to_refresh_footer, null);
        this.addFooterView(footView);
        tvTitlefoot = (TextView) inflate.findViewById(R.id.tv_title_foot);
        footProgress = (ProgressBar) inflate.findViewById(R.id.pb_loading_foot);
        footView.measure(0, 0);
        //头布局测量后的高
        mFootHeight = footView.getMeasuredHeight();
        //隐藏头布局
        footView.setPadding(0, -mFootHeight, 0, 0);
        this.setOnScrollListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (startY == -1) {// 当用户按住头条新闻的viewpager进行下拉时,ACTION_DOWN会被viewpager消费掉,
                    // 导致startY没有赋值,此处需要重新获取一下
                    startY = (int) ev.getY();
                }
                if (currenState == SHUAXIN) {
                    // 如果是正在刷新, 跳出循环
                    break;
                }
                int endY = (int) ev.getY();
                int dy = endY - startY;
                int firstVisiblePosition = getFirstVisiblePosition();// 当前显示的第一个item的位置
                // 必须下拉,并且当前显示的是第一个item
                if (dy > 0 && firstVisiblePosition == 0) {
                    int padding = dy - mHeight;// 计算当前下拉控件的padding值
                    inflate.setPadding(0, padding, 0, 0);

                    if (padding > 0 && currenState != SONGKAI) {
                        // 改为松开刷新
                        currenState = SONGKAI;
                        refreshState();
                    } else if (padding < 0
                            && currenState != XIALA) {
                        // 改为下拉刷新
                        currenState = XIALA;
                        refreshState();
                    }
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                startY = -1;
                if (currenState == SONGKAI) {
                    currenState = SHUAXIN;
                    refreshState();
                    // 完整展示头布局
                    inflate.setPadding(0, 0, 0, 0);
                    //设置回调
                    if (onRefreshLis != null) {
                        onRefreshLis.onPullRefresh();
                    }
                } else if (currenState == XIALA) {
                    // 隐藏头布局
                    inflate.setPadding(0, -mHeight, 0, 0);
                }
                break;
            default:
                break;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 根据当前状态刷新界面
     */
    private void refreshState() {
        switch (currenState) {
            case XIALA:
                tvTitle.setText("下拉刷新");
                pbProgress.setVisibility(View.INVISIBLE);
                ivArrow.setVisibility(View.VISIBLE);
                ivArrow.startAnimation(animDown);
                break;
            case SONGKAI:
                tvTitle.setText("松开刷新");
                pbProgress.setVisibility(View.INVISIBLE);
                ivArrow.setVisibility(View.VISIBLE);
                ivArrow.startAnimation(animUp);
                break;
            case SHUAXIN:
                tvTitle.setText("正在刷新...");
                ivArrow.clearAnimation();// 清除箭头动画,否则无法隐藏
                pbProgress.setVisibility(View.VISIBLE);
                ivArrow.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    /**
     * 初始化箭头动画
     */
    private void initAnim() {
        animUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        animUp.setDuration(200);
        animUp.setFillAfter(true);

        animDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animDown.setDuration(200);
        animDown.setFillAfter(true);
    }

    // 设置刷新时间
    private void setCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date());

        tvTime.setText(time);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        //获得最后一个可视条目
        int lastVisiblePosition = getLastVisiblePosition();
        int count = getAdapter().getCount();
        if (lastVisiblePosition == count - 1) {
            if (!isLoaderMore) {
                footView.setPadding(0, 0, 0, 0);
                //将当前状态调到上啦加载更多
                isLoaderMore = true;
                setSelection(count - 1);
                //设置回调接口
                if (onRefreshLis != null) {
                    onRefreshLis.onLoaderMore();
                }
            }
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    /**
     * 停止下啦刷新或者上拉加载过多
     */
    public void stopRefresh(boolean success) {
        if (isLoaderMore) {
            footView.setPadding(0, -mFootHeight, 0, 0);
            isLoaderMore = false;
        } else {
            //隐藏头布局
            inflate.setPadding(0, -mHeight, 0, 0);
            currenState = XIALA;
            tvTitle.setText("下拉刷新");
            pbProgress.setVisibility(View.INVISIBLE);
            ivArrow.setVisibility(View.VISIBLE);

            if (success) {// 只有刷新成功之后才更新时间
                setCurrentTime();
            }
        }

    }

    //CallBack Interface
    public interface OnRefreshLis {
        void onPullRefresh();

        void onLoaderMore();
    }

    private OnRefreshLis onRefreshLis;

    public void setOnRefreshLis(OnRefreshLis onRefreshLis) {
        this.onRefreshLis = onRefreshLis;
    }
}
