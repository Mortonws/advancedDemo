package com.advanced.demo.gyro;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.util.Arrays;
import java.util.Locale;

/**
 * @author by sangw on 2017/9/14.
 */

public class GyroscopeProperty extends IAsyncProperty {
    private final static String TAG = "Gyro.PropertyCollect";
    private SensorManager mSensorManager;
    private Handler mHandler;
    private int mReceiveGyroCount = 0;
    private final static int DEFAULT_RECEIVE_COUNT = 3;
    private SensorEventListener mSensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE
                    && mHandler != null) {
                float[] sensorValue = event.values;

                String sensorProperty = "1";//默认值，获取参数异常
                Log.e(TAG, "sensorValue: " + Arrays.toString(sensorValue) + "; mReceiveGyroCount: " + mReceiveGyroCount);
                if (mReceiveGyroCount < DEFAULT_RECEIVE_COUNT) {
                    mReceiveGyroCount++;
                    if (sensorValue != null && sensorValue.length == 3) {
                        if (sensorValue[0] == 0.0f && sensorValue[1] == 0.0f && sensorValue[2] == 0.0f) {
                            return;
                        }
                    } else {
                        return;
                    }
                    sensorProperty = String.format(Locale.getDefault(), "%s,%s,%s", sensorValue[0], sensorValue[1], sensorValue[2]);
                }
                Message msg = new Message();
                msg.what = PropertyCollector.AsynchronousEvent.EVENT_ASYNC_GYRO;
                msg.obj = sensorProperty;
                mHandler.sendMessage(msg);

                mHandler = null;
                if (mSensorManager != null) {
                    mSensorManager.unregisterListener(mSensorEventListener);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    public void getProperty(Context context, Handler handler) {
        this.mHandler = handler;
        this.mReceiveGyroCount = 0;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        String errorCode = "0";//defaultErrorCode，获取传感器设备失败
        if (mSensorManager != null) {
            Sensor sensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            if (sensor != null) {
                mSensorManager.registerListener(mSensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                return;
            }
            errorCode = "-1";//获取陀螺仪设备失败
        }
        Message msg = new Message();
        msg.what = PropertyCollector.AsynchronousEvent.EVENT_ASYNC_GYRO;
        msg.obj = errorCode; //设备不支持
        mHandler.sendMessage(msg);

        mHandler = null;
    }

    public String getPropertyKey() {
        return "jr_app_gyro";
    }
}
