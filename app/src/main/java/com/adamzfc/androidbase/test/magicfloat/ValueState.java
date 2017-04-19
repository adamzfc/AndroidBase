package com.adamzfc.androidbase.test.magicfloat;

import android.graphics.Bitmap;
import android.graphics.PointF;

/**
 * Created by adamzfc on 4/19/17.
 */

public class ValueState {
    public Bitmap bitmap;
    public int alpha;
    public float scale;
    public PointF pointF;

    @Override
    public String toString() {
        return "{alpha="+alpha+", scale="+scale+", pointF="+pointF.toString()+"}";
    }
}
