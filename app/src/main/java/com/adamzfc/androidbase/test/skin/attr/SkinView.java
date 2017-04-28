package com.adamzfc.androidbase.test.skin.attr;

import android.view.View;

import java.util.List;

/**
 * Created by adamzfc on 4/28/17.
 */

public class SkinView {
    private View mView;
    private List<SkinAttr> mAttrs;

    public SkinView(View view, List<SkinAttr> attrs) {
        mView = view;
        mAttrs = attrs;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
    }

    public List<SkinAttr> getAttrs() {
        return mAttrs;
    }

    public void setAttrs(List<SkinAttr> attrs) {
        mAttrs = attrs;
    }

    public void apply() {
        for (SkinAttr attr : mAttrs) {
            attr.apply(mView);
        }
    }
}
