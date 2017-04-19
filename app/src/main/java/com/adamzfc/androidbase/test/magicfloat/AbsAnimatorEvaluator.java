package com.adamzfc.androidbase.test.magicfloat;

import android.animation.TypeEvaluator;
import android.graphics.Bitmap;

/**
 * Created by adamzfc on 4/19/17.
 */

public abstract class AbsAnimatorEvaluator implements TypeEvaluator<ValueState> {
    private int mMeasuredWith;
    private int mMeasuredHeigh;
    private Bitmap mBitmap;

    public AbsAnimatorEvaluator(int width, int heigh, Bitmap bitmap) {
        this.mMeasuredWith = width;
        this.mMeasuredHeigh = heigh;
        this.mBitmap = bitmap;
    }

    public int getMeasuredWith() {
        return mMeasuredWith;
    }

    public int getMeasuredHeigh() {
        return mMeasuredHeigh;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public abstract ValueState createAnimatorStart();
    public abstract ValueState createAnimatorEnd();
}
