package com.adamzfc.androidbase.test.skin.attr;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.adamzfc.androidbase.test.skin.ResourcesManager;
import com.adamzfc.androidbase.test.skin.SkinManager;

/**
 * Created by adamzfc on 4/28/17.
 */

public enum SkinAttrType {

    BACKGROUND("background") {
        @Override
        public void apply(View view, String resName) {
            Drawable drawable = getResourcesManager().getDrawableByResName(resName);
            if (drawable != null) {
                view.setBackgroundDrawable(drawable);
            }
        }
    },
    SRC("src") {
        @Override
        public void apply(View view, String resName) {
            Drawable drawable = getResourcesManager().getDrawableByResName(resName);
            if (view instanceof ImageView) {
                ImageView imageView = (ImageView) view;
                if (drawable != null) {
                    imageView.setImageDrawable(drawable);
                }
            }
        }
    },
    TEXT_COLOR("textColor") {
        @Override
        public void apply(View view, String resName) {
            ColorStateList colorStateList = getResourcesManager().getColorByResName(resName);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                if (colorStateList != null) {
                    textView.setTextColor(colorStateList);
                }
            }
        }
    };
    String resType;
    SkinAttrType(String type) {
        resType = type;
    }

    public abstract void apply(View view, String resName);

    public ResourcesManager getResourcesManager() {
        return SkinManager.getInstance().getResourcesManager();
    }

    public String getResType() {
        return resType;
    }
}
