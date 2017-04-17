package com.adamzfc.androidbase.test.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by adamzfc on 4/17/17.
 */

public class ReflectionDrawable extends Drawable {
    private Bitmap mSrcBitmap;
    private Bitmap mReflectBitmap;

    private Paint mPaint;
    private int mReflectionHeight;

    public ReflectionDrawable(Bitmap bitmap) {
        mSrcBitmap = bitmap;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Matrix matrix = new Matrix();
        matrix.preScale(1.0f, -1.0f);
        mReflectBitmap = Bitmap.createBitmap(mSrcBitmap, 0, 0,
                mSrcBitmap.getWidth(), mSrcBitmap.getHeight(),
                matrix, true);
    }

    public ReflectionDrawable setReflectionHeight(@IntRange(from = 0) int height) {
        mReflectionHeight = height;
        invalidateSelf();
        return this;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect rect = getBounds();
        Rect rectSrc = new Rect(rect.left, rect.top, rect.right, rect.bottom - mReflectionHeight);
        Rect rectReflect = new Rect(rect.left, rect.bottom - mReflectionHeight, rect.right, rect.bottom);

        canvas.drawBitmap(mSrcBitmap, new Rect(0, 0, mSrcBitmap.getWidth(), mSrcBitmap.getHeight()),
                rectSrc, null);

        canvas.drawBitmap(mReflectBitmap, new Rect(0, 0, mReflectBitmap.getWidth(), mReflectBitmap.getHeight()),
                rectReflect, null);
        mPaint.setShader(new LinearGradient(rectReflect.left, rectReflect.top,
                rectReflect.left, rectReflect.bottom,
                Color.TRANSPARENT, Color.BLACK,
                Shader.TileMode.CLAMP));
        canvas.drawRect(rectReflect, mPaint);
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
