package com.advanced.demo.gyro;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

/**
 * @author by sangw on 2017/9/14.
 */

public abstract class IAsyncProperty extends IProperty {

    public abstract void getProperty(Context context, Handler handler);

    @Override
    public HashMap<String, String> getProperties(Context context) {
        return null;
    }
}
