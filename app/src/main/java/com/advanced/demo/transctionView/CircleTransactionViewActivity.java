package com.advanced.demo.transctionView;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.advanced.demo.R;

/**
 * @author by morton_ws on 2017/9/20.
 */

public class CircleTransactionViewActivity extends Activity {
    private ImageView mCircleRed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_view);

        initView();
    }

    private void initView() {
        mCircleRed = (ImageView) findViewById(R.id.red_circle);
        mCircleRed.post(new Runnable() {
            @Override
            public void run() {
                int x = (mCircleRed.getRight() - mCircleRed.getLeft()) / 2;
                int y = (mCircleRed.getBottom() - mCircleRed.getTop()) / 2;
                RotateAnimation animation = new RotateAnimation(0, 360, x , y);
                animation.setDuration(3000);
                animation.setRepeatCount(-1);
                animation.setRepeatMode(Animation.RESTART);
                mCircleRed.startAnimation(animation);

            }
        });

        ImageView loadingAnimation = (ImageView) findViewById(R.id.loading_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) loadingAnimation.getDrawable();
        animationDrawable.start();
    }
}
