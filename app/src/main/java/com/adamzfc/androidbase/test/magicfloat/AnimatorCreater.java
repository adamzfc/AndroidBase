package com.adamzfc.androidbase.test.magicfloat;

import android.graphics.Bitmap;

/**
 * Created by adamzfc on 4/19/17.
 */

public class AnimatorCreater {
    public static final int TYPE_B2T_SCATTER = 0;
    public static final int TYPE_T2B_RAIN_NORMAL = 1;

    public static AbsAnimatorEvaluator create(int animType, int width, int heigh, Bitmap bitmap) {
        AbsAnimatorEvaluator evaluator = null;
        switch (animType) {
            case TYPE_B2T_SCATTER:
                evaluator = new B2TScatterEvaluator(width, heigh, bitmap);
                break;
            case TYPE_T2B_RAIN_NORMAL:
                evaluator = new T2BRainEvaluator(width, heigh, bitmap);
                break;
            default:
                evaluator = new B2TScatterEvaluator(width, heigh, bitmap);
                break;
        }
        return evaluator;
    }
}
