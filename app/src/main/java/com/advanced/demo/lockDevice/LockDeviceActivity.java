package com.advanced.demo.lockDevice;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

/**
 * @author by sangw on 2017/9/4.
 */

public class LockDeviceActivity extends BaseActivity {
    private Button mBtnLockDevice;

    private DevicePolicyManager mDevicePolicyManager;
    private ComponentName mComponentName;

    @Override
    protected void initPages() {
        super.initPages();
        //获取设备管理器
        mDevicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, LockReceiver.class);
    }

    @Override
    protected void initView() {
        super.initView();
        mBtnLockDevice = (Button) findViewById(R.id.btn_lock_device);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnLockDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 判断该组件是否有系统管理员的权限
                if (mDevicePolicyManager.isAdminActive(mComponentName)) {
                    mDevicePolicyManager.lockNow(); //锁屏
                } else {
                    activeManager();    //激活权限
                }

            }
        });
    }

    /**
     * 激活设备管理器获取权限
     */
    private void activeManager() {
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "一键锁屏");
        startActivity(intent);
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_lock_device;
    }
}
