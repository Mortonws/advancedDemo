package com.advanced.demo.cameraDetect;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;
import com.sangw.library.DensityUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author by morton_ws on 2017/10/9.
 */

public class CameraDetectActivity extends BaseActivity implements TextureView.SurfaceTextureListener {
    private TextureView mTextureView;
    private int mCameraId = 1;
    private Camera mCamera;
    private ImageView mBtnChangeCamera;
    private SurfaceTexture mSurfaceTexture;
    private int mCameraWidth;
    private int mCameraHeight;
    private int mTitleBarHeight;

    @Override
    protected void initView() {
        super.initView();
        mTextureView = (TextureView) findViewById(R.id.texture_view);
        mBtnChangeCamera = (ImageView) findViewById(R.id.btn_change_camera);
    }

    @Override
    protected void initPages() {
        super.initPages();
        mTextureView.setSurfaceTextureListener(this);
        mTextureView.setKeepScreenOn(true);
        mTitleBarHeight = getResources().getDimensionPixelSize(R.dimen.base_view_height);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mBtnChangeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeCamera();
            }
        });
    }

    private void changeCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
        if (mCameraId == 1) {
            mCameraId = 0;
        } else {
            mCameraId = 1;
        }
        startPreview();
    }

    private void startPreview() {
        try {
            mCamera = Camera.open(mCameraId);
            mCamera.setDisplayOrientation(getCameraAngle(this));
            Camera.Parameters params = mCamera.getParameters();

            mCameraWidth = DensityUtils.getWidth(this);
            mCameraHeight = DensityUtils.getHeight(this) - DensityUtils.getStatusHeight(this) - mTitleBarHeight;
            Camera.Size size = calBestPreviewSize(mCamera.getParameters(), mCameraWidth, mCameraHeight);
            params.setPreviewSize(size.width, size.height);
            mCamera.setParameters(params);

//            mTextureView.setAspectRatio(size.height, size.width);
            float scaleY = size.width * 1.0f / mCameraHeight;
            mTextureView.setScaleY(scaleY);

            mCamera.setPreviewTexture(mSurfaceTexture);
            mCamera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 通过屏幕参数、相机预览尺寸计算布局参数
    public RelativeLayout.LayoutParams getLayoutParam() {
        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();
        float scale = Math.min(DensityUtils.getWidth(mContext) * 1.0f / previewSize.height,
                (DensityUtils.getHeight(mContext) * 1.0f) / previewSize.width);
        int layout_width = (int) (scale * previewSize.height);
        int layout_height = (int) (scale * previewSize.width);

//        layout_height = layout_height * (mCameraWidth / layout_width);
//        layout_width = mCameraWidth;

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(layout_width, layout_height);
        params.setMargins(0, mTitleBarHeight, 0, 0);
        return params;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_camera_detect;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        this.mSurfaceTexture = surface;
        startPreview();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    /**
     * 获取照相机旋转角度
     */
    public int getCameraAngle(Activity activity) {
        int rotateAngle;
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(mCameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            rotateAngle = (info.orientation + degrees) % 360;
            rotateAngle = (360 - rotateAngle) % 360; // compensate the mirror
        } else { // back-facing
            rotateAngle = (info.orientation - degrees + 360) % 360;
        }
        return rotateAngle;
    }

    private Camera.Size calBestPreviewSize(Camera.Parameters camPara,
                                           final int width, final int height) {
        List<Camera.Size> allSupportedSize = camPara.getSupportedPreviewSizes();
        ArrayList<Camera.Size> widthLargerSize = new ArrayList<>();
        for (Camera.Size tmpSize : allSupportedSize) {
            if (tmpSize.width > tmpSize.height) {
                widthLargerSize.add(tmpSize);
            }
        }

        Collections.sort(widthLargerSize, new Comparator<Camera.Size>() {
            @Override
            public int compare(Camera.Size lhs, Camera.Size rhs) {
                int off_one = Math.abs(lhs.width * lhs.height - width * height);
                int off_two = Math.abs(rhs.width * rhs.height - width * height);
                return off_one - off_two;
            }
        });

        return widthLargerSize.get(0);
    }
}
