package com.advanced.demo.threadTest;

import android.util.Log;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author by morton_ws on 2019-07-29.
 */
public class ThreadDemoActivity extends BaseActivity {
    private boolean isRunThread = false;

    private ExecutorService mThreadPool = Executors.newCachedThreadPool();
    ExecutorService mThread2 = Executors.newScheduledThreadPool(2);

    @Override
    protected void initPages() {
        super.initPages();
        isRunThread = true;
        mThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                while (isRunThread) {
                    try {
                        Log.e(getTag(), "threadName = " + Thread.currentThread().getName() + "; threadId = " + Thread.currentThread().getId());
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRunThread = false;
        Log.e(getTag(), "[onDestroy] hashCode = " + hashCode());
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_thread_demo;
    }
}
