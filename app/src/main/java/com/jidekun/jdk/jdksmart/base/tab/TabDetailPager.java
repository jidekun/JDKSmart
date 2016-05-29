package com.jidekun.jdk.jdksmart.base.tab;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jidekun.jdk.jdksmart.R;
import com.jidekun.jdk.jdksmart.activity.NewsDetailActivity;
import com.jidekun.jdk.jdksmart.base.BaseMenuDetaiPager;
import com.jidekun.jdk.jdksmart.bean.NewsBean;
import com.jidekun.jdk.jdksmart.bean.NewsTabBean;
import com.jidekun.jdk.jdksmart.global.GlobalConstants;
import com.jidekun.jdk.jdksmart.net.Net;
import com.jidekun.jdk.jdksmart.ui.NewsDetailViewPager;
import com.jidekun.jdk.jdksmart.ui.RefreshListView;
import com.jidekun.jdk.jdksmart.utils.PrefUtils;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;

/**
 * Created by JDK on 2016/5/15.
 */
public class TabDetailPager extends BaseMenuDetaiPager {
    private NewsBean.NewsMenuTab children;
    private LinearLayout ll;
    private RefreshListView lv;
    private NewsDetailViewPager vp;
    private LinearLayout vpll;
    private ArrayList<NewsTabBean.NewsData> newsDatas;
    private ArrayList<NewsTabBean.TopNews> topNewses;
    private NewsTabBean newsTabBean, moreNewsTabBean;
    private LVAda lvAda;
    private VPAda vpAda;
    //设置viewpager的轮播
    private Handler handler;

    public TabDetailPager(Activity activity, NewsBean.NewsMenuTab children) {
        super(activity);
        this.children = children;
        initData();
    }

    @Override
    public View initView() {
        ll = (LinearLayout) View.inflate(activity, R.layout.tabdetailpager_listview, null);
        lv = (RefreshListView) ll.findViewById(R.id.lv_detail);
        vpll = (LinearLayout) View.inflate(activity, R.layout.tabdetailpager_viewpager, null);
        vp = (NewsDetailViewPager) vpll.findViewById(R.id.vp_detail);
        //将viewpager的布局添加到listview中
        lv.addHeaderView(vpll);

        return ll;
    }


    @Override
    public void initData() {
        String url = GlobalConstants.SERVICE_URL + children.url;
        getData(url, false);
        lv.setOnRefreshLis(new RefreshListView.OnRefreshLis() {
            @Override
            public void onPullRefresh() {
                String url = GlobalConstants.SERVICE_URL + children.url;
                getData(url, false);
                //设置延时 效果明显

//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        try {
//                            sleep(3000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        mRootView.post(new Runnable() {
//                            @Override
//                            public void run() {
                //当加载完数据后,隐藏头布局
                lv.stopRefresh(true);
//                            }
//                        });
//                    }
//                }.start();

            }

            //加载更多
            @Override
            public void onLoaderMore() {
                String url = GlobalConstants.SERVICE_URL + newsTabBean.data.more;
                getData(url, true);
//设置延时 效果明显
//                new Thread() {
//                    @Override
//                    public void run() {
//                        super.run();
//                        try {
//                            sleep(3000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                        mRootView.post(new Runnable() {
//                            @Override
//                            public void run() {
                //当加载完数据后,隐藏脚布局
                lv.stopRefresh(true);
//                            }
//                        });
//
//                    }
//                }.start();

            }
        });


    }

