package com.advanced.demo.emulator;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author by morton_ws on 2017/11/6.
 */

public class EmulatorTestActivity extends BaseActivity {

    protected static final String UNKNOWN = "N/A";
    private static HashMap<String, Boolean> sEmulatorTestMap = new HashMap<>();

    static {
        sEmulatorTestMap.put("generic", Build.FINGERPRINT.startsWith("generic"));
        sEmulatorTestMap.put("vbox", Build.FINGERPRINT.toLowerCase().contains("vbox"));
        sEmulatorTestMap.put("test-keys", Build.FINGERPRINT.toLowerCase().contains("test-keys"));
        sEmulatorTestMap.put("model google_sdk", Build.MODEL.contains("google_sdk"));
        sEmulatorTestMap.put("Emulator", Build.MODEL.contains("Emulator"));
        sEmulatorTestMap.put("Android SDK built for x86", Build.MODEL.contains("Android SDK built for x86"));
        sEmulatorTestMap.put("Genymotion", Build.MANUFACTURER.contains("Genymotion"));
        sEmulatorTestMap.put("brand & device generic", (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")));
        sEmulatorTestMap.put("google_sdk", "google_sdk".equals(Build.PRODUCT));
    }

    private Button mBtnReset;
    private Button mBtnEmulatorTest;
    private TextView mEmulatorTestResult;
    private Button mBtnSensorManager;
    private TextView mSensorResult;

    @Override
    protected void initView() {
        super.initView();
        mBtnEmulatorTest = (Button) findViewById(R.id.btn_emulator_test);
        mEmulatorTestResult = (TextView) findViewById(R.id.emulator_test_result);
        mBtnSensorManager = (Button) findViewById(R.id.btn_sensor_manager);
        mSensorResult = (TextView) findViewById(R.id.sensor_manager_result);
        mBtnReset = (Button) findViewById(R.id.btn_reset);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnEmulatorTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder emulatorResult = new StringBuilder();
                for (Map.Entry<String, Boolean> emulator : sEmulatorTestMap.entrySet()) {
                    String key = emulator.getKey();
                    boolean result = emulator.getValue();
                    emulatorResult.append(key).append(": ").append(result).append("\n");
                }
                emulatorResult.append("isRoot: ").append(isRoot()).append("\n");
                emulatorResult.append("CPU Core Num: ").append(getCPUNumCores()).append("\n");
                emulatorResult.append("CPU type: ").append(getCPUType()).append("\n");
                emulatorResult.append("Battery Info: ").append(getBattery());
                emulatorResult.append("Wifi Mac: ").append(getWifiMac()).append("\n");
                mEmulatorTestResult.setText(emulatorResult.toString());
            }
        });
        mBtnSensorManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sensorResult = getSensorManager();
                //noinspection StringBufferReplaceableByString
                StringBuilder result = new StringBuilder(sensorResult);
                result.append(getGyroSensor());
                result.append(getIMEI());
                result.append(getBluetoothAddress());
                result.append("isEmulator: ").append(isEmulator(mContext)).append("\n");
                mSensorResult.setText(result.toString());
            }
        });
        mBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSensorResult.setText(null);
                mEmulatorTestResult.setText(null);
            }
        });
    }

    private String getWifiMac() {
        String wifiMac = null;
        NetworkInfo info = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            switch (info.getType()) {
                case ConnectivityManager.TYPE_MOBILE:
                    wifiMac = "Connected by Mobile";
                    break;
                case ConnectivityManager.TYPE_WIFI:
                    wifiMac = getMacAddress();
                    break;
            }
        }
        return wifiMac;
    }

    private static String getMacAddress() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

                byte[] macBytes = nif.getHardwareAddress();
                if (macBytes == null) {
                    return "";
                }

                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02X:",b));
                }

                if (res1.length() > 0) {
                    res1.deleteCharAt(res1.length() - 1);
                }
                return res1.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "02:00:00:00:00:00";
    }

    private boolean isRoot() {
        return !((!new File("/system/bin/su").exists())
                && (!new File("/system/xbin/su").exists()));
    }

    private int getCPUNumCores() {
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                return Pattern.matches("cpu[0-9]", pathname.getName());
            }
        }

        try {
            File dir = new File("/sys/devices/system/cpu/");
            File[] files = dir.listFiles(new CpuFilter());
            return files.length;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    private String getCPUType() {
        String cpuType;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cpuType = getSystemProperty("ro.product.cpu.abilist");
        } else {
            cpuType = Build.CPU_ABI + "," + Build.CPU_ABI2;
        }
        return cpuType;
    }

    protected String getSystemProperty(@SuppressWarnings("SameParameterValue") String name) {
        String ret = UNKNOWN;
        try {
            @SuppressLint("PrivateApi") Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            //noinspection unchecked
            Method m = cl.getMethod("get", String.class, String.class);
            Object result = m.invoke(invoker, name, UNKNOWN);
            ret = (String) result;
        } catch (Exception ignored) {
        }
        return ret;
    }

    private String getBattery() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, ifilter);

        // Are we charging / charged?
        //noinspection ConstantConditions
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        //noinspection unused
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        //float batteryPct = level / (float)scale;

        //noinspection StringBufferReplaceableByString
        StringBuilder sbBattery = new StringBuilder();
        sbBattery.append("isInCharge: ").append(isCharging).append("\n");
        sbBattery.append("remainBattery: ").append(level).append("\n");
        return sbBattery.toString();
    }

    private String getSensorManager() {
        StringBuilder sensorResult = new StringBuilder();
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager == null) {
            sensorResult.append("Sensor Manager Device is Null").append("\n");
        } else {
            List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
            if (sensorList == null || sensorList.size() == 0) {
                sensorResult.append("Sensor List is null").append("\n");
            } else {
                sensorResult.append("Sensor Item List: ").append("\n");
                for (Sensor sensor : sensorList) {
                    sensorResult.append(sensor.getName()).append("\n");
                }
            }
        }
        return sensorResult.toString();
    }

    private String getGyroSensor() {
        StringBuilder gyroSensorResult = new StringBuilder();
        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            if (sensor != null) {
                gyroSensorResult.append("gyro Sensor: ").append(sensor.getName()).append("\n");
            } else {
                gyroSensorResult.append("gyro sensor is null").append("\n");
            }
        } else {
            gyroSensorResult.append("Sensor Manager device is NULL").append("\n");
        }
        return gyroSensorResult.toString();
    }

    @SuppressLint("MissingPermission")
    private String getIMEI() {
        StringBuilder imeiResult = new StringBuilder();
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String imei = tm.getSubscriberId();
        @SuppressLint("MissingPermission") String deviceId = tm.getDeviceId();
        if (TextUtils.isEmpty(imei)) {
            imeiResult.append("imei is Empty").append("\n");
        } else {
            imeiResult.append("Phone IMEI: ").append(imei).append("\n");
        }
        if (TextUtils.isEmpty(deviceId)) {
            imeiResult.append("deviceId is Empty").append("\n");
        } else {
            imeiResult.append("Phone deviceId: ").append(deviceId).append("\n");
        }
        String imeiReflect;
        Class<?> telephoneManagerClazz = TelephonyManager.class;
        try {
            Method method =  telephoneManagerClazz.getMethod("getImei");
            imeiReflect = (String) method.invoke(tm);
        } catch (Exception e) {
            e.printStackTrace();
            imeiReflect = "EXCEPTION";
        }
        imeiResult.append("Relect IMEI: ").append(imeiReflect).append("\n");
        CTelephoneInfo.getInstance(mContext).setCTelephoneInfo();
        imeiResult.append("IMEI1: ").append(CTelephoneInfo.getInstance(mContext).getImeiSIM1()).append("\n");
        imeiResult.append("IMEI2: ").append(CTelephoneInfo.getInstance(mContext).getImeiSIM2()).append("\n");
        return imeiResult.toString();
    }

    private String getBluetoothAddress() {
        //noinspection StringBufferReplaceableByString
        StringBuilder bluetooth = new StringBuilder();
        bluetooth.append("bluetooth: ");
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            bluetooth.append("is Null");
        } else {
            bluetooth.append(Settings.Secure.getString(getContentResolver(), "bluetooth_address"));
            bluetooth.append("; getByAdapter: ").append(bluetoothAdapter.getAddress());
        }
        bluetooth.append("\n");
        return bluetooth.toString();
    }

    private boolean isEmulator(Context context) {
        return isEmulatorBySensor(context)
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || "google_sdk".equals(Build.PRODUCT);
    }

    private boolean isEmulatorBySensor(Context context) {
        if (Build.MANUFACTURER.toLowerCase().contains("genymotion")) {
            return true;
        } else {
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            boolean hasGyroHardware = true;
            boolean hasBluetoothHardware = true;
            if (bluetoothAdapter == null) {
                hasBluetoothHardware = false;
            }
            if (sensorManager == null) {
                hasBluetoothHardware = false;
            } else {
                Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
                if (sensor == null) {
                    hasGyroHardware = false;
                }
            }
            return !hasBluetoothHardware && !hasGyroHardware;
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_emulator_test;
    }
}
