package com.adamzfc.androidbase.test.drawable;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by adamzfc on 4/17/17.
 */

public class LauncherIconDrawable extends Drawable {
    private Drawable mDrawable;
    private float mPercent;

    private ColorFilter mDefaultColorFilter;
    private ColorFilter mPercentColorFilter;

    public LauncherIconDrawable(Drawable drawable) {
        mDrawable = drawable;
    }

    /**
     * set default color
     * @param defaultColor default color
     * @return LauncherIconDrawable
     */
    public LauncherIconDrawable setDefaultColor(@ColorInt int defaultColor) {
        return setDefaultColor(defaultColor, PorterDuff.Mode.MULTIPLY);
    }

    /**
     * set default color
     * @param defaultColor defautl color
     * @param mode mode
     * @return LauncherIconDrawable
     */
    public LauncherIconDrawable setDefaultColor(@ColorInt int defaultColor, PorterDuff.Mode mode) {
        mDefaultColorFilter = new PorterDuffColorFilter(defaultColor, mode);
        invalidateSelf();
        return this;
    }

    /**
     * set percent color
     * @param percentColor percent color
     * @return LauncherIconDrawable
     */
    public LauncherIconDrawable setPercentColor(@ColorInt int percentColor) {
        return setPercentColor(percentColor, PorterDuff.Mode.MULTIPLY);
    }

    /**
     * set percent color
     * @param percentColor percent color
     * @param mode mode
     * @return LauncherIconDrawable
     */
    public LauncherIconDrawable setPercentColor(@ColorInt int percentColor, PorterDuff.Mode mode) {
        mPercentColorFilter = new PorterDuffColorFilter(percentColor, mode);
        invalidateSelf();
        return this;
    }

    /**
     * set current percent
     * @param curPercent current percent
     */
    public void setCurPercent(@FloatRange(from = 0f, to = 1f) float curPercent) {
        mPercent = curPercent;
        invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        if (mDrawable == null) {
            return;
        }

        Rect rect = getBounds();
        int curOffset = (int) (mPercent * rect.height());
        canvas.save();
        canvas.clipRect(rect.left, rect.top, rect.width(), rect.height() - curOffset);
        mDrawable.setColorFilter(mDefaultColorFilter);
        mDrawable.draw(canvas);
        canvas.restore();

        canvas.save();
        canvas.clipRect(rect.left, rect.height() - curOffset, rect.width(), rect.height());
        mDrawable.setColorFilter(mPercentColorFilter);
        mDrawable.draw(canvas);
        canvas.restore();
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        if (mDrawable != null) {
            mDrawable.setBounds(getBounds());
        }
    }

    @Override
    public void invalidateSelf() {
        super.invalidateSelf();
        if (mDrawable != null) {
            mDrawable.invalidateSelf();
        }
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {
        if (mDrawable != null) {
            mDrawable.setAlpha(alpha);
        }
    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {
        if (mDrawable != null) {
            mDrawable.setColorFilter(colorFilter);
        }
    }

    @Override
    public int getIntrinsicHeight() {
        if (mDrawable != null) {
            return mDrawable.getIntrinsicHeight();
        }
        return -1;
    }

    @Override
    public int getIntrinsicWidth() {
        if (mDrawable != null) {
            return mDrawable.getIntrinsicWidth();
        }
        return -1;
    }

    @Override
    public int getOpacity() {
        if (mDrawable != null) {
            return mDrawable.getOpacity();
        }
        return PixelFormat.TRANSLUCENT;
    }
}
