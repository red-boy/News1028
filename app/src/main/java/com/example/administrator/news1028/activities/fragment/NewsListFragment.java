package com.example.administrator.news1028.activities.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.news1028.R;
import com.example.administrator.news1028.activities.adapter.NetEaseAdapter1;
import com.example.administrator.news1028.activities.base.BaseFragment;
import com.example.administrator.news1028.activities.biz.Xhttp;
import com.example.administrator.news1028.activities.common.CommonUrls;
import com.example.administrator.news1028.activities.entity.NetEase;
import com.example.administrator.news1028.activities.view.RecycleViewDivider;

import java.util.List;
import java.util.TimerTask;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsListFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener, NetEaseAdapter1.OnItemCLickListener {

    private static final String KEY_TID = "param1";
    private static final String KEY_TNAME = "param2";

    Handler handler;
    NetEaseAdapter1 netEaseAdapter1;
    private LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());


    @BindView(R.id.recyclerView1)
    RecyclerView mRecyclerView1;
    @BindView(R.id.refresh)
    SwipeRefreshLayout mRefresh;

    private String tid, tname;
    private OnFragmentInteractionListener mListener;

    private Xhttp.OnSuccessListener listener = new Xhttp.OnSuccessListener() {
        @Override
        public void setNewLists(List<NetEase> netEases) {
            Log.d("xHttp", "main ");

            netEaseAdapter1 = new NetEaseAdapter1(netEases);//监听里有传入集合
            mRecyclerView1.setAdapter(netEaseAdapter1);

            netEaseAdapter1.setOnItemClickListener(NewsListFragment.this);

            //必须要设置一个布局管理器:三种类型1.listview,2.gridview,3.瀑布流
            mRecyclerView1.setLayoutManager(new LinearLayoutManager(getContext()));//1.
            //            recyclerView1.setLayoutManager(new StaggeredGridLayoutManager(4,StaggeredGridLayoutManager.VERTICAL));//3.
            //            recyclerView1.setLayoutManager(new GridLayoutManager(MainActivity.this,3,GridLayoutManager.HORIZONTAL,true));//2.
            //添加分割线
            mRecyclerView1.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.HORIZONTAL));
        }
    };

    public NewsListFragment() {
        // Required empty public constructor需要空参数的构造方法
    }


    public static NewsListFragment newInstance(String tid, String tname) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TID, tid);
        args.putString(KEY_TNAME, tname);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            tid = getArguments().getString(KEY_TID);
            tname = getArguments().getString(KEY_TNAME);
        }
    }


    @Override
    protected void initData() {
        handler = new Handler();

        mRefresh.setOnRefreshListener(this);//SwipeRefreshLayout谷歌提供的上拉控件，设置监听事件，上拉加载数据

        RecyclerView.OnScrollListener ttt = new RecyclerView.OnScrollListener() {//RecyclerView下拉界面加载数据
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, final int newState) {
                if (!mRefresh.isRefreshing()) {

                    int lastItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition == netEaseAdapter1.getItemCount() - 1) {
                        netEaseAdapter1.setCurrentState(NetEaseAdapter1.FOOTER_PULLING);//加载状态

                        Xhttp.getNewsList(CommonUrls.getUrl(tid), tid, new Xhttp.OnSuccessListener() {
                            @Override
                            public void setNewLists(List<NetEase> netEases) {
                                netEaseAdapter1.addDataList(netEases);
                                netEaseAdapter1.notifyDataSetChanged();
                                if (netEases.size() == 0) {
                                    netEaseAdapter1.setCurrentState(NetEaseAdapter1.FOOTER_PULL_NO_DATA);
                                } else {
                                    netEaseAdapter1.setCurrentState(NetEaseAdapter1.FOOTER_PULL_FINISHED);
                                }
                            }
                        });

                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        };

        mRecyclerView1.addOnScrollListener(ttt);//RecyclerView下拉界面加载数据
        Xhttp.getNewsList(CommonUrls.getUrl(tid), tid, listener);//先从网络服务器获取数据资源.需创建一个listener

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_list;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onRefresh() {
        Runnable runnable = new TimerTask() {
            @Override
            public void run() {

                NetEase netEase = netEaseAdapter1.getDataList().get(1);
                netEaseAdapter1.addData(1, netEase);
                netEaseAdapter1.notifyItemInserted(1);
                Toast.makeText(getContext(), "加载数据完毕", Toast.LENGTH_SHORT).show();
                mRefresh.setRefreshing(false);

            }
        };
        handler.postDelayed(runnable, 1000);
    }

    @Override
    public void Click(int position) {

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
