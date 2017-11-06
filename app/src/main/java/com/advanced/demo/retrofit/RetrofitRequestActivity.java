package com.advanced.demo.retrofit;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

import java.util.List;

/**
 * @author by morton_ws on 2017/10/27.
 */

public class RetrofitRequestActivity extends BaseActivity {

    private final static int MOVIE_REQUEST_COUNT = 20;
    private RetrofitRestClient mRetrofitClient;
    private ProgressDialog mProgressDialog;
    private RecyclerView mMovieList;
    private DoubanMovieAdapter mMovieAdapter;
    private LinearLayoutManager mLayoutManager;
    private SwipeRefreshLayout mMovieRefresh;
    private int mMovieStart = 0;

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
        mRetrofitClient.request(mMovieStart, MOVIE_REQUEST_COUNT);
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
                    List<MovieResponse.MovieBean> movieBeanList = data.subjects;
                    int size = 0;
                    if (movieBeanList != null) {
                        size = movieBeanList.size();
                    }
                    if (size < MOVIE_REQUEST_COUNT) {
                        mMovieAdapter.loadingStatusChanged(RetrofitRequestUtils.LoadingStatus.STATUS_LOADING_EMPTY);
                    } else {
                        mMovieAdapter.loadingStatusChanged(RetrofitRequestUtils.LoadingStatus.STATUS_LOAD_COMPLETED);
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
                mRetrofitClient.request(mMovieStart, MOVIE_REQUEST_COUNT);
            }
        });
        mMovieList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int loadingStatus = mMovieAdapter.getLoadingStatus();
                    if (loadingStatus != RetrofitRequestUtils.LoadingStatus.STATUS_LOAD_COMPLETED) {
                        return;
                    }
                    int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();
                    int adapterLastItemPosition = mMovieAdapter.getItemCount() - 1;
                    if (lastVisibleItemPosition == adapterLastItemPosition) {
                        mMovieStart = mMovieStart + MOVIE_REQUEST_COUNT;
                        mMovieAdapter.loadingStatusChanged(RetrofitRequestUtils.LoadingStatus.STATUS_LOADING);
                        mRetrofitClient.request(mMovieStart, MOVIE_REQUEST_COUNT);
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
