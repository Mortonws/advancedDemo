package com.advanced.demo.propertyAnimator;

import android.animation.ValueAnimator;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.advanced.baselib.base.BaseActivity;
import com.advanced.demo.R;

/**
 * @author by sangw on 2017/8/16.
 */

public class PropertyAnimatorActivity extends BaseActivity {

    private TextView mPropertyValue;
    private Button mRestart;
    private ValueAnimator mValueAnimator;

    @Override
    protected void initPages() {
        super.initPages();
        mValueAnimator = ValueAnimator.ofInt(0, 1000);
        mValueAnimator.setDuration(1000 * 10);
        mValueAnimator.start();
    }

    @Override
    protected void initView() {
        super.initView();
        mPropertyValue = (TextView) findViewById(R.id.property_value);
        mRestart = (Button) findViewById(R.id.animator_restart);
    }

    @Override
    protected void initListener() {
        super.initListener();
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animatorValue = (int) animation.getAnimatedValue();
                mPropertyValue.setText(String.valueOf(animatorValue));
            }
        });
        mRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPropertyValue.setText("0");
                mValueAnimator.start();
            }
        });
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_property_animator;
    }
}
