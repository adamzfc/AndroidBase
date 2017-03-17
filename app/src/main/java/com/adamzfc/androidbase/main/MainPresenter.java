package com.adamzfc.androidbase.main;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by adamzfc on 3/9/17.
 */

public class MainPresenter implements MainContract.Presenter {
    private final MainContract.View mMainView;
    private List<MainItem> mDatas = new ArrayList<>();
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.bmob.cn/1/classes/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
    Api api = retrofit.create(Api.class);


    @Inject
    MainPresenter(MainContract.View mainView) {
        mMainView = mainView;
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

        api.getList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse);

        if (showLoadingUI) {
            mMainView.setLoadingIndicator(false);
        }
    }

    private void handleResponse(BaseBean<List<ListTable>> result) {
        mDatas.clear();
        for (ListTable table : result.getResults()) {
            mDatas.add(new MainItem(table.getTitle() + table.getCreatedAt()));
        }
        mMainView.showData(mDatas);
    }
}
