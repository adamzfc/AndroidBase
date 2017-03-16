package com.adamzfc.base.mvp;

import android.support.annotation.Nullable;

/**
 * Created by adamzfc on 3/16/17.
 */

public interface Presenter<V> {
    void onViewAttached(V view);

    void onViewDetached();

    void onDestroyed();

    boolean isViewAttached();

    @Nullable V getView();
}
