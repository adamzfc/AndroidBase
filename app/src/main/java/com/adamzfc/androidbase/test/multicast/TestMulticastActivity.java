package com.adamzfc.androidbase.test.multicast;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.adamzfc.androidbase.R;

/**
 * Created by adamzfc on 5/8/17.
 */

public class TestMulticastActivity extends Activity {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                mDisplay.append("receive: " + ((byte[])msg.obj)[0]);
            }
        }
    };
    private TextView mDisplay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_multicast);
        mDisplay = (TextView) findViewById(R.id.tv_display);
        new MulticastReceiver(mHandler, this).start();
        findViewById(R.id.bt_send).setOnClickListener(v -> {
            String data = "test";
            new MulticastSender(data, this).start();
            mDisplay.append("send: " + data);
        });
    }

}
