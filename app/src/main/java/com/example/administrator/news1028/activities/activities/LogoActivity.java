package com.example.administrator.news1028.activities.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.news1028.R;
import com.example.administrator.news1028.activities.biz.Xhttp;
import com.example.administrator.news1028.activities.common.IgnoreTypes;
import com.example.administrator.news1028.activities.entity.NewsTypes;

import java.util.ArrayList;
import java.util.List;

public class LogoActivity extends AppCompatActivity implements Xhttp.NewsTypeListener {
    private NewsTypes newsType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        Xhttp.getNewsTypes(this);
    }

    public static void start(Context context) {//starter
        Intent starter = new Intent(context, LogoActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void onSuccess(NewsTypes newsType) {
        //-MainActivity----HomeFragment----NewTyepAdapter--设置标题
        if (newsType != null) {
            this.newsType = newsType;
            //排除那些没有数据结果的分类
            ignore(newsType);
        }

    }


    @Override
    public void onFinish() {
        MainActivity.start(this, newsType);
        finish();
    }

    private void ignore(NewsTypes newsType) {
        List<NewsTypes.SubName> tobeDeleted = new ArrayList<>();
        for (int i = 0; i < IgnoreTypes.TYPES.length; i++) {
            for (int j = 0; j <newsType.tList.size(); j++) {
                if (IgnoreTypes.TYPES[i].equals(newsType.tList.get(j).tname)) {
                    tobeDeleted.add(newsType.tList.get(j));
                }
            }
        }
        newsType.tList.removeAll(tobeDeleted);
    }
}
