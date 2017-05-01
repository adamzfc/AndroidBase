package com.adamzfc.androidbase.test.skin;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;

/**
 * Created by adamzfc on 4/28/17.
 */

public class ResourcesManager {
    private Resources mResources;
    private String mPkgName;

    private String mSuffix;

    public ResourcesManager(Resources resources, String pkgName, String suffix) {
        mResources = resources;
        mPkgName = pkgName;
        if (suffix == null) {
            mSuffix = "";
        }
        mSuffix = suffix;
    }

    public Drawable getDrawableByResName(String name) {
        name = appendSuffix(name);
        try {
            return mResources.getDrawable(mResources.getIdentifier(name, "drawable", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String appendSuffix(String name) {
        if (!TextUtils.isEmpty(mSuffix)) {
            name += "_" + mSuffix;
        }
        return name;
    }

    public ColorStateList getColorByResName(String name) {
        try {
            return mResources.getColorStateList(mResources.getIdentifier(name, "color", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
