package com.advanced.demo.newScrollerDemo;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

/**
 * @author by morton_ws on 2019-08-15.
 */
public class NewScrollerDemoActivity extends BaseActivity {

    private final static String TAG = "Scroller.DemoActivity";
    private final static int DEFAULT_COUNT = 50;
    private AutoScrollerView mScrollerView;
    private int mCount = 0;
    private Handler mHandler = new Handler();
    private int mRootViewHeight;
    private int mRootViewWidth;
    private View mDemoView;

    @Override
    protected void initView() {
        super.initView();

        mScrollerView = findViewById(R.id.root_view);
        mDemoView = findViewById(R.id.view_demo);
    }

    @Override
    protected void initPages() {
        super.initPages();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int demoViewWidth = mDemoView.getMeasuredWidth();
                int demoViewHeight = mDemoView.getMeasuredHeight();

                mRootViewWidth = mScrollerView.getMeasuredWidth() ;
                mRootViewHeight = mScrollerView.getMeasuredHeight() ;

                Log.e(TAG, "mRootViewWidth = " + mRootViewWidth + "; mRootViewHeight = " + mRootViewHeight);
//                Log.e(TAG, "demoViewWidth = " + demoViewWidth + "; demoViewHeight = " + demoViewHeight);

//                mScrollerView.smoothScrollTo(mRootViewWidth - demoViewWidth, mRootViewHeight - demoViewHeight);

                startScroll();
            }
        }, 2000);

    }

    private void startScroll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mCount++ < DEFAULT_COUNT) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mScrollerView.smoothScrollTo(mRootViewWidth / DEFAULT_COUNT, mRootViewHeight / DEFAULT_COUNT);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCount = DEFAULT_COUNT + 1;
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_new_scroller_demo;
    }
}
