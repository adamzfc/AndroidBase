package com.adamzfc.base.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.adamzfc.base.R;

/**
 * Created by adamzfc on 4/19/17.
 */

public final class ConvertUtils {
    private ConvertUtils() {
    }

    /**
     * create bitmap from xml drawable
     * <strong>NOTE: the xml drawable must set size attribute</strong>
     * <a>http://stackoverflow.com/questions/10111073/how-to-get-a-bitmap-from-a-drawable-defined-in-a-xml</a>
     * @param context context
     * @param drawableRes xml drawable
     * @return bitmap
     */
    public static Bitmap drawableRes2Bitmap(@NonNull Context context, @DrawableRes int drawableRes) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableRes);
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * only use for drawable which is instanceof BitmapDrawable
     * @param drawable input drawable
     * @return bitmap or null
     */
    public static @Nullable Bitmap drawable2Bitmap(@Nullable Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return  ((BitmapDrawable)drawable).getBitmap();
        } else {
            return null;
        }
    }

    /**
     * convert view to bitmap
     * @param view view
     * @return bitmap or null
     */
    public static @Nullable Bitmap view2Bitmap(@Nullable View view) {
        if (view == null) return null;
        Bitmap ret = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(ret);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return ret;
    }

    /**
     * dp to int px
     * @param dp dp
     * @param context context
     * @return int px
     */
    public static int dp2px(float dp, @NonNull Context context) {
        return Math.round(dp * context.getResources().getDisplayMetrics().density);
    }

    /**
     * px to int dp
     * @param px px
     * @param context context
     * @return int dp
     */
    public static int px2dp(float px, @NonNull Context context) {
        return Math.round(px * context.getResources().getDisplayMetrics().scaledDensity);
    }

    /**
     * px to int sp
     * @param px px
     * @param context context
     * @return int sp
     */
    public static int px2sp(float px, @NonNull Context context) {
        return Math.round(px / context.getResources().getDisplayMetrics().scaledDensity);
    }
}
