package com.example.administrator.news1028.activities.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Administrator.
 * @version .
 * @time .
 */

public class GuideActivityAdapter extends PagerAdapter {
private List<View> mList;

    public GuideActivityAdapter(List<View> list) {
        mList = list;
    }

    public void  addView(View view) {
        mList.add(view);
    }

    public List<View> getList() {
        return mList;
    }

    public void setList(List<View> list) {
        mList = list;
    }


    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {

        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=mList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }
}
