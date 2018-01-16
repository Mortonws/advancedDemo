package com.advanced.demo.annotationTest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.advanced.demo.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * @author by sangw on 2018/1/16.
 */
@EActivity(R.layout.activity_annotation_test)
public class TestAnnotationActivity extends AppCompatActivity {

    @ViewById(R.id.content)
    TextView mContent;
    @ViewById(R.id.input_content)
    EditText mInputContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Click
    void btn_change_content() {
        String content = mInputContent.getText().toString();
        if (!TextUtils.isEmpty(content)) {
            mContent.setText(content);
        }
    }
}
