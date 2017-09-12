package com.advanced.demo.camera;

/**
 * @author by sangw on 2017/9/11.
 */

public class TakePhotoUtils {
    private static TakePhotoUtils sInstance = new TakePhotoUtils();
    private String mTakePhotoPath;

    private TakePhotoUtils() {

    }

    public static TakePhotoUtils getInstance() {
        if (sInstance == null) {
            sInstance = new TakePhotoUtils();
        }
        return sInstance;
    }

    public void setTakePhotoPath(String takePhotoPath) {
        mTakePhotoPath = takePhotoPath;
    }

    public String getTakePhotoPath() {
        return mTakePhotoPath;
    }
}
