package com.adamzfc.androidbase.test.magicfloat;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.adamzfc.androidbase.R;
import com.adamzfc.base.util.ConvertUtils;

/**
 * Created by adamzfc on 4/19/17.
 */

public class TestMagicFloatViewActivity extends Activity implements View.OnClickListener {
    private MagicFlyLinearLayout mRainLinearLayout;
    private Button mRainButton;

    private MagicFlyLinearLayout mFlyLinearLayout;
    private Button mFlyButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_magicfloat);

        mFlyLinearLayout = (MagicFlyLinearLayout) findViewById(R.id.rain_layout2);
        mFlyButton = (Button) findViewById(R.id.bt_rain2);
        mFlyButton.setOnClickListener(this);

        mRainLinearLayout = (MagicFlyLinearLayout) findViewById(R.id.rain_layout1);
        mRainButton = (Button) findViewById(R.id.bt_rain1);
        mRainButton.setOnClickListener(this);

        mRainLinearLayout.addDrawable(ConvertUtils.drawableRes2Bitmap(this, R.drawable.circle_orange));
        mRainLinearLayout.addDrawable(ConvertUtils.drawableRes2Bitmap(this, R.drawable.circle_red));
        mRainLinearLayout.addDrawable(ConvertUtils.drawableRes2Bitmap(this, R.drawable.circle_blue));
        mRainLinearLayout.addDrawable(ConvertUtils.drawableRes2Bitmap(this, R.drawable.circle_green));

        mFlyLinearLayout.addDrawable(ConvertUtils.drawableRes2Bitmap(this, R.drawable.circle_orange));
        mFlyLinearLayout.addDrawable(ConvertUtils.drawableRes2Bitmap(this, R.drawable.circle_red));
        mFlyLinearLayout.addDrawable(ConvertUtils.drawableRes2Bitmap(this, R.drawable.circle_blue));
        mFlyLinearLayout.addDrawable(ConvertUtils.drawableRes2Bitmap(this, R.drawable.circle_green));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_rain2:
                mFlyLinearLayout.flying();
                break;
            case R.id.bt_rain1:
                for (int i = 0; i < 18; i ++) {
                    mRainLinearLayout.flying();
                }
                break;
            default:
                break;
        }
    }
}
