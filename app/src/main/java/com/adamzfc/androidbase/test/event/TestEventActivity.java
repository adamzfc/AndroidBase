package com.adamzfc.androidbase.test.event;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.adamzfc.androidbase.R;

/**
 * Created by adamzfc on 4/14/17.
 */
public class TestEventActivity extends Activity implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "Activity Event Test";
    private TestButton mButton;
    private TestLinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_event);

        mButton = (TestButton) this.findViewById(R.id.button);
        mLayout = (TestLinearLayout) this.findViewById(R.id.layout);

        mButton.setOnClickListener(this);
        mLayout.setOnClickListener(this);

        mButton.setOnTouchListener(this);
        mLayout.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.i(TAG, "onClick----v=" + v);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i(TAG, "onTouch--action="+event.getAction()+"--v="+v);
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.i(TAG, "MainActivity--dispatchTouchEvent--action=" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void onUserInteraction() {
        Log.i(TAG, "MainActivity--onUserInteraction");
        super.onUserInteraction();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i(TAG, "MainActivity--onTouchEvent--action="+event.getAction());
        return super.onTouchEvent(event);
    }
}
