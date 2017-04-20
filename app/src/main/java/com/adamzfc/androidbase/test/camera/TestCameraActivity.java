package com.adamzfc.androidbase.test.camera;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.adamzfc.androidbase.R;

/**
 * Created by adamzfc on 4/20/17.
 */

public class TestCameraActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_camera);
        if (null == savedInstanceState) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }
    }
}
