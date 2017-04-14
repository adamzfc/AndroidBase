package com.adamzfc.androidbase.test.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by adamzfc on 4/14/17.
 */

public class TestButton extends android.support.v7.widget.AppCompatButton {
    private static final String TAG = "Activity Event Test";
    public TestButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.i(TAG, "TestButton--dispatchTouchEvent--action="+event.getAction());
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "TestButton--onTouchEvent--action="+event.getAction());
        return super.onTouchEvent(event);
    }
}
