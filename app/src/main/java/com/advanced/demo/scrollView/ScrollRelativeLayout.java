package com.advanced.demo.scrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Scroller;

import java.util.Locale;

/**
 * @author by WuSang on 2018/4/26.
 */

public class ScrollRelativeLayout extends RelativeLayout {
    private final static String TAG = "ScrollRelativeLayout";
    private Scroller mScroller;

    private int mTotalVerticalScroll = 0;

    public ScrollRelativeLayout(Context context) {
        this(context, null);
    }

    public ScrollRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    private float mVerticalDown;
    private float mVerticalLastMove;
    private float mVerticalMove;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mVerticalDown = event.getRawY();
                mVerticalLastMove = mVerticalDown;
                return true;
            case MotionEvent.ACTION_MOVE:
                mVerticalMove = event.getRawY();
                int scrollVerticalDistance = (int) (mVerticalLastMove - mVerticalMove);
//                mScroller.startScroll(0, mScroller.getStartY(), 0, scrollVerticalDistance);
//                invalidate();

                if (getChildCount() > 0) {
                    int viewBottom = getChildAt(0).getBottom();
                    int parentViewBottom = getBottom();
                    if (scrollVerticalDistance > 0) {
                        if (mTotalVerticalScroll - scrollVerticalDistance < 0) {
                            scrollVerticalDistance = mTotalVerticalScroll;
                        }
                    } else if (scrollVerticalDistance < 0) {
                        if (mTotalVerticalScroll + viewBottom - scrollVerticalDistance > parentViewBottom) {
                            scrollVerticalDistance = mTotalVerticalScroll + viewBottom - parentViewBottom;
                        }
                    }
                }

                mTotalVerticalScroll = mTotalVerticalScroll - scrollVerticalDistance;

                Log.e(TAG, "scrollVerticalDistance: " + scrollVerticalDistance);
                scrollBy(0, scrollVerticalDistance);
                mVerticalLastMove = mVerticalMove;
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            int verticalY = mScroller.getCurrY();
            if (getChildCount() > 0) {
                View view = getChildAt(0);
                int top = view.getTop();
                int bottom = view.getBottom();
                int parentViewBottom = getBottom();
                String logMsg = String.format(Locale.getDefault(), "verticalY: %s; top: %s; bottom: %s; parentViewBottom: %s",
                        verticalY, top, bottom, parentViewBottom);
                Log.e(TAG, logMsg);
                if (verticalY > 0) {
                    if (top - verticalY < 0) {
                        verticalY = top;
                    }
                } else if (verticalY < 0) {
                    if (bottom - verticalY > parentViewBottom) {
                        verticalY = bottom - parentViewBottom;
                    }
                }
            }
            scrollBy(0, verticalY);
            invalidate();
        }
    }

    public void resetScrollVertical() {
        scrollTo(0, 0);
    }
}
