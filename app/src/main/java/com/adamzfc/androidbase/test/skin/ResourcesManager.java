package com.adamzfc.androidbase.test.skin;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

/**
 * Created by adamzfc on 4/28/17.
 */

public class ResourcesManager {
    private Resources mResources;
    private String mPkgName;

    public ResourcesManager(Resources resources, String pkgName) {
        mResources = resources;
        mPkgName = pkgName;
    }

    public Drawable getDrawableByResName(String name) {
        try {
            return mResources.getDrawable(mResources.getIdentifier(name, "drawable", mPkgName));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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
