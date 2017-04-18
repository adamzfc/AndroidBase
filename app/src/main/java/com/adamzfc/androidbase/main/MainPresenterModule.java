package com.adamzfc.androidbase.main;

import dagger.Module;
import dagger.Provides;

/**
 * Created by adamzfc on 3/9/17.
 */

@Module
public class MainPresenterModule {
    private final MainContract.View mView;

    public MainPresenterModule(MainContract.View view) {
        mView = view;
    }

    /**
     * provide
     * @return MainContract.View
     */
    @Provides
    MainContract.View provideMainContractView() {
        return mView;
    }
}
