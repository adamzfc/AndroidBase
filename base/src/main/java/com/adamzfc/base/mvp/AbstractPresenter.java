package com.adamzfc.base.mvp;

import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * Created by adamzfc on 3/16/17.
 */

public abstract class AbstractPresenter<V> implements Presenter<V> {

    private WeakReference<V> view;

    @Override
    public void onViewAttached(V view) {
        this.view = new WeakReference<>(view);
    }

    @Override
    public void onViewDetached() {
        this.view.clear();
    }

    @Override
    public void onDestroyed() {
        view = null;
    }

    @Nullable
    @Override
    public V getView() {
        return view == null ? null : view.get();
    }

    @Override
    public boolean isViewAttached() {
        return view != null && view.get() != null;
    }
}
