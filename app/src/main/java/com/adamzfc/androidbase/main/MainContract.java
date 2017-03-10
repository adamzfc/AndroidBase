package com.adamzfc.androidbase.main;

import com.adamzfc.base.BasePresentr;
import com.adamzfc.base.BaseView;

import java.util.List;

/**
 * Created by adamzfc on 3/9/17.
 */

public interface MainContract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);

        void showData(List<MainItem> datas);
    }

    interface Presenter extends BasePresentr {
        void loadData(boolean showLoadingUI);
    }
}
