package com.example.administrator.news1028.activities.biz;

import android.util.Log;
import android.widget.Toast;

import com.example.administrator.news1028.activities.app.GlobalApp;
import com.example.administrator.news1028.activities.common.CommonUrls;
import com.example.administrator.news1028.activities.entity.NetEase;
import com.example.administrator.news1028.activities.entity.NewsTypes;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator.
 * @version .
 * @time .    xutils第三方工具的运用
 */

public class Xhttp {

    private static final String TAG = "xhttp";

    public static void getNewsList(String url, final String tid, final OnSuccessListener listener) {//参数tid：网址的title标题
        RequestParams entity = new RequestParams(url);//RequestParams网络请求参数实体,新建对象entity

        Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {
            @Override//回调接口及方法,泛型是我们需要的返回的数据类型即解析出的字符串
            public void onSuccess(String result) {
                Log.d("xHttp", "onSuccess: ");
                Gson gson = new Gson();
                ArrayList<NetEase> list = new ArrayList<>();
                try {
                    JSONObject obj = new JSONObject(result);
                    JSONArray array = obj.getJSONArray(tid);
                    for (int i = 0; i < array.length(); i++) {
                        NetEase netEase = gson.fromJson(array.get(i).toString(), NetEase.class);//fromJson方法两个参数
                        list.add(netEase);
                    }
                    //解析完成再使用监听接口回调传入数据
                    if (listener != null) {
                        listener.setNewLists(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d("xHttp", "onError: ");
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d("xHttp", "onCancelled: ");
            }

            @Override
            public void onFinished() {
                Log.d("xHttp", "onFinished: ");
            }
        };
        x.http().get(entity, callback);//网络访问获取数据

    }

    //接口
    public interface OnSuccessListener {
        void setNewLists(List<NetEase> netEases);
    }

    public static void getNewsTypes(final NewsTypeListener listener) {

        RequestParams entity = new RequestParams(CommonUrls.NEWS_TYPE);
        Callback.CommonCallback<String> callback = new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                NewsTypes newsType = gson.fromJson(result, NewsTypes.class);

                if (listener!=null){
                    listener.onSuccess(newsType);
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "onError: ");
                Toast.makeText(GlobalApp.getGlobalApp(), "error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d(TAG, "onCancelled: ");
            }

            @Override
            public void onFinished() {
                Log.d(TAG, "onFinished: ");
                if (listener!=null){
                    listener.onFinish();
                }
            }
        };
        x.http().get(entity, callback);
    }

    public static interface  NewsTypeListener{//给LogoActivity提供获取集合的接口方法
        void onSuccess(NewsTypes newsType);
        void onFinish();
    }
}
