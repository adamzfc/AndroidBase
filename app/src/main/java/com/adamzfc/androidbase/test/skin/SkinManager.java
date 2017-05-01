package com.adamzfc.androidbase.test.skin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncTask;

import com.adamzfc.androidbase.test.skin.attr.SkinView;
import com.adamzfc.androidbase.test.skin.callback.ISkinChangeListener;
import com.adamzfc.androidbase.test.skin.callback.ISkinChangingCallback;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by adamzfc on 4/28/17.
 */

public class SkinManager {
    private Context mContext;
    private static SkinManager sInstance;
    private ResourcesManager mResourcesManager;

    private List<ISkinChangeListener> mListeners = new ArrayList<>();

    private Map<ISkinChangeListener, List<SkinView>> mSkinViewMaps = new HashMap<>();

    private PrefUtils mPrefUtils;

    private String mCurPath;
    private String mCurPkg;
    private String mSuffix;

    private SkinManager() {
    }

    public static SkinManager getInstance() {
        if (sInstance == null) {
            synchronized (SkinManager.class) {
                if (sInstance == null) {
                    sInstance = new SkinManager();
                }
            }
        }
        return sInstance;
    }

    public void init(Context context) {
        mContext = context.getApplicationContext();
        mPrefUtils = new PrefUtils(mContext);
        mSuffix = mPrefUtils.getSuffix();

        try {
            String pluginPath = mPrefUtils.getPluginPath();
            String pluginPkg = mPrefUtils.getPluginPkg();

            File file = new File(pluginPath);
            if (file.exists()) {
                loadPlugin(pluginPath, pluginPkg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            mPrefUtils.clear();
        }
    }

    public ResourcesManager getResourcesManager() {
        if (!usePlugin()) {
            return new ResourcesManager(mContext.getResources(), mContext.getPackageName(), mSuffix);
        }
        return mResourcesManager;
    }

    private void loadPlugin(String skinPluginPath, String skinPluginPkg) {
        if (skinPluginPath.equals(mCurPath) && skinPluginPkg.equals(mCurPkg)) {
            return;
        }
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method assAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
            assAssetPathMethod.invoke(assetManager, skinPluginPath);

            Resources superResources = mContext.getResources();
            Resources resources = new Resources(assetManager, superResources.getDisplayMetrics(),
                    superResources.getConfiguration());
            mResourcesManager = new ResourcesManager(resources, skinPluginPkg, null);
//            Drawable drawable = mResourcesManager.getDrawableByResName("skin_main_bg");
//            if (drawable != null) {
//                mDrawerLayout.setBackground(drawable);
//            }

            mCurPath = skinPluginPath;
            mCurPkg = skinPluginPkg;

        } catch (InstantiationException | IllegalAccessException
                | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public List<SkinView> getSkinViews(ISkinChangeListener listener) {
        return mSkinViewMaps.get(listener);
    }

    public void addSkinView(ISkinChangeListener listener, List<SkinView> views) {
        mSkinViewMaps.put(listener, views);
    }

    public void registerListener(ISkinChangeListener listener) {
        mListeners.add(listener);
    }

    public void unRegisterListener(ISkinChangeListener listener) {
        mListeners.remove(listener);
        mSkinViewMaps.remove(listener);
    }

    public void changeSkin(String skinPluginPath, String skinPluginPkg, ISkinChangingCallback callback) {
        if (callback == null) {
            callback = ISkinChangingCallback.DEFAULT_CALLBACK;
        }
        final ISkinChangingCallback finalCallback = callback;
        finalCallback.onStart();
        new AsyncTask<Void, Integer, Integer>() {

            @Override
            protected Integer doInBackground(Void... params) {
                try {
                    loadPlugin(skinPluginPath, skinPluginPkg);
                } catch (Exception e) {
                    e.printStackTrace();
                    finalCallback.onError(e);
                    return -1;
                }
                return 0;
            }

            @Override
            protected void onPostExecute(Integer code) {
                if (code == -1) {
                    finalCallback.onError(null);
                    return;
                }
                try {
                    notifyChangedListener();
                    finalCallback.onComplete();
                    updatePluginInfo(skinPluginPath, skinPluginPkg);
                } catch (Exception e) {
                    finalCallback.onError(e);
                }
            }
        };

    }

    public void changeSkin(String suffix) {
        clearPluginInfo();
        mSuffix = suffix;
        mPrefUtils.saveSuffix(suffix);
        notifyChangedListener();
    }

    private void clearPluginInfo() {
        mCurPath = null;
        mCurPkg = null;
        mSuffix = null;
        mPrefUtils.clear();
    }

    private void updatePluginInfo(String path, String pkg) {
        mPrefUtils.savePluginPath(path);
        mPrefUtils.savePluginPkg(pkg);
    }

    private void notifyChangedListener() {
        for (ISkinChangeListener listener : mListeners) {
            skinChange(listener);
            listener.onSkinChanged();
        }
    }

    public void skinChange(ISkinChangeListener listener) {
        List<SkinView> skinViews = mSkinViewMaps.get(listener);
        skinViews.forEach(SkinView::apply);
    }

    public boolean needChangeSkin() {
        return usePlugin() || useSuffix();
    }

    private boolean usePlugin() {
        return mCurPath != null && !mCurPath.trim().equals("");
    }

    private boolean useSuffix() {
        return mSuffix != null && !mSuffix.trim().equals("");
    }

}
