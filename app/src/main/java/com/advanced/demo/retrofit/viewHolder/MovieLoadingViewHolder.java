package com.advanced.demo.retrofit.viewHolder;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.advanced.demo.R;
import com.advanced.demo.retrofit.RetrofitRequestUtils;

/**
 * @author by sangw on 2017/11/2.
 */

public class MovieLoadingViewHolder extends MovieBaseViewHolder {
    private RelativeLayout mRootLoading;
    private ImageView mIconLoading;
    private TextView mLoadEmpty;
    private RotateAnimation mRotateAnimation;

    public MovieLoadingViewHolder(Context context, View view) {
        super(context, view);
        mRootLoading = (RelativeLayout) view.findViewById(R.id.root_loading);
        mIconLoading = (ImageView) view.findViewById(R.id.icon_movie_loading);
        mLoadEmpty = (TextView) view.findViewById(R.id.load_empty);

        mRotateAnimation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mRotateAnimation.setDuration(1000);
        mRotateAnimation.setRepeatCount(-1);
    }

    @Override
    public void onShow() {
        int loadingStatus = mMovie.loadingStatus;
        if (loadingStatus == RetrofitRequestUtils.LoadingStatus.STATUS_LOADING) {
            mRootLoading.setVisibility(View.VISIBLE);
            mIconLoading.setVisibility(View.VISIBLE);
            mLoadEmpty.setVisibility(View.GONE);
            mIconLoading.setAnimation(mRotateAnimation);
            mRotateAnimation.start();
        } else if (loadingStatus == RetrofitRequestUtils.LoadingStatus.STATUS_LOADING_EMPTY) {
            mRotateAnimation.cancel();
            mIconLoading.clearAnimation();
            mRootLoading.setVisibility(View.VISIBLE);
            mIconLoading.setVisibility(View.GONE);
            mLoadEmpty.setVisibility(View.VISIBLE);
        } else if (loadingStatus == RetrofitRequestUtils.LoadingStatus.STATUS_LOAD_COMPLETED) {
            mRotateAnimation.cancel();
            mIconLoading.clearAnimation();
            mRootLoading.setVisibility(View.GONE);
        }
    }
}
