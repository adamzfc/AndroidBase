package com.adamzfc.base;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;

/**
 * base dialog
 * Created by adamzfc on 3/15/17.
 */

public abstract class BaseDialog extends Dialog {
    public static final int MATCH_PARENT = WindowManager.LayoutParams.MATCH_PARENT;
    public static final int WRAP_CONTENT = WindowManager.LayoutParams.WRAP_CONTENT;
    protected Window mWindow;
    protected final Context mContext;
    protected LayoutInflater mInflater;

    public BaseDialog(Context context) {
        this(context, R.style.dialog_base_theme);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        init();
    }

    private void init() {
        mWindow = getWindow();
        mWindow.setContentView(mInflater.inflate(getContentLayoutId(), null));

    }

    public void setLayoutParams(int height, int width) {
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mWindow.getAttributes());
        lp.height = height;
        lp.width = width;
        mWindow.setAttributes(lp);
    }

    public void setLayoutXY(int x, int y) {
        mWindow.setGravity(Gravity.START | Gravity.TOP);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(mWindow.getAttributes());
        lp.x = x;
        lp.y = y;
        mWindow.setAttributes(lp);
    }

    public void setDialogGravity(int gravity) {
        mWindow.setGravity(gravity);
    }

    protected abstract int getContentLayoutId();

}
