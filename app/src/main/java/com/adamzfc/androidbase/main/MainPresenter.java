package com.adamzfc.androidbase.main;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by adamzfc on 3/9/17.
 */

public class MainPresenter implements MainContract.Presenter {
    private final MainContract.View mMainView;
    private List<MainItem> mDatas = new ArrayList<>();
    private Retrofit mRetrofit = new Retrofit.Builder()
            .baseUrl("https://mApi.bmob.cn/1/classes/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
    private RestApi mApi = mRetrofit.create(RestApi.class);

    /**
     * inject view
     * @param mainView inject view
     */
    @Inject
    MainPresenter(MainContract.View mainView) {
        mMainView = mainView;
    }

    /**
     * set up listener
     */
    @Inject
    void setupListeners() {
        mMainView.setPresenter(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void loadData(boolean showLoadingUI) {
        if (showLoadingUI) {
            mMainView.setLoadingIndicator(true);
        }

        mApi.getList()
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
