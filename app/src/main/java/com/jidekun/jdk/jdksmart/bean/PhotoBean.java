package com.jidekun.jdk.jdksmart.bean;

import java.util.ArrayList;

/**
 * Created by JDK on 2016/5/18.
 * 组图的javabean
 */
public class PhotoBean {

    public PhotosData data;

    public class PhotosData {
        public ArrayList<PhotoNews> news;
    }

    public class PhotoNews {
        public int id;
        public String listimage;
        public String title;
    }
}
