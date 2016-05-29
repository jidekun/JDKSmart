package com.jidekun.jdk.jdksmart.base.menu;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jidekun.jdk.jdksmart.R;
import com.jidekun.jdk.jdksmart.base.BaseMenuDetaiPager;
import com.jidekun.jdk.jdksmart.bean.PhotoBean;
import com.jidekun.jdk.jdksmart.global.GlobalConstants;
import com.jidekun.jdk.jdksmart.net.Net;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


/**
 * Created by JDK on 2016/5/14.
 */
public class PhotosMenuDetaiPager extends BaseMenuDetaiPager {


    private boolean isList = true;
    //新闻页面下的数据集合

    private GridView gv;
    public PhotoBean photoBean;
    private GVAda gvAda;
    //private ArrayList<PhotoBean.PhotoNews> news;

    public PhotosMenuDetaiPager(Activity activity) {
        super(activity);
        initData();
    }

    @Override
    public View initView() {

        LinearLayout ll = (LinearLayout) View.inflate(activity, R.layout.photomenudetailpager, null);
        gv = (GridView) ll.findViewById(R.id.gv_photomenu);
        return ll;
    }

    @Override
    public void initData() {
       /* Net.getInstance(activity).getServiceData(GlobalConstants.PHOTO_URL, PhotoBean.class, new Net.OnGetNewsPagerNet() {
            @Override
            public void onGetData(Object bean) {
                photoBean = (PhotoBean) bean;
                GVAda  gvAda = new GVAda();
                gv.setAdapter(gvAda);
            }

            @Override
            public void onFailure() {

            }
        });*/
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, GlobalConstants.PHOTO_URL, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                Gson gson = new Gson();
                photoBean = gson.fromJson(responseInfo.result, PhotoBean.class);
                GVAda gvAda = new GVAda();
                gv.setAdapter(gvAda);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }

    class GVAda extends BaseAdapter {
        private BitmapUtils bitmapUtils;

        public GVAda() {
            if (bitmapUtils == null) {
                bitmapUtils = new BitmapUtils(activity);
                //设置默认图片
                bitmapUtils.configDefaultLoadingImage(R.drawable.pic_item_list_default);
            }
        }

        @Override
        public int getCount() {
            return photoBean.data.news.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(activity, R.layout.photomenuitem, null);
            ImageView iv = (ImageView) view.findViewById(R.id.iv_photomenu);
            TextView title = (TextView) view.findViewById(R.id.tv_photomenu_title);
            PhotoBean.PhotoNews photoNews = photoBean.data.news.get(position);
            String title1 = photoNews.title;
            String listimage = photoNews.listimage;
            bitmapUtils.display(iv, listimage);
            return view;
        }
    }

}
