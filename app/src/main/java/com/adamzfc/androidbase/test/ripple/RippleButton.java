package com.adamzfc.androidbase.test.ripple;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

/**
 * Created by adamzfc on 5/1/17.
 */

public class RippleButton extends AppCompatButton {

    private RippleDrawable mRippleDrawable;

    public RippleButton(Context context) {
        this(context, null);
    }

    public RippleButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRippleDrawable = new RippleDrawable();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mRippleDrawable.draw(canvas);
        super.onDraw(canvas);
    }
}
