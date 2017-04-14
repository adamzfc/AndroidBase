package com.adamzfc.androidbase.test.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by adamzfc on 4/14/17.
 */

public class TestLinearLayout extends LinearLayout {
    private static final String TAG = "Activity Event Test";
    public TestLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.i(TAG, "TestLinearLayout--onInterceptTouchEvent--action="+ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "TestLinearLayout--dispatchTouchEvent--action=" + event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "TestLinearLayout--onTouchEvent--action="+event.getAction());
        return super.onTouchEvent(event);
    }
}
