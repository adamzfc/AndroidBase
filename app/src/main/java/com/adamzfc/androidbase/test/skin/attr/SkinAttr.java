package com.adamzfc.androidbase.test.skin.attr;

import android.view.View;

/**
 * Created by adamzfc on 4/28/17.
 */

public class SkinAttr {
    private String mResName;
    private SkinAttrType mType;

    public SkinAttr(String resName, SkinAttrType type) {
        mResName = resName;
        mType = type;
    }

    public String getResName() {
        return mResName;
    }

    public void setResName(String resName) {
        mResName = resName;
    }

    public SkinAttrType getType() {
        return mType;
    }

    public void setType(SkinAttrType type) {
        mType = type;
    }

    public void apply(View view) {
        mType.apply(view, mResName);
    }
}
