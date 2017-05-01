package com.adamzfc.androidbase.test.skin;

import android.content.Context;
import android.content.SharedPreferences;

import com.adamzfc.androidbase.test.skin.config.Const;

/**
 * Created by adamzfc on 5/1/17.
 */

public class PrefUtils {
    private Context mContext;

    public PrefUtils(Context context) {
        this.mContext = context;
    }

    public void savePluginPath(String path) {
        SharedPreferences sp = mContext.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(Const.KEY_PLUGIN_PATH, path).apply();
    }

    public void savePluginPkg(String pkg) {
        SharedPreferences sp = mContext.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(Const.KEY_PLUGIN_PKG, pkg).apply();
    }

    public void saveSuffix(String suffix) {
        SharedPreferences sp = mContext.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(Const.KEY_SUFFIX, suffix).apply();
    }

    public String getPluginPath() {
        SharedPreferences sp = mContext.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(Const.KEY_PLUGIN_PATH, "");
    }

    public String getPluginPkg() {
        SharedPreferences sp = mContext.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(Const.KEY_PLUGIN_PKG, "");
    }

    public String getSuffix() {
        SharedPreferences sp = mContext.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(Const.KEY_SUFFIX, "");
    }

    public void clear() {
        SharedPreferences sp = mContext.getSharedPreferences(Const.PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().clear().apply();
    }
}
