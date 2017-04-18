package com.adamzfc.androidbase.main;

import com.adamzfc.base.BasePresentr;
import com.adamzfc.base.BaseView;

import java.util.List;

/**
 * Created by adamzfc on 3/9/17.
 */
public interface MainContract {
    /**
     * main view
     */
    interface View extends BaseView<Presenter> {
        /**
         * set loading indicator
         * @param active is active
         */
        void setLoadingIndicator(boolean active);

        /**
         * show data
         * @param datas datas
         */
        void showData(List<MainItem> datas);
    }

    /**
     * main presenter
     */
    interface Presenter extends BasePresentr {
        /**
         * load data
         * @param showLoadingUI is show loading ui
         */
        void loadData(boolean showLoadingUI);
    }
}
