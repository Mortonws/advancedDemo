package com.advanced.demo.liveDataDemo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author by morton_ws on 2019-07-18.
 */
public class DemoDataViewModel extends ViewModel {


    private MutableLiveData<String>  mContentLiveData = new MutableLiveData<>();
    private Handler mHandler = new Handler();

    public void loadData() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setData();
            }
        }, 3000);
    }

    private void setData() {
        SimpleDateFormat format = new SimpleDateFormat( "HH:mm:ss", Locale.getDefault());
        mContentLiveData.postValue("SangW, Time: " + format.format(new Date()));
    }

    public MutableLiveData<String> getContentLiveData() {
        return mContentLiveData;
    }
}
