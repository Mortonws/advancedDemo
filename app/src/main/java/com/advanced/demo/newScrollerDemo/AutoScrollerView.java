package com.advanced.demo.newScrollerDemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import java.util.Locale;

/**
 * @author by morton_ws on 2019-08-15.
 */
public class AutoScrollerView extends RelativeLayout {
    private final static String TAG = "Scroller.View";
    private Context mContext;
    private Scroller mScroller;
    private int mCount = 0;

    public AutoScrollerView(Context context) {
        this(context, null);
    }

    public AutoScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mContext = getContext();
        mScroller = new Scroller(mContext);
    }

    public void smoothScrollTo(int destX, int destY) {
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        destX = destX * -1;
        destY = destY * -1;
        String logMsg = String.format(Locale.getDefault(),
                "index = %s; destX: %s; destY: %s; scrollX: %s; scrollY: %s",
                ++mCount, destX, destY, scrollX, scrollY);
        Log.e(TAG, logMsg);
        mScroller.startScroll(scrollX, scrollY, destX, destY, 99);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
