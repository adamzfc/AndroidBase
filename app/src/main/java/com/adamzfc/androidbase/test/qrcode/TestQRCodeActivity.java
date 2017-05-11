package com.adamzfc.androidbase.test.qrcode;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.adamzfc.androidbase.R;

/**
 * Created by adamzfc on 5/11/17.
 */

public class TestQRCodeActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_qrcode);
        findViewById(R.id.bt_scan).setOnClickListener(v -> {
//            Intent intent = new Intent(this, CaptureActivity.class);
//            startActivityForResult(intent, 0);
        });
    }
}
