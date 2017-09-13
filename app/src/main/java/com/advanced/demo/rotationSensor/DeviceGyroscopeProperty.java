package com.advanced.demo.rotationSensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;

/**
 * @author by sangw on 2017/9/13.
 *         GYROSCOPE
 */

public class DeviceGyroscopeProperty implements IDeviceProperty {
    private Context mContext;

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private SensorPropertyEventListener mSensorEventListener;
    private Handler mHandler;

    public DeviceGyroscopeProperty(Context context) {
        this.mContext = context;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mSensorEventListener = new SensorPropertyEventListener();
    }

    public void getDeviceGyroscope(Handler handler) {
        this.mHandler = handler;
        mSensorManager.registerListener(mSensorEventListener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void unregisterListener() {
        mSensorManager.unregisterListener(mSensorEventListener);
    }

    private class SensorPropertyEventListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
                StringBuilder valueBuilder = new StringBuilder();
                float[] values = event.values;
                valueBuilder.append(values[0]).append(",").append(values[1]).append(",").append(values[2]);
                if (mHandler != null) {
                    Message msg = new Message();
                    msg.what = EVENT_COLLECT_GYROSCOPE;
                    msg.obj = valueBuilder.toString();
                    mHandler.sendMessage(msg);
                    mHandler = null;
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    }
}
