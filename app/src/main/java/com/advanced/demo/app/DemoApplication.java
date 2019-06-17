package com.advanced.demo.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;

/**
 * @author by morton_ws on 2019-06-10.
 */
public class DemoApplication extends Application {
    private static String TAG = DemoApplication.class.getSimpleName();

    public static Context sAppContext;
    private static int activityStartedCount = 0;
    private Handler mHandler = new Handler();
    private boolean isCodeStart = true;

    @Override
    public void onCreate() {
        super.onCreate();
        sAppContext = this;
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                isCodeStart = false;
            }
        }, 2000);
        register();
    }

    private void register() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                activityStartedCount++;
                if (activityStartedCount == 1) {
                    if (!isCodeStart) {

                    }
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                activityStartedCount--;
                if (activityStartedCount == 0) {
                    //切换到后台
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
