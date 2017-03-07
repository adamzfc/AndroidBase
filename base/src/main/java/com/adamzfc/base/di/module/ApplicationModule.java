package com.adamzfc.base.di.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by adamzfc on 3/6/17.
 */
@Module
public class ApplicationModule {
    private final Context mContext;

    ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
