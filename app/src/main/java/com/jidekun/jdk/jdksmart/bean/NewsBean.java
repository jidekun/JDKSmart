package com.jidekun.jdk.jdksmart.bean;

import java.util.ArrayList;

/**
 * Created by JDK on 2016/5/14.
 */

public class NewsBean {
    public ArrayList<NewsMenuBean> data;
    public ArrayList<String> extend;
    public int retcode;

    public class NewsMenuBean {
        public ArrayList<NewsMenuTab> children;
        public String id;
        public String title;
        public int type;
        public String url;
        public String url1;

        @Override
        public String toString() {
            return "NewsMenuBean [children=" + children + ", title=" + title
                    + "]";
        }
    }

    public class NewsMenuTab {
        public String id;
        public String title;
        public int type;
        public String url;

        @Override
        public String toString() {
            return "NewsMenuTab [id=" + id + ", title=" + title + "]";
        }
    }

    @Override
    public String toString() {
        return "NewsBean [data=" + data + ", retcode=" + retcode + "]";
    }
}

/*
public class NewsBean {

    public ArrayList<NewsMenuBean> data;
    public ArrayList<String> extend;
    public int retcode;

    public class NewsMenuBean {
        public ArrayList<NewsMenuTab> children;
        public String id;
        public String title;
        public int type;
        public String url;
        public String url1;

        @Override
        public String toString() {
            return "NewsMenuBean [children=" + children + ", title=" + title
                    + "]";
        }
    }

    public class NewsMenuTab {
        public String id;
        public String title;
        public int type;
        public String url;

        @Override
        public String toString() {
            return "NewsMenuTab [id=" + id + ", title=" + title + "]";
        }
    }

    @Override
    public String toString() {
        return "NewsBean [data=" + data + ", retcode=" + retcode + "]";
    }
}*/