    //从服务器上请求数据
    private void getData(String url, boolean more) {
        if (more) {
            // 获取加载更多的数据
            Net.getInstance(activity).getServiceData(url, NewsTabBean.class, new Net.OnGetNewsPagerNet() {
                @Override
                public void onGetData(Object bean) {

                    //获得返回的加载更多的bean的对象
                    moreNewsTabBean = (NewsTabBean) bean;
                    //获得加载更多的数据
                    ArrayList<NewsTabBean.NewsData> moreNewsDatas = moreNewsTabBean.data.news;
                    ArrayList<NewsTabBean.TopNews> moreTopNewses = moreNewsTabBean.data.topnews;
                    //将加载更多的数据添加到开始获得的数据中
                    newsDatas.addAll(moreNewsDatas);
                    topNewses.addAll(moreTopNewses);
                    //刷新数据
                    vpAda.notifyDataSetChanged();
                    lvAda.notifyDataSetChanged();

                }

                @Override
                public void onFailure() {

                }
            });
        } else {
            //请求服务器
            Net.getInstance(activity).getServiceData(url, NewsTabBean.class, new Net.OnGetNewsPagerNet() {
                @Override
                public void onGetData(Object bean) {
                    //获得返回的bean对象
                    newsTabBean = (NewsTabBean) bean;
                    newsDatas = newsTabBean.data.news;
                    topNewses = newsTabBean.data.topnews;

                    vpAda = new VPAda();
                    vp.setAdapter(vpAda);
                    lvAda = new LVAda();
                    lv.setAdapter(lvAda);
                    //开始轮播新闻
                    loop();
                    //设置listview条目点击事件监听
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //获得当前被点击条目的对象
                            TextView title = (TextView) view.findViewById(R.id.tv_title);
                            title.setTextColor(Color.GRAY);
                            //获得头布局的个数
                            int headerViewsCount = lv.getHeaderViewsCount();
                            NewsTabBean.NewsData newsData = newsDatas.get(position - headerViewsCount);
                            int id1 = newsData.id;
                            //获得已设置颜色的条目的id
                            String idCount = PrefUtils.getString(activity, "idindex", "");
                            //如果已经设置 则跳过
                            if (!idCount.contains(id1 + "")) {
                                idCount += id1 + ",";
                                PrefUtils.setString(activity, "idindex", idCount);
                            }
                            //跳转到新闻详情页
                            Intent intent = new Intent(activity, NewsDetailActivity.class);
                            intent.putExtra("url", newsData.url);
                            activity.startActivity(intent);
                        }
                    });
                }

                @Override
                public void onFailure() {

                }
            });
        }

    }

    /**
     * 自动切换新闻页
     */
    private void loop() {
        if (handler == null) {
            handler = new Handler() {
                public void handleMessage(android.os.Message msg) {
                    int currentItem = vp.getCurrentItem();
                    currentItem++;
                    if (currentItem > vpAda.getCount() - 1) {
                        currentItem = 0;// 如果已经到了最后一个页面,跳到第一页
                    }
                    vp.setCurrentItem(currentItem);
                    handler.sendEmptyMessageDelayed(0, 3000);// 继续发送延时3秒的消息,形成内循环 相当于无限循环
                }
            };
            // 保证启动自动轮播逻辑只执行一次
            handler.sendEmptyMessageDelayed(0, 3000);// 发送延时3秒的消息  启动一次
        }

        vp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //当手指按下时, 移除所有消息 停止新闻的轮播
                        handler.removeCallbacksAndMessages(null);
                        break;
                    case MotionEvent.ACTION_UP:
                        //当手指抬起时 启动轮播
                        handler.sendEmptyMessageDelayed(0, 3000);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        //当事件消失时 启动轮播
                        handler.sendEmptyMessageDelayed(0, 3000);
                        break;
                }
                return false;
            }
        });
    }

    //listview适配器
    class LVAda extends BaseAdapter {
        private BitmapUtils mBitmapUtils;

        public LVAda() {
            mBitmapUtils = new BitmapUtils(activity);
        }

        @Override
        public int getCount() {
            return newsDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return newsDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = View.inflate(activity, R.layout.tabdetailpager_listview_item, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_icon);
            //标题
            TextView title = (TextView) view.findViewById(R.id.tv_title);
            //时间
            TextView date = (TextView) view.findViewById(R.id.tv_date);
            NewsTabBean.NewsData newsData = newsDatas.get(position);
            //判断是否该条目信息已读
            String idCount = PrefUtils.getString(activity, "idindex", "");
            int id = newsData.id;


            //   获得标题
            String title1 = newsData.title;
            //  获得时间
            String date1 = newsData.pubdate;
            title.setText(title1);
            date.setText(date1);
            //如果已读 将字体颜色设置为灰色
            if (idCount.contains(id + "")) {
                title.setTextColor(Color.GRAY);
            }
            mBitmapUtils.display(iv, newsData.listimage);
            return view;
        }
    }

    //viewpager适配器
    class VPAda extends PagerAdapter {
        private BitmapUtils mBitmapUtils;

        public VPAda() {
            mBitmapUtils = new BitmapUtils(activity);
        }

        @Override
        public int getCount() {
            return topNewses.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv = new ImageView(activity);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            String path = topNewses.get(position).topimage;
            mBitmapUtils.display(iv, path);
            container.addView(iv);
            return iv;
        }
    }
}
