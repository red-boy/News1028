package com.example.administrator.news1028.activities.adapter;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.administrator.news1028.R;
import com.example.administrator.news1028.activities.entity.NetEase;
import com.example.administrator.news1028.activities.utils.XImageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * @author Administrator.
 * @version .
 * @time .
 */

public class NetEaseAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<NetEase> dataList;

    private static final int VIEW_VIEWAPGER = 153;
    private static final int VIEW_LONG_IMAGE = 168;
    private static final int VIEW_ONE_IMAGE = 0;
    private static final int VIEW_THREE_IMAGE = 429;
    private static final int VIEW_FOOTER = 552;

    public List<NetEase> getDataList() {
        return dataList;
    }

    public void setDataList(List<NetEase> dataList) {
        this.dataList = dataList;
    }

    public void addData(NetEase netEase) {
        dataList.add(netEase);
    }

    public void addData(int position, NetEase netEase) {
        dataList.add(position, netEase);
    }

    public void addDataList(List<NetEase> netEaseList) {
        if (netEaseList == null) {
            Log.d(TAG, "addDataList:集合不能为空 ");
            return;
        }
        dataList.addAll(netEaseList);
    }


    public NetEaseAdapter1(List<NetEase> dataList) {
        this.dataList = dataList;
    }


    //根据视图类型viewType决定视图的布局，使用哪个holder创建视图
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case VIEW_VIEWAPGER://先创建对应数目的静态常量
                view = View.inflate(parent.getContext(), R.layout.layout_item_vp, null);
                holder = new ViewPagerHolder(view);
                break;
            case VIEW_LONG_IMAGE:
                view = View.inflate(parent.getContext(), R.layout.layout_item_one_head, null);
                holder = new LongImageHolder(view);
                break;
            case VIEW_ONE_IMAGE:
                view = View.inflate(parent.getContext(), R.layout.layout_item_one_img, null);
                holder = new OneImageHolder(view);
                break;
            case VIEW_THREE_IMAGE:
                view = View.inflate(parent.getContext(), R.layout.layout_item_three_img, null);
                holder = new ThreeImageHolder(view);
                break;
            case VIEW_FOOTER:
                view = View.inflate(parent.getContext(), R.layout.footer, null);
                holder = new FooterHolder(view);
                break;

        }
        return holder;
    }

    //通过位置确定当前的视图类型
    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return dataList.get(position).ads == null ? VIEW_LONG_IMAGE : VIEW_VIEWAPGER;
        } else if (position < dataList.size()) {
            return dataList.get(position).imgextra == null ? VIEW_ONE_IMAGE : VIEW_THREE_IMAGE;
        } else {
            return VIEW_FOOTER;
        }
    }

    //给每个holder设置绑定对应的数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof OneImageHolder) {
            initOneImageView((OneImageHolder) holder, dataList.get(position));//加载页面数据的方法。强转类型
        } else if (holder instanceof ViewPagerHolder) {
            initViewPagerView((ViewPagerHolder) holder, dataList.get(position));
        } else if (holder instanceof LongImageHolder) {
            initLongImageView((LongImageHolder) holder, dataList.get(position));
        } else if (holder instanceof ThreeImageHolder) {
            initThreeImageView((ThreeImageHolder) holder, dataList.get(position));
        } else if (holder instanceof FooterHolder) {
            initFooterView();

        } else {

        }

        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.Click(position);
                }
            });
        }

    }

    private OnItemCLickListener onItemClickListener;

    public void setOnItemClickListener(OnItemCLickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    public interface OnItemCLickListener {//创建RecyclerView的监听

        void Click(int position);
    }

    //三张横向图片的设置
    private void initThreeImageView(ThreeImageHolder holder, NetEase netEase) {
        holder.tvTitle.setText(netEase.title);
        holder.tvFollow.setText(netEase.replyCount + "");
        XImageUtil.display(holder.img1, netEase.imgsrc);
        XImageUtil.display(holder.img2, netEase.imgextra.get(0).imgsrc);
        XImageUtil.display(holder.img3, netEase.imgextra.get(0).imgsrc);
    }

    //上拉加载分状态去初始化：const快捷生成静态常量
    public static final int FOOTER_IDLE = 874;
    public static final int FOOTER_PULLING = 483;
    public static final int FOOTER_PULL_FINISHED = 306;
    public static final int FOOTER_PULL_NO_DATA = 147;


    private int currentState;

    public void setCurrentState(int currentState) {//set方法，方便外部使用
        this.currentState = currentState;
    }

    //footer上拉加载的设置
    private void initFooterView() {
    }

    private void initLongImageView(LongImageHolder holder, NetEase netEase) {
        XImageUtil.display(holder.imgHead, netEase.imgsrc);
        holder.tvTitle.setText(netEase.title);
    }

    /**
     * 图片左右滑动：
     */
    private void initViewPagerView(final ViewPagerHolder holder, NetEase netEase) {
        NetEase_ViewPagerAdapter adapter = new NetEase_ViewPagerAdapter(netEase.ads);
        holder.vpager.setAdapter(adapter);
        if (holder.llLayout.getChildCount() == 0) {//.getChildCount()得到一个正整数代表子控件的数量
            for (int i = 0; i < netEase.ads.size(); i++) {
                ImageView img = new ImageView(holder.llLayout.getContext());//通过布局控件轻易得到上下文
                img.setImageResource(R.drawable.adware_style_default);
                holder.llLayout.addView(img);//遍历，最后必须得把点的图片加入父部控件llLayout

                //LayoutParams相当于一个Layout的信息包
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) img.getLayoutParams();
                layoutParams.rightMargin = 5;
                layoutParams.leftMargin = 5;
            }
        }
        ((ImageView) holder.llLayout.getChildAt(0)).setImageResource(R.drawable.adware_style_selected);
        //实现左右滑动。setCurrentItem...将是一个平滑的动画当前项之间的过渡和指定的项。
        holder.vpager.setCurrentItem(Integer.MAX_VALUE / 2 - ((Integer.MAX_VALUE / 2) % netEase.ads.size()));
        //翻页变动监听：让点跟随变动
        holder.vpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < holder.llLayout.getChildCount(); i++) {
                    ImageView img = (ImageView) holder.llLayout.getChildAt(i);
                    img.setImageResource(R.drawable.adware_style_default);
                }
                ImageView im = (ImageView) holder.llLayout.getChildAt(position % holder.llLayout.getChildCount());
                im.setImageResource(R.drawable.adware_style_selected);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 左边大图：
     */
    private void initOneImageView(OneImageHolder holder, NetEase netEase) {
        XImageUtil.display(holder.imgLeft, netEase.imgsrc);
        holder.tvTitle.setText(netEase.title);
        holder.tvFollow.setText(netEase.replyCount + "");
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public static class OneImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_left)
        ImageView imgLeft;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_follow)
        TextView tvFollow;

        public OneImageHolder(View itemView) {//重写父类的构造方法
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ThreeImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.img1)
        ImageView img1;
        @BindView(R.id.img2)
        ImageView img2;
        @BindView(R.id.img3)
        ImageView img3;
        @BindView(R.id.tv_follow)
        TextView tvFollow;

        public ThreeImageHolder(View itemView) {//重写父类的构造方法
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class ViewPagerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.vpager)
        ViewPager vpager;
        @BindView(R.id.ll_layout)
        LinearLayout llLayout;

        public ViewPagerHolder(View itemView) {//重写父类的构造方法
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class LongImageHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.img_head)
        ImageView imgHead;

        public LongImageHolder(View itemView) {//重写父类的构造方法
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class FooterHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progressBar1)
        ProgressBar progressBar1;
        @BindView(R.id.textView1)
        TextView textView1;

        public FooterHolder(View itemView) {//重写父类的构造方法
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }


}
