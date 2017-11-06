package com.advanced.demo.emulator;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Method;
import java.util.HashMap;
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

    private Button mBtnEmulatorTest;
    private TextView mEmulatorTestResult;

    @Override
    protected void initView() {
        super.initView();
        mBtnEmulatorTest = (Button) findViewById(R.id.btn_emulator_test);
        mEmulatorTestResult = (TextView) findViewById(R.id.emulator_test_result);
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
                mEmulatorTestResult.setText(emulatorResult.toString());
            }
        });
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

    protected String getSystemProperty(String name) {
        String ret = UNKNOWN;
        try {
            Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
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
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        //float batteryPct = level / (float)scale;

        //noinspection StringBufferReplaceableByString
        StringBuilder sbBattery = new StringBuilder();
        sbBattery.append("isInCharge: ").append(isCharging).append("\n");
        sbBattery.append("remainBattery: ").append(level).append("\n");
        return sbBattery.toString();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_emulator_test;
    }
}
