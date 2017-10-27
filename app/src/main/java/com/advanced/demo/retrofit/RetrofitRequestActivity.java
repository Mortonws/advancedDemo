package com.advanced.demo.retrofit;

import android.app.ProgressDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
    private RetrofitRequestUtils mRequestUtils;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
                case RetrofitRequestUtils.EVENT_REQUEST_MOVIE:
                    mProgressDialog.show();
                    int start = msg.arg1;
                    int count = msg.arg2;
                    mMovieAdapter.clearData();
                    mRetrofitClient.request(start, count);
                    break;
            }
            return false;
        }
    });

    @Override
    protected void initView() {
        super.initView();
        mMovieList = (RecyclerView) findViewById(R.id.movie_list);
    }

    @Override
    protected void initPages() {
        super.initPages();
        mRequestUtils = RetrofitRequestUtils.getInstance();
        mRetrofitClient = new RetrofitRestClient();
        mRetrofitClient.createClient();
        mProgressDialog = new ProgressDialog(mContext);

        mMovieAdapter = new DoubanMovieAdapter(mContext);
        mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        mMovieList.setAdapter(mMovieAdapter);
        mMovieList.setLayoutManager(mLayoutManager);
        mMovieList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void initListener() {
        super.initListener();
        mRetrofitClient.addRequestListener(new IRetrofitRequestListener() {
            @Override
            public void onSuccess(MovieResponse data) {
                onAfter();
                if (data != null && data.subjects != null) {
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
                mProgressDialog.dismiss();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_retrofit_request;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRequestUtils.registerHandler(mHandler);
    }

    @Override
    public void finish() {
        super.finish();
        mRequestUtils.unregisterHandler(mHandler);
    }
}
