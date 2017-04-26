package com.adamzfc.androidbase.test.slidemenu;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by adamzfc on 4/26/17.
 */

public class SlideMenu extends ViewGroup {
    private View menuView, mainView;
    private int screenHeight = 0;
    public SlideMenu(Context context) {
        super(context);
        init();
    }

    public SlideMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }

    /**
     * Called when direct child view inflated
     * NOTE can not get the size of child view
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        menuView = getChildAt(0);
        mainView = getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // measure all child view
        menuView.measure(menuView.getLayoutParams().width, heightMeasureSpec);
        // use param of slidemenu
        mainView.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        menuView.layout(-menuView.getLayoutParams().width, 0, 0, menuView.getMeasuredHeight());
    }
}
