package com.advanced.baselib.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * @author by morton_ws on 2017/10/9.
 */

public class DensityUtils {

    public static int getWidth(Context context) {
        initScreen(context);
        if (screen != null) {
            return screen.widthPixels;
        }
        return 0;
    }

    public static int getHeight(Context context) {
        initScreen(context);
        if (screen != null) {
            return screen.heightPixels;
        }
        return 0;
    }

    private static void initScreen(Context context) {
        if (screen == null) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager windowManager = (WindowManager) context
                    .getSystemService(Context.WINDOW_SERVICE);
            windowManager.getDefaultDisplay().getMetrics(dm);
            if (dm.widthPixels > dm.heightPixels) {
                screen = new Screen(dm.heightPixels, dm.widthPixels);
            } else {
                screen = new Screen(dm.widthPixels, dm.heightPixels);
            }
        }
    }

    public static int getStatusHeight(Context context) {
        if (STATUS_HEIGHT <= 0) {
            try {
                int resourceId = context.getResources()
                        .getIdentifier("status_bar_height", "dimen", "android");
                if (resourceId > 0) {
                    STATUS_HEIGHT = context.getResources()
                            .getDimensionPixelSize(resourceId);
                }
            } catch (Exception ex) {
            }
        }
        return STATUS_HEIGHT;
    }

    private static Screen screen = null;
    private static int STATUS_HEIGHT = 0;

    public static class Screen {
        public int widthPixels;
        public int heightPixels;

        public Screen() {
        }

        public Screen(int widthPixels, int heightPixels) {
            this.widthPixels = widthPixels;
            this.heightPixels = heightPixels;
        }

        @NonNull
        @Override
        public String toString() {
            return "(" + widthPixels + "," + heightPixels + ")";
        }

    }
}
