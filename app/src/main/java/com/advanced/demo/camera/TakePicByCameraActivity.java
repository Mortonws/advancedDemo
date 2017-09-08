package com.advanced.demo.camera;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;
import com.bumptech.glide.Glide;

import java.io.File;

/**
 * @author by sangw on 2017/9/8.
 */

public class TakePicByCameraActivity extends BaseActivity {
    private final static String TAG = "TakePic";
    private final static String EXTRA_SAVE_PHOTO_PATH_KEY = "com.advanced.demo.camera.PHOTO_PATH_KEY";

    private final static String PICS_DIR = "/advance/pic";
    private final static int RESULT_CODE_TAKE_PHOTO = 1001;

    private Button mTakePic;
    private ImageView mPicture;
    private String mTakePhotoPath;
    private File mTakePhotoDir;
    private Uri mImageUri;
    private String mPhotoName;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //判断 activity被销毁后 有没有数据被保存下来
        if (savedInstanceState != null) {

            mTakePhotoPath = savedInstanceState.getString(EXTRA_SAVE_PHOTO_PATH_KEY);

            Log.i(TAG, "onCreate, saveInstanceState, mTakePhotoPath: " + mTakePhotoPath);
            if (TextUtils.isEmpty(mTakePhotoPath)) {
                return;
            }

            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            File file = new File(mTakePhotoPath);
            Uri contentUri = Uri.fromFile(file);
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);

            Glide.with(mContext).load(file).into(mPicture);
        }
    }

    @Override
    protected void initPages() {
        super.initPages();
        mTakePhotoDir = new File(Environment.getExternalStorageDirectory().getPath() + PICS_DIR);
    }

    @Override
    protected void initView() {
        super.initView();
        mTakePic = (Button) findViewById(R.id.btn_take_pic);
        mPicture = (ImageView) findViewById(R.id.picture);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mTakePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoName = "advance_photo_" + System.currentTimeMillis() + ".jpg";
                mImageUri = Uri.fromFile(new File(mTakePhotoDir, mPhotoName));
                mTakePhotoPath = mImageUri.getPath();
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
                        startActivityForResult(openCameraIntent, RESULT_CODE_TAKE_PHOTO);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RESULT_CODE_TAKE_PHOTO:
                    Log.i(TAG, "mTakePhotoDir: " + mTakePhotoDir + "; mPhotoName: " + mPhotoName);
                    if (TextUtils.isEmpty(mTakePhotoPath)) {
                        Log.i(TAG, "onActivityResult()...mTakePhotoPath is empty");
                        return;
                    }
                    File photo = new File(mTakePhotoPath);
                    if (photo.exists() && photo.isFile()) {
                        Log.i(TAG, "onActivityResult()...send photo:" + mTakePhotoPath);
                        Glide.with(mContext).load(photo).into(mPicture);
                    }
                    break;
            }
        }
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_camera_pic;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()...mTakePhotoPath: " + mTakePhotoPath);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()...mTakePhotoPath: " + mTakePhotoPath);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_SAVE_PHOTO_PATH_KEY, mTakePhotoPath);
    }
}
