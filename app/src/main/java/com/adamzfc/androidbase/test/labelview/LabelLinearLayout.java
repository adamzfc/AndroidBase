package com.adamzfc.androidbase.test.labelview;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by adamzfc on 4/19/17.
 */

public class LabelLinearLayout extends LinearLayout {
    private LabelViewHelper mLabelViewHelper;
    private boolean mLabelVisable = true;

    public LabelLinearLayout(Context context) {
        this(context, null);
    }

    public LabelLinearLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLabelViewHelper = new LabelViewHelper(context, attrs);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (mLabelVisable) {
            mLabelViewHelper.drawLabel(this, canvas);
        }
    }

    /**
     * set text content
     *
     * @param content content
     */
    public void setTextContent(String content) {
        mLabelViewHelper.setTextContent(content);
        invalidate();
    }

    /**
     * set text title
     *
     * @param title title
     */
    public void setTextTitle(String title) {
        mLabelViewHelper.setTextTitle(title);
        invalidate();
    }

    /**
     * set label background color
     *
     * @param color color
     */
    public void setLabelBackGroundColor(int color) {
        mLabelViewHelper.setLabelBackGroundColor(color);
        invalidate();
    }

    /**
     * set label visable
     *
     * @param visable true or false
     */
    public void setLabelVisable(boolean visable) {
        mLabelVisable = visable;
        postInvalidate();
    }
}
