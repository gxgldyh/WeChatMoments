package com.chat.dyh;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.chat.dyh.adapter.MomentAdapter;
import com.chat.dyh.modle.TweetItmeInfo;
import com.chat.dyh.presenter.TweetContract;
import com.chat.dyh.presenter.TweetInterfaceImpl;
import com.chat.dyh.view.DivItemDecoration;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements TweetContract.View{

    private TweetInterfaceImpl presenter;
    private SuperRecyclerView recyclerView;
    private LinearLayoutManager layoutManager;


    private final static int TYPE_PULLREFRESH = 1;//刷新
    private final static int TYPE_UPLOADREFRESH = 2;//加载更多
    private int pageIndex = 1;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;

    private MomentAdapter momentAdapter;
    private List<TweetItmeInfo> mAllDatas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new TweetInterfaceImpl(this);

        initView();
        //实现自动下拉刷新功能
        recyclerView.getSwipeToRefresh().post(new Runnable(){
            @Override
            public void run() {
                recyclerView.setRefreshing(true);//执行下拉刷新的动画
                refreshListener.onRefresh();//执行数据加载操作
            }
        });
    }
    private void initView(){
        recyclerView =  findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DivItemDecoration(2, true));
        recyclerView.getMoreProgressView().getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;

        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                if (edittextbody.getVisibility() == View.VISIBLE) {
//                    updateEditTextBodyVisible(View.GONE, null);
//                    return true;
//                }
                return false;
            }
        });

        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        presenter.loadData(pageIndex,TYPE_PULLREFRESH);
                    }
                }, 2000);
            }
        };
        recyclerView.setRefreshListener(refreshListener);

        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    Glide.with(MainActivity.this).resumeRequests();
                }else{
                    Glide.with(MainActivity.this).pauseRequests();
                }

            }
        });
        momentAdapter = new MomentAdapter(this);
        momentAdapter.setmTweetPresenter(presenter);
        momentAdapter.setDatas(mAllDatas);
        recyclerView.setAdapter(momentAdapter);

    }
    @Override
    public void showLoading(String msg) {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String errorMsg) {

    }

    @Override
    public void update2loadData(int loadType, final int pageIndex, List<TweetItmeInfo> datas) {
        if (loadType == TYPE_PULLREFRESH){
            recyclerView.setRefreshing(false);
            momentAdapter.setDatas(datas);
        }else if(loadType == TYPE_UPLOADREFRESH){
            momentAdapter.getDatas().addAll(datas);
        }
        momentAdapter.notifyDataSetChanged();

        if(momentAdapter.getDatas().size()<45 + 1){
            recyclerView.setupMoreListener(new OnMoreListener() {
                @Override
                public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            presenter.loadData(pageIndex+1,TYPE_UPLOADREFRESH);
                        }
                    }, 2000);

                }
            }, 1);
        }else{
            recyclerView.removeMoreListener();
            recyclerView.hideMoreProgress();
        }
    }
}
