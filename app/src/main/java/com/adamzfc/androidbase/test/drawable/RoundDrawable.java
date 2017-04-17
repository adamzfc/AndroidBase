package com.adamzfc.androidbase.test.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by adamzfc on 4/17/17.
 */

public class RoundDrawable extends Drawable {
    private Paint mPaint;
    private Bitmap mBitmap;
    private BitmapShader mShader;
    private Matrix mMatrix;
    private float mCornerRadius;

    public RoundDrawable(Bitmap mBitmap) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mBitmap = mBitmap;
        mShader = new BitmapShader(mBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mPaint.setShader(mShader);
        mMatrix = new Matrix();
    }

    public RoundDrawable setCornerRadius(@FloatRange(from = 0f) float radius) {
        mCornerRadius = radius;
        invalidateSelf();
        return this;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect rect = getBounds();
        mMatrix.preTranslate(rect.left, rect.top);
        mMatrix.preScale(rect.width() * 1.0f / mBitmap.getWidth(),
                rect.height() * 1.0f / mBitmap.getHeight());
        mShader.setLocalMatrix(mMatrix);
        canvas.drawRoundRect(new RectF(rect), mCornerRadius, mCornerRadius, mPaint);
    }

    @Override
    public int getIntrinsicHeight() {
        return mBitmap.getHeight();
    }

    @Override
    public int getIntrinsicWidth() {
        return mBitmap.getWidth();
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
