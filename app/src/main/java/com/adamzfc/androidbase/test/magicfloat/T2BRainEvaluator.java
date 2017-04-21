package com.adamzfc.androidbase.test.magicfloat;

import android.graphics.Bitmap;
import android.graphics.PointF;

import java.util.Random;

/**
 * Created by adamzfc on 4/19/17.
 */

public class T2BRainEvaluator extends B2TScatterEvaluator {

    @SuppressWarnings({"membername", "multiplevariabledeclarations"})
    private PointF pointF1, pointF2;

    public T2BRainEvaluator(int width, int heigh, Bitmap bitmap) {
        super(width, heigh, bitmap);
        int realH = getMeasuredHeigh() - getBitmap().getHeight();
        pointF1 = getBezierP01PointF(0, realH / 2);
        pointF2 = getBezierP01PointF(realH / 2, realH);
    }

    private PointF getBezierP01PointF(int min, int max) {

        PointF pointF = new PointF();
        pointF.x = new Random().nextInt(getMeasuredWith() - getBitmap().getWidth());
        pointF.y = randomRange(min, max);
        return pointF;
    }

    private int randomRange(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }

    @Override
    public ValueState evaluate(float fraction, ValueState startValue, ValueState endValue) {
        float timeStart = 1.0f - fraction;

        ValueState valueState = new ValueState();
        PointF point = new PointF();
        point.x = timeStart * timeStart * timeStart * (startValue.pointF.x) + 3
                * timeStart * timeStart * fraction * (pointF1.x) + 3 * timeStart
                * fraction * fraction * (pointF2.x) + fraction * fraction * fraction * (endValue.pointF.x);

        point.y = timeStart * timeStart * timeStart * (startValue.pointF.y) + 3
                * timeStart * timeStart * fraction * (pointF1.y) + 3 * timeStart
                * fraction * fraction * (pointF2.y) + fraction * fraction * fraction * (endValue.pointF.y);
        valueState.pointF = point;
        valueState.scale = 1;
        valueState.alpha = 255;
        valueState.bitmap = getBitmap();
        return valueState;
    }

    @Override
    public ValueState createAnimatorStart() {
        ValueState valueState = new ValueState();
        valueState.bitmap = getBitmap();
        valueState.alpha = 255;
        valueState.scale = 1f;
        valueState.pointF = new PointF(new Random().nextInt(getMeasuredWith()), -getBitmap().getHeight());
        return valueState;
    }

    @Override
    public ValueState createAnimatorEnd() {
        ValueState valueState = new ValueState();
        valueState.bitmap = getBitmap();
        valueState.alpha = 255;
        valueState.scale = 1f;
        valueState.pointF = new PointF(new Random().nextInt(getMeasuredWith()), getMeasuredHeigh());
        return valueState;
    }
}
