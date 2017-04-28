package com.adamzfc.androidbase.test.skin.attr;

import android.content.Context;
import android.util.AttributeSet;

import com.adamzfc.androidbase.test.skin.config.Const;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adamzfc on 4/28/17.
 */

public class SkinAttrSupport {
    public static List<SkinAttr> getSkinAttrs(AttributeSet attrs, Context context) {
        List<SkinAttr> mSkinAttrs = new ArrayList<>();
        SkinAttrType attrType = null;
        SkinAttr skinAttr = null;
        for (int i = 0, n = attrs.getAttributeCount(); i < n; i ++) {
            String attrName = attrs.getAttributeName(i);
            String attrVal = attrs.getAttributeValue(i);

            if (attrVal.startsWith("@")) {
                int id = -1;
                try {
                    id = Integer.parseInt(attrVal.substring(1));
                } catch (NumberFormatException e) {
                    // do nothing
                }
                if (id == -1) {
                    continue;
                }
                String resName = context.getResources().getResourceEntryName(id);

                if (resName.startsWith(Const.SKIN_PERFIX)) {
                    attrType = getSupportAttrType(resName);
                    if (attrType == null) {
                        continue;
                    }
                    skinAttr = new SkinAttr(resName, attrType);
                    mSkinAttrs.add(skinAttr);
                }
            }
        }
        return null;
    }

    private static SkinAttrType getSupportAttrType(String attrName) {
        for (SkinAttrType attrType : SkinAttrType.values()) {
            if (attrType.getResType().equals(attrName)) {
                return attrType;
            }
        }
        return null;
    }
}
