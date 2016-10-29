package com.example.administrator.news1028.activities.common;

/**
 * Created by Administrator on 2016/10/29.
 */

public class CommonUrls {
    private static final String BASE_URL = "http://c.m.163.com/";
    /**
     * 网易新闻的分类获取链接
     */
    public static final String NEWS_TYPE = BASE_URL + "nc/topicset/android/subscribe/manage/listspecial.html";

    public static String getUrl(String tid) {
        return BASE_URL + "nc/article/list/" + tid + "/0-20.html";
    }

}


