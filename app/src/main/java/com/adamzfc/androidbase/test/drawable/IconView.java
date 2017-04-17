package com.adamzfc.androidbase.test.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by adamzfc on 4/17/17.
 */

public class IconView extends View {
    private IconDrawable mDrawable;

    public IconView(Context context) {
        this(context, null);
    }

    public IconView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IconDrawable getIconDrawable() {
        return mDrawable;
    }

    private void init() {
        setLayerType(LAYER_TYPE_SOFTWARE, null);
        mDrawable = new IconDrawable();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDrawable.setBounds(new Rect(0, 0, w, h));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDrawable.draw(canvas);
    }
}
