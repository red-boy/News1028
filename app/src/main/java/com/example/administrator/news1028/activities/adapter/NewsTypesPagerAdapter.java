package com.example.administrator.news1028.activities.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.administrator.news1028.activities.entity.NewsTypes;
import com.example.administrator.news1028.activities.fragment.NewsListFragment;

/**
 * @author Administrator.
 * @version .
 * @time .
 */

public class NewsTypesPagerAdapter extends FragmentPagerAdapter {

    NewsTypes mNewsTypes;
    public NewsTypesPagerAdapter(FragmentManager fm, NewsTypes newsTypes) {
        super(fm);
        this.mNewsTypes=newsTypes;

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mNewsTypes.tList.get(position).tname;
    }

    //持有着viewpager要显示的视图的那个碎片对象
    @Override
    public Fragment getItem(int position) {
        return NewsListFragment.newInstance(mNewsTypes.tList.get(position).tid,mNewsTypes.tList.get(position).tname);
    }

    @Override
    public int getCount() {
        return mNewsTypes.tList==null?0:mNewsTypes.tList.size();
    }
}
