package com.advanced.demo.transctionView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import com.advanced.demo.R;

/**
 * @author by morton_ws on 2017/9/20.
 */

public class TransactionView extends View {
    private Context mContext;

    private Canvas temp;
    private Paint paint;
    private Paint p = new Paint();
    private Paint transparentPaint;
    private Bitmap bitmap;

    private int mWidth;
    private int mHeight;
    private Bitmap mCircleBitmap;
    private int mCircleWidth;
    private int mCircleHeight;
    private Paint mCirclePaint = new Paint();

    public TransactionView(Context context) {
        this(context, null);
    }

    public TransactionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initPaint();
    }

    private void initPaint() {
        mWidth = getResources().getDimensionPixelSize(R.dimen.hole_view_width);
        mHeight = getResources().getDimensionPixelSize(R.dimen.hole_view_height);
        Bitmap sourceCircleBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.circle_gray);
        mCircleWidth = sourceCircleBitmap.getWidth() * 3 / 5;
        mCircleHeight = sourceCircleBitmap.getHeight() * 3 / 5;

        mCircleBitmap = Bitmap.createScaledBitmap(sourceCircleBitmap, mCircleWidth, mCircleHeight, true);
        mCirclePaint.setColor(ContextCompat.getColor(mContext, R.color.black));

        bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        temp = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(ContextCompat.getColor(mContext, R.color.white));
        transparentPaint = new Paint();
        transparentPaint.setColor(ContextCompat.getColor(mContext, R.color.transparent));
        transparentPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        temp.drawRect(0, 0, temp.getWidth(), temp.getHeight(), paint);
        temp.drawBitmap(mCircleBitmap, (mWidth - mCircleWidth) / 2, (mHeight - mCircleHeight) / 2, mCirclePaint);
        temp.drawCircle(mWidth / 2, mHeight / 2, Math.min(mCircleWidth / 2, mCircleHeight / 2) * 2 / 3, transparentPaint);
        canvas.drawBitmap(bitmap, 0, 0, p);
    }
}
