package com.example.administrator.news1028.activities.fragment;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.administrator.news1028.R;
import com.example.administrator.news1028.activities.activities.MainActivity;
import com.example.administrator.news1028.activities.adapter.NewsTypesPagerAdapter;
import com.example.administrator.news1028.activities.base.BaseFragment;
import com.example.administrator.news1028.activities.entity.NewsTypes;
import com.viewpagerindicator.TabPageIndicator;

import butterknife.BindView;

/**
 * @author Administrator.
 * @version .首页碎片:新闻
 * @time .
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.indicator)
    TabPageIndicator mIndicator;
    @BindView(R.id.pager)
    ViewPager mPager;

    private NewsTypesPagerAdapter mNewsTypesPagerAdapter;

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        NewsTypes newsTypes = (NewsTypes) bundle.getSerializable(MainActivity.KEY_TYPELIST);
        mNewsTypesPagerAdapter = new NewsTypesPagerAdapter(getFragmentManager(),newsTypes);
        mPager.setAdapter(mNewsTypesPagerAdapter);
        mIndicator.setViewPager(mPager);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.layout_home;
    }

    public static HomeFragment newInstance(Bundle bundle) {
        HomeFragment ho = new HomeFragment();
        ho.setArguments(bundle);
        return ho;
    }


}
