package com.advanced.demo.retrofit;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

/**
 * @author by morton_ws on 2017/10/27.
 */

public class RetrofitRequestActivity extends BaseActivity {

    private RetrofitRestClient mRetrofitClient;
    private ProgressDialog mProgressDialog;
    private RecyclerView mMovieList;
    private DoubanMovieAdapter mMovieAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mMovieRefresh;

    private int mMovieStart = 0;
    private int mMovieCount = 20;

    @Override
    protected void initView() {
        super.initView();
        mMovieList = (RecyclerView) findViewById(R.id.movie_list);
        mMovieRefresh = (SwipeRefreshLayout) findViewById(R.id.movie_refresh);
    }

    @Override
    protected void initPages() {
        super.initPages();
        mMovieRefresh.setColorSchemeResources(R.color.holo_red_light, R.color.holo_blue_bright, R.color.holo_green_light, R.color.holo_orange_light);
        mRetrofitClient = new RetrofitRestClient();
        mRetrofitClient.createClient();
        mProgressDialog = new ProgressDialog(mContext);

        mMovieAdapter = new DoubanMovieAdapter(mContext);
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mMovieList.setAdapter(mMovieAdapter);
        mMovieList.setLayoutManager(mLayoutManager);
        mMovieList.setItemAnimator(new DefaultItemAnimator());

        mProgressDialog.show();
        mRetrofitClient.request(mMovieStart, mMovieCount);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mRetrofitClient.addRequestListener(new IRetrofitRequestListener() {
            @Override
            public void onSuccess(MovieResponse data) {
                onAfter();
                if (data != null && data.subjects != null) {
                    if (mMovieStart == 0) {
                        mMovieAdapter.clearData();
                    }
                    mMovieAdapter.addMovieData(data.subjects);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                onAfter();
            }

            @Override
            public void onError(String error) {
                onAfter();
            }

            private void onAfter() {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                if (mMovieRefresh.isRefreshing()) {
                    mMovieRefresh.setRefreshing(false);
                }
            }
        });
        mMovieRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mMovieStart = 0;
                mMovieCount = 20;
                mRetrofitClient.request(mMovieStart, mMovieCount);
            }
        });
        mMovieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                    int adapterLastItemPosition = mMovieAdapter.getItemCount() - 1;
                    if (lastVisibleItemPosition == adapterLastItemPosition) {
                        mMovieStart = mMovieStart + mMovieCount;
                        Toast.makeText(mContext, "加载ing...", Toast.LENGTH_SHORT).show();
                        mRetrofitClient.request(mMovieStart, mMovieCount);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        findViewById(R.id.movie_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItemPosition <= 20) {
                    mMovieList.smoothScrollToPosition(0);
                } else {
                    mMovieList.scrollToPosition(20);
                    mMovieList.smoothScrollToPosition(0);
                }
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_retrofit_request;
    }
}
