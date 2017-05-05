package com.adamzfc.androidbase.test.jni;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.adamzfc.androidbase.R;

/**
 * Created by adamzfc on 5/5/17.
 */

public class TestJniActivity extends Activity {
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_jni);
        TextView text = (TextView) findViewById(R.id.tv_text);
        text.setText(stringFromJNI());
    }

    public native String stringFromJNI();
}
