package com.adamzfc.androidbase.test.drawable;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

/**
 * Created by adamzfc on 4/17/17.
 */

public class IconDrawable extends Drawable {
    private Paint mTextPaint;
    private String mContentText;
    private int mFontSize;
    private int mBgColor = Color.GREEN;
    private int mTextColor = Color.WHITE;

    private Bitmap mContentBitmap;

    private Path mClipPath;
    private Matrix mMatrix;

    public IconDrawable() {
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mClipPath = new Path();
        mMatrix = new Matrix();
    }

    /**
     * set text label
     * @param string string label
     * @return IconDrawable
     */
    public IconDrawable setTextLabel(String string) {
        mContentText = string;
        mContentBitmap = null;
        invalidateSelf();
        return this;
    }

    /**
     * set icon label
     * @param bitmap bitmap label
     * @return IconDrawable
     */
    public IconDrawable setIconLabel(Bitmap bitmap) {
        mContentBitmap = bitmap;
        mContentText = null;
        invalidateSelf();
        return this;
    }

    /**
     * set text font size
     * @param size font size
     * @return IconDrawable
     */
    public IconDrawable setTextFontSize(int size) {
        mFontSize = size;
        invalidateSelf();
        return this;
    }

    /**
     * set text color
     * @param color text color
     * @return IconDrawable
     */
    public IconDrawable setTextColor(int color) {
        mTextColor = color;
        invalidateSelf();
        return this;
    }

    /**
     * set background color
     * @param color background color
     * @return IconDrawable
     */
    public IconDrawable setBackgroundColor(int color) {
        mBgColor = color;
        invalidateSelf();
        return this;
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        Rect rect = getBounds();

        int count = canvas.saveLayer(new RectF(rect), null, Canvas.ALL_SAVE_FLAG);
        canvas.translate(rect.left, rect.top);
        mClipPath.reset();
        mClipPath.addCircle(rect.width() / 2, rect.height() / 2,
                Math.min(rect.width(), rect.height()) / 2, Path.Direction.CCW);
        canvas.clipPath(mClipPath);

        if (mContentBitmap == null && !TextUtils.isEmpty(mContentText)) {
            mTextPaint.setColor(mBgColor);
            canvas.drawRect(rect, mTextPaint);
            int fontSize = this.mFontSize <= 0 ? Math.min(rect.width(), rect.height()) / 2
                    : this.mFontSize;
            mTextPaint.setTextSize(fontSize);
            mTextPaint.setColor(mTextColor);
            canvas.drawText(mContentText, rect.width() / 2,
                    rect.height() / 2 - ((mTextPaint.descent() + mTextPaint.ascent()) / 2),
                    mTextPaint);
        } else {
            mMatrix.setScale(rect.width() * 1.0f / mContentBitmap.getWidth(),
                    rect.height() * 1.0f / mContentBitmap.getHeight());
            Bitmap scaleContentBitmap = Bitmap.createBitmap(mContentBitmap, 0, 0,
                    mContentBitmap.getWidth(), mContentBitmap.getHeight(),
                    mMatrix, true);
            canvas.drawBitmap(scaleContentBitmap, rect, rect, null);
        }
        canvas.restoreToCount(count);
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        mTextPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        mTextPaint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public int getIntrinsicHeight() {
        Rect rect = getContentRect();
        return (rect.width() > 0 ? rect.width() : -1);
    }

    @Override
    public int getIntrinsicWidth() {
        Rect rect = getContentRect();
        return (rect.height() > 0 ? rect.height() : -1);
    }

    private Rect getContentRect() {
        Rect rect = new Rect();
        if (!TextUtils.isEmpty(mContentText)) {
            mTextPaint.getTextBounds(mContentText, 0, mContentText.length(), rect);
        } else {
            rect.set(0, 0, mContentBitmap.getWidth(), mContentBitmap.getHeight());
        }
        return rect;
    }
}
