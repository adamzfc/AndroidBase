package com.adamzfc.androidbase.main;

import com.adamzfc.base.di.scope.FragmentScope;

import dagger.Component;

/**
 * Created by adamzfc on 3/9/17.
 */
@FragmentScope
@Component(modules = MainPresenterModule.class)
public interface MainComponet {
    void inject(MainActivity activity);
}
