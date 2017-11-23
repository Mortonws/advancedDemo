package com.advanced.demo.gyro;

import java.util.Map;

/**
 * @author by sangw on 2017/9/13.
 */

public interface DevicePropertyCollectListener {
    void onCollect(Map<String, String> property);
}
