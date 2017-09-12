package com.advanced.demo.camera;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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

    private final static String PICS_DIR = "/advance/pic";

    private Button mTakePic;
    private ImageView mPicture;
    private File mTakePhotoDir;
    private Uri mImageUri;
    private String mPhotoName;

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
                TakePhotoUtils.getInstance().setTakePhotoPath("");
                mPhotoName = "advance_photo_" + System.currentTimeMillis() + ".jpg";
                mImageUri = Uri.fromFile(new File(mTakePhotoDir, mPhotoName));
                TakePhotoUtils.getInstance().setTakePhotoPath(mImageUri.getPath());
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);
//                startActivityForResult(openCameraIntent, RESULT_CODE_TAKE_PHOTO);
                startActivity(openCameraIntent);
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_camera_pic;
    }

    @Override
    protected void onResume() {
        super.onResume();
        String takePhotoPath = TakePhotoUtils.getInstance().getTakePhotoPath();
        if (!TextUtils.isEmpty(takePhotoPath) ) {
            File photoFile = new File(takePhotoPath);
            if (photoFile.exists() && photoFile.isFile()) {
                Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(photoFile);
                mediaScanIntent.setData(contentUri);
                sendBroadcast(mediaScanIntent);
                Log.i(TAG, "onResume, takePhotoPath: " + takePhotoPath);

                Glide.with(mContext).load(photoFile).override(1080, 1920).into(mPicture);
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        TakePhotoUtils.getInstance().setTakePhotoPath("");
    }
}
