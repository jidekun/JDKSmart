package com.jidekun.jdk.jdksmart.fragament;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jidekun.jdk.jdksmart.R;
import com.jidekun.jdk.jdksmart.activity.HomeActivity;
import com.jidekun.jdk.jdksmart.base.BaseFragment;
import com.jidekun.jdk.jdksmart.base.BasePager;
import com.jidekun.jdk.jdksmart.base.iml.NewsPager;
import com.jidekun.jdk.jdksmart.bean.NewsBean;
import com.jidekun.jdk.jdksmart.ui.NoSldingViewPager;

import java.util.ArrayList;

/**
 * Created by JDK on 2016/5/12.
 * 左侧侧滑栏
 */
public class LeftMenutFragment extends BaseFragment {

    private View view;
    private ListView lv;
    private ArrayList<String> title;
    private ListViewAdapter listViewAdapter;
    private ContentFragment contentFragment;
    private NewsPager newsPager;
    private SlidingMenu slidingMenu;
    public TextView tv;
    private int currenItem;

    public LeftMenutFragment() {
    }

    @Override
    public View initView() {

        view = View.inflate(mActivity, R.layout.fragment_left_menu, null);
        lv = (ListView) view.findViewById(R.id.lv_leftmenufrgment);
        return view;
    }

    @Override
    public void initDate() {
        currenItem = 0;
    }

    /**
     * 由activity上的Viewpager页面设置新闻页数据
     */
    public void addNewsData(ArrayList<NewsBean.NewsMenuBean> data) {
        //获得NewsPager对象
        HomeActivity homeActivity = (HomeActivity) mActivity;
        //slidingmenu对象
        slidingMenu = homeActivity.getSlidingMenu();
        //主界面碎片对象
        ContentFragment contentFragment = homeActivity.getContentFragment();

        ArrayList<BasePager> list = contentFragment.list;
        newsPager = (NewsPager) list.get(1);

        //处理传递过来的数据

        title = new ArrayList<>();
        //从bean中获得标题数据,并添加到集合中
        for (NewsBean.NewsMenuBean tempData : data) {
            title.add(tempData.title);
        }

        listViewAdapter = new ListViewAdapter();
        lv.setAdapter(listViewAdapter);
        //设置item的点击事件
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //调用newspager 中的方法设置不同的主题
                //设置当前哪个条目被点击
                currenItem = position;
                //刷新适配器
                listViewAdapter.notifyDataSetChanged();
                newsPager.setViewData(position);
                //在条目点击之后 关闭侧滑栏
                slidingMenu.toggle();
            }
        });
    }

    class ListViewAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return title.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LinearLayout ll = (LinearLayout) View.inflate(mActivity, R.layout.leftmenu_listview_item, null);
            final TextView tv = (TextView) ll.findViewById(R.id.tv_leftmenu_item);
            tv.setText(title.get(position));
            if (currenItem == position) {
                tv.setTextColor(Color.RED);
            } else {
                tv.setTextColor(Color.WHITE);
            }
            return ll;
        }
    }
}
