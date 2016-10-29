package com.example.administrator.news1028.activities.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.news1028.R;
import com.example.administrator.news1028.activities.entity.NetEase;
import com.example.administrator.news1028.activities.utils.XImageUtil;

import java.util.List;

/**
 * PagerAdapter适配器要有四个重写的方法
 *
 * @author Administrator.图片轮播效果，不可自动
 * @version .
 * @time .
 */

public class NetEase_ViewPagerAdapter extends PagerAdapter {
    private List<NetEase.Ad> list;

    public NetEase_ViewPagerAdapter(List<NetEase.Ad> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;//可让其左右滑动
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=View.inflate(container.getContext(), R.layout.layout_item_one_head,null);
        ImageView imageView= (ImageView) view.findViewById(R.id.img_head);
        TextView textView= (TextView) view.findViewById(R.id.tv_title);
        XImageUtil.display(imageView,list.get(position%list.size()).imgsrc);//get(position%list.size())
        textView.setText(list.get(position%list.size()).title);
        container.addView(view);//必须要加入视图容器
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
