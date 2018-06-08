package com.advanced.demo.matrixView;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author by WuSang on 2018/6/7.
 */

public class TranslateImageView extends AppCompatImageView implements View.OnTouchListener {

    public TranslateImageView(Context context) {
        this(context, null);
    }

    public TranslateImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        setScaleType(ScaleType.MATRIX);

        setOnTouchListener(this);
    }

    private Matrix mMatrix = new Matrix();

    private float mLastRawX;
    private float mLastRawY;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void translateBitmap() {
        Drawable drawable = getDrawable();
        int baseBitmapWidth = drawable.getIntrinsicWidth();
        int baseBitmapHeight = drawable.getIntrinsicHeight();

        int imageWidth = getMeasuredWidth();
        int imageHeight = getMeasuredHeight();

        int moveDx = (imageWidth - baseBitmapWidth) / 2;
        int moveDy = (imageHeight - baseBitmapHeight) / 2;

        mMatrix.postTranslate(moveDx, moveDy);
        setImageMatrix(mMatrix);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastRawX = event.getX();
                mLastRawY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float eventX = event.getX();
                float eventY = event.getY();

                float moveDx = eventX - mLastRawX;
                float moveDy = eventY - mLastRawY;

                mMatrix.postTranslate(moveDx, moveDy);
                setImageMatrix(mMatrix);

                mLastRawX = eventX;
                mLastRawY = eventY;

                break;
        }
        return true;
    }
}
