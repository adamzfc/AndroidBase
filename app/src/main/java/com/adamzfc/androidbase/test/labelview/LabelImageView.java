package com.adamzfc.androidbase.test.labelview;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by adamzfc on 4/19/17.
 */

public class LabelImageView extends AppCompatImageView {
    private LabelViewHelper mLabelViewHelper;
    private boolean mLabelVisable = true;

    public LabelImageView(Context context) {
        this(context, null);
    }

    public LabelImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLabelViewHelper = new LabelViewHelper(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mLabelVisable) {
            mLabelViewHelper.drawLabel(this, canvas);
        }
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
     * @param color background color
     */
    public void setLabelBackGroundColor(int color) {
        mLabelViewHelper.setLabelBackGroundColor(color);
        invalidate();
    }

    /**
     * set whether the label is visable
     * @param visable true visable or false unvisable
     */
    public void setLabelVisable(boolean visable) {
        mLabelVisable = visable;
        postInvalidate();
    }
}
