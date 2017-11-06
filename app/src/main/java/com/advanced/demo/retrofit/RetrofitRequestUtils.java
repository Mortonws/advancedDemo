package com.advanced.demo.retrofit;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author by morton_ws on 2017/10/27.
 */

public class RetrofitRequestUtils {

    public final static int EVENT_REQUEST_MOVIE = 1001;

    public interface ItemViewType {
        int ITEM_TYPE_MOVIE = 1000;
        int ITEM_TYPE_LOADING = 1001;
    }

    public interface LoadingStatus {
        int STATUS_LOADING = 2001;
        int STATUS_LOAD_COMPLETED = 2002;
        int STATUS_LOADING_EMPTY = 2003;
    }

    private static RetrofitRequestUtils sInstance;
    private List<WeakReference<Handler>> mWeakReferenceList = new ArrayList<>();

    private RetrofitRequestUtils() {

    }

    public static RetrofitRequestUtils getInstance() {
        if (sInstance == null) {
            sInstance = new RetrofitRequestUtils();
        }
        return sInstance;
    }

    public void registerHandler(Handler target) {
        for (WeakReference<Handler> reference : mWeakReferenceList) {
            Handler handler = reference.get();
            if (handler == target) {
                return;
            }
        }
        mWeakReferenceList.add(new WeakReference<>(target));
    }

    public void unregisterHandler(Handler target) {
        int i = 0;
        while (i < mWeakReferenceList.size()) {
            Handler handler = mWeakReferenceList.get(i).get();
            if (handler == target) {
                mWeakReferenceList.remove(new WeakReference<>(handler));
            } else {
                i++;
            }
        }
    }

    public void sendMsg(Message msg) {
        for (WeakReference<Handler> reference : mWeakReferenceList) {
            Handler handler = reference.get();
            if (handler != null) {
                Message sendMsg = new Message();
                sendMsg.copyFrom(msg);
                handler.sendMessage(sendMsg);
            }
        }
    }
}
