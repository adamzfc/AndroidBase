package com.adamzfc.androidbase.test.ripple;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by adamzfc on 5/1/17.
 */

public class RippleDrawable extends Drawable {
    private @IntRange(from = 0, to = 255) int mAlpha = 255;
    private int mRippleColor;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mRipplePointX, mRipplePointY;
    private float mRippleRadius = 200;

    private Bitmap mBitmap;

    public RippleDrawable() {
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
    }

    public void setRippleColor(int color) {
        mRippleColor = color;
        onColorOrAlphaChange();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
//        canvas.drawColor(Color.RED);
        canvas.drawCircle(mRipplePointX, mRipplePointY,
                mRippleRadius, mPaint);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        mAlpha = alpha;
        onColorOrAlphaChange();
    }

    private void onColorOrAlphaChange() {
        mPaint.setColor(mRippleColor);
        if (mAlpha != 255) {
            int pAlpha = mPaint.getAlpha();
//            pAlpha = Color.alpha(mRippleColor);
            int realAlpha = (int) (pAlpha * (mAlpha / 255f));
            mPaint.setAlpha(realAlpha);
        }
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        if (mPaint.getColorFilter() != colorFilter) {
            mPaint.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getAlpha() {
        return mAlpha;
    }

    @Override
    public int getOpacity() {
        int alpha = mPaint.getAlpha();
        if (alpha == 255) {
            return PixelFormat.OPAQUE;
        } else if (alpha == 0) {
            return PixelFormat.TRANSPARENT;
        } else {
            return PixelFormat.TRANSLUCENT;
        }
    }
}
