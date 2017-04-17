package com.adamzfc.androidbase.test.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by adamzfc on 4/17/17.
 */

public class CustomView extends View {
    private Drawable mDrawable;

    public CustomView(Context context) {
        this(context, null);
    }

    public CustomView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        mDrawable = new SimpleRoundDrawable();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mDrawable.setBounds(getLeft(), getTop(), getRight(), getBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mDrawable.draw(canvas);
    }
}
