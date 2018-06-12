package com.advanced.demo.scrollView;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * @author by morton_ws on 2018/4/29.
 */

public class DispatchRecyclerView extends RecyclerView {
    private final static String TAG = "Dispatch.RecyclerView";

    public DispatchRecyclerView(Context context) {
        this(context, null);
    }

    public DispatchRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        boolean dispatchEventResult = super.dispatchTouchEvent(ev);
        Log.e(TAG, "dispatchTouchEvent; dispatchEventResult: " + dispatchEventResult);
        return dispatchEventResult;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        boolean interceptEventResult = super.onInterceptTouchEvent(e);
        Log.e(TAG, "onInterceptTouchEvent; interceptEventResult: " + interceptEventResult);
        return interceptEventResult;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean touchEventResult = super.onTouchEvent(e);
        String motionEventAction = "other";
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            motionEventAction = "ACTION_DOWN";
        } else if (e.getAction() == MotionEvent.ACTION_MOVE) {
            motionEventAction = "ACTION_MOVE";
        }
        Log.e(TAG, "onTouchEvent; touchEventResult: " + touchEventResult + "; motionEventAction: " + motionEventAction);
        return touchEventResult;
    }
}
