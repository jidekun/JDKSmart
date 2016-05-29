package com.jidekun.jdk.jdksmart.net;

import android.app.Activity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jidekun.jdk.jdksmart.utils.CacheUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;


/**
 * Created by JDK on 2016/5/14.
 * 访问网络的类,请求服务器数据,并解析Json 获得对应的JavaBean
 */
public class Net  {
    private static Activity mActivity;
    private static Net ourInstance = new Net();
    private OnGetNewsPagerNet newsPagerNet;
    private String path;
    private Class<?> cls;
    /**
     * 获得类的实例
     */
    public static Net getInstance(Activity activity) {
        mActivity = activity;
        return ourInstance;
    }
    /**
     * 定义接口
     */
    public interface OnGetNewsPagerNet {
        void onGetData(Object bean);
        void onFailure();
    }
    /**
     * 访问服务器获得数据
     * 回调接口方法中返回的对象需要手动强转为 传入字节码的对象类型
     * @param path         服务器路径
     * @param cls          JavaBean的字节码对象
     * @param newsPagerNet 网络访问的回调
     */
    public void getServiceData( String path, Class<?> cls,OnGetNewsPagerNet newsPagerNet) {

        this.newsPagerNet = newsPagerNet;
        this.path = path;
        this.cls = cls;
        //检查缓存
        String cache = CacheUtils.getCache(mActivity, path);

        if (cache != null) {

//            Object oldBean = processData(cache);
//
//            //设置回调
//            if (newsPagerNet != null) {
//                newsPagerNet.onGetData(oldBean);
//            }
        }
        NetRequestCallBack netRequestCallBack = new NetRequestCallBack();
        //使用XUtils网络请求
        HttpUtils utils = new HttpUtils();

        utils.send(HttpRequest.HttpMethod.GET, path, netRequestCallBack);
    }

    /**
     * 使用Gson解析
     */
    public Object processData(String result) {
        Gson gson = new Gson();
        Object bean = gson.fromJson(result, cls);
        return bean;
    }
   private class NetRequestCallBack extends RequestCallBack<String> {
        @Override
        public void onSuccess(ResponseInfo<String> responseInfo) { // 当请求成功
            // 获取服务器返回结果
            String result = responseInfo.result;
            //将结果存入缓存
           // CacheUtils.setCache(mActivity, path, result);
            Object bean = processData(result);
            //设置回调
            if (newsPagerNet != null) {
                newsPagerNet.onGetData(bean);
            }
        }

        @Override
        public void onFailure(HttpException error, String msg) {//当请求失败
            // 请求失败,打印失败原因
            error.printStackTrace();
            //弹出toast
            Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();

            if (newsPagerNet != null) {
                newsPagerNet.onFailure();
            }
        }
    }

}
