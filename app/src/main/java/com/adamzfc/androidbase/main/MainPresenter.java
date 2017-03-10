package com.adamzfc.androidbase.main;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by adamzfc on 3/9/17.
 */

public class MainPresenter implements MainContract.Presenter {
    private final MainContract.View mMainView;
    private List<MainItem> mDatas = new ArrayList<>();

    @Inject
    MainPresenter(MainContract.View mainView) {
        mMainView = mainView;
        mDatas.add(new MainItem("ttt1"));
        mDatas.add(new MainItem("ttt2"));
    }

    @Inject
    void setupListeners() {
        mMainView.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void loadData(boolean showLoadingUI) {
        if (showLoadingUI) {
            mMainView.setLoadingIndicator(true);
        }
        mMainView.showData(mDatas);
        if (showLoadingUI) {
            mMainView.setLoadingIndicator(false);
        }
    }
}
