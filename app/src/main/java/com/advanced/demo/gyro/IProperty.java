package com.advanced.demo.gyro;

import android.content.Context;

import java.lang.reflect.Method;
import java.util.HashMap;

/**
 *
 */

public abstract class IProperty {
    protected static final String UNKNOWN = "N/A";
    private static final String TRUE = "1";
    private static final String FALSE = "0";

    public abstract HashMap<String, String> getProperties(Context context);

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

    protected String convertBooleanStr(boolean b) {
        return b ? TRUE : FALSE;
    }
}
