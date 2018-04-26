package com.advanced.demo.openSocial;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

public class OpenSocialActivity extends BaseActivity {
    @Override
    protected void initView() {
        super.initView();
    }

    @Override
    protected void initListener() {
        super.initListener();
        findViewById(R.id.btn_open_ins).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://instagram.com/_u/LightInTheBox/");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/LightInTheBox/")));
                }
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_open_social;
    }
}
