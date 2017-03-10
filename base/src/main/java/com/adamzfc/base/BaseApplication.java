package com.adamzfc.base;

import android.app.Application;
import android.content.Context;

/**
 * Created by adamzfc on 3/4/17.
 */

public abstract class BaseApplication extends Application {
    private static BaseApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (mApplication != null) {
            mApplication = null;
        }
    }

    public static Context getContext() {
        return mApplication;
    }
}
