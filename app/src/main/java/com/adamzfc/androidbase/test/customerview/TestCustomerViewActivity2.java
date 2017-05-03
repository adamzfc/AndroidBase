package com.adamzfc.androidbase.test.customerview;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.adamzfc.androidbase.R;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by adamzfc on 5/2/17.
 */

public class TestCustomerViewActivity2 extends Activity {
    private WuziqiPanel mWuziqiPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_customer_view2);

        mWuziqiPanel = (WuziqiPanel) findViewById(R.id.panel);

    }

}
