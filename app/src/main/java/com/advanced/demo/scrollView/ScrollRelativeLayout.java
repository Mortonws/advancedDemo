package com.advanced.demo.scrollView;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    private final static String TAG = "Scroll.RelativeLayout";

    private final static int ACTION_MOVE_UP = 0x1 << 1;

    private Scroller mScroller;

    @SuppressWarnings("FieldCanBeLocal")
    private float mVerticalDown;
    private float mVerticalLastMove;
    @SuppressWarnings("FieldCanBeLocal")
    private float mVerticalMove;

    private RecyclerView mCanScrollRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private boolean mShouldHookMotionEvent = false;

    public ScrollRelativeLayout(Context context) {
        this(context, null);
    }

    public ScrollRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.mCanScrollRecyclerView = recyclerView;
        if (mCanScrollRecyclerView != null) {
            mLayoutManager = (LinearLayoutManager) mCanScrollRecyclerView.getLayoutManager();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mVerticalDown = event.getRawY();
                mVerticalLastMove = mVerticalDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mVerticalMove = event.getRawY();
                int scrollVerticalDistance = (int) (mVerticalLastMove - mVerticalMove);
//                mScroller.startScroll(0, mScroller.getStartY(), 0, scrollVerticalDistance);
//                invalidate();

                if (mCanScrollRecyclerView != null) {
                    int[] recyclerViewLocationInWindow = new int[2];
                    mCanScrollRecyclerView.getLocationInWindow(recyclerViewLocationInWindow);
                    int recyclerViewTop = recyclerViewLocationInWindow[1];
                    int viewBottom = recyclerViewTop + mCanScrollRecyclerView.getHeight();

                    int[] parentViewLocationInWindow = new int[2];
                    getLocationInWindow(parentViewLocationInWindow);
                    int parentViewTop = parentViewLocationInWindow[1];
                    int parentViewBottom = parentViewTop + getHeight();

                    int position = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                    int actionMove = 0x1;
                    if (scrollVerticalDistance >= 0) {
                        actionMove = actionMove << 1;
                        if (recyclerViewTop - scrollVerticalDistance < parentViewTop) {
                            scrollVerticalDistance = recyclerViewTop - parentViewTop;
                        }
                    } else {
                        actionMove = actionMove << 2;
                        if (viewBottom - scrollVerticalDistance > parentViewBottom) {
                            scrollVerticalDistance = viewBottom - parentViewBottom;
                        }
                    }
                    scrollBy(0, scrollVerticalDistance);
                    if (actionMove == ACTION_MOVE_UP) {
                        if (position != 0 || recyclerViewTop == parentViewTop || scrollVerticalDistance < 0.5) {
                            if (!mShouldHookMotionEvent) {
                                mShouldHookMotionEvent = true;

                                event.setAction(MotionEvent.ACTION_DOWN);
                                ScrollRelativeLayout.super.dispatchTouchEvent(event);
                                event.setAction(MotionEvent.ACTION_MOVE);
                            }

                        } else {
                            mShouldHookMotionEvent = false;
                            scrollBy(0, scrollVerticalDistance);
                        }
                    } else {
                        if (position != 0) {
                            if (!mShouldHookMotionEvent) {
                                mShouldHookMotionEvent = true;

                                event.setAction(MotionEvent.ACTION_DOWN);
                                ScrollRelativeLayout.super.dispatchTouchEvent(event);
                                event.setAction(MotionEvent.ACTION_MOVE);
                            }
                        } else {
                            mShouldHookMotionEvent = false;
                            if (Math.abs(scrollVerticalDistance) > 5) {
                                scrollBy(0, scrollVerticalDistance);
                            }
                        }
                    }
                }
                mVerticalLastMove = mVerticalMove;
                break;
            default:
                mShouldHookMotionEvent = false;
                break;
        }
        super.dispatchTouchEvent(event);
        return true;
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
}
