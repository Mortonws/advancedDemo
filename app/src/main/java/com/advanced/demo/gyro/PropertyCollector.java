package com.advanced.demo.gyro;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.HashSet;


public class PropertyCollector {

    private DevicePropertyCollectListener mCollectListener;
    private Context mContext;
    private HashMap<String, String> mAsyncPropertyMap = new HashMap<>();

    public interface AsynchronousEvent {
        int EVENT_ASYNC_GYRO = 1001;
    }

    private static class AsyncPropertyMap {
        final static HashSet<IAsyncProperty> propertySet = new HashSet<>();
        static {
            propertySet.add(new GyroscopeProperty());
        }
    }

    public static HashMap<String, String> collect(Context context) {
        HashMap<String, String> ret = new HashMap<>();
        return ret;
    }

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case AsynchronousEvent.EVENT_ASYNC_GYRO:
                    String value = (String) msg.obj;
                    mAsyncPropertyMap.put("jr_app_gyro", value);
                    break;
            }
            asyncEventCollected();
            return false;
        }
    });

    public void collect(Context context, DevicePropertyCollectListener collectListener) {
        this.mCollectListener = collectListener;
        this.mContext = context;
        for (IAsyncProperty property : AsyncPropertyMap.propertySet) {
            property.getProperty(context, mHandler);
        }
    }

    private synchronized void asyncEventCollected() {
        if (mAsyncPropertyMap.size() == AsyncPropertyMap.propertySet.size()) {
            HashMap<String ,String> propertyMap = collect(mContext);
            propertyMap.putAll(mAsyncPropertyMap);
            if (mCollectListener != null) {
                mCollectListener.onCollect(propertyMap);
            }
        }
    }
}
