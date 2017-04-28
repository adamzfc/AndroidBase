package com.adamzfc.androidbase.test.skin;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.adamzfc.androidbase.test.camera.Camera2BasicFragment;
import com.adamzfc.androidbase.test.skin.attr.SkinView;
import com.adamzfc.androidbase.test.skin.callback.ISkinChangeListener;
import com.adamzfc.androidbase.test.skin.callback.ISkinChangingCallback;

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
    }

    public ResourcesManager getResourcesManager() {
        return mResourcesManager;
    }

    private void loadPlugin(String skinPluginPath, String skinPluginPkg) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method assAssetPathMethod = assetManager.getClass().getMethod("addAssetPath", String.class);
            assAssetPathMethod.invoke(assetManager, skinPluginPath);

            Resources superResources = mContext.getResources();
            Resources resources = new Resources(assetManager, superResources.getDisplayMetrics(),
                    superResources.getConfiguration());
            mResourcesManager = new ResourcesManager(resources, skinPluginPkg);
//            Drawable drawable = mResourcesManager.getDrawableByResName("skin_main_bg");
//            if (drawable != null) {
//                mDrawerLayout.setBackground(drawable);
//            }

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
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    loadPlugin(skinPluginPath, skinPluginPkg);
                } catch (Exception e) {
                    e.printStackTrace();
                    finalCallback.onError(e);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                try {
                    notifyChangedListener();
                    finalCallback.onComplete();
                } catch (Exception e) {
                    finalCallback.onError(e);
                }
            }
        };

    }

    private void notifyChangedListener() {
        for (ISkinChangeListener listener : mListeners) {
            skinChanged(listener);
            listener.onSkinChanged();
        }
    }

    private void skinChanged(ISkinChangeListener listener) {
        List<SkinView> skinViews = mSkinViewMaps.get(listener);
        skinViews.forEach(SkinView::apply);
    }
}
