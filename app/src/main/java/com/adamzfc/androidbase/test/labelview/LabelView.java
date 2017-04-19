package com.adamzfc.androidbase.test.labelview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by adamzfc on 4/19/17.
 */

public class LabelView extends View {
    private LabelViewHelper mLabelViewHelper;

    public LabelView(Context context) {
        this(context, null);
    }

    public LabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mLabelViewHelper = new LabelViewHelper(context, attrs);
    }

    /**
     * set text content
     * @param content content
     */
    public void setTextContent(String content) {
        mLabelViewHelper.setTextContent(content);
        invalidate();
    }

    /**
     * set text title
     * @param title title
     */
    public void setTextTitle(String title) {
        mLabelViewHelper.setTextTitle(title);
        invalidate();
    }

    /**
     * set label background color
     * @param color color
     */
    public void setLabelBackGroundColor(int color) {
        mLabelViewHelper.setLabelBackGroundColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mLabelViewHelper.drawLabel(this, canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int rotateViewWH = (int) (mLabelViewHelper.getBgTriangleHeight() * Math.sqrt(2));
        setMeasuredDimension(rotateViewWH, rotateViewWH);
    }
}
