package com.adamzfc.androidbase.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.adamzfc.androidbase.R;
import com.adamzfc.base.BaseHolder;
import com.adamzfc.base.BaseRecyclerAdapter;
import com.adamzfc.base.BaseRecyclerHolder;
import com.adamzfc.base.DefaultAdapter;
import com.adamzfc.base.view.widget.ScrollChildSwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adamzfc on 3/9/17.
 */

public class MainFragment extends Fragment implements MainContract.View {

    private MainContract.Presenter mPresenter;
    private ItemAdapter mListAdapter;
    private BaseRecyclerAdapter<MainItem> mAdapter;
    List<MainItem> datas = new ArrayList<>();


    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        datas.add(new MainItem("tttt2"));
//        datas.add(new MainItem("tttttt3"));
        mListAdapter = new ItemAdapter(datas);
        mAdapter = new BaseRecyclerAdapter<MainItem>(getActivity(), datas) {
            @Override
            protected void bindData(BaseRecyclerHolder holder, int position, MainItem mainItem) {
                Button name = (Button) holder.getView(R.id.name);
                name.setText(mainItem.getName());
            }

            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.item_main;
            }
        };
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recyclerView);
//        recyclerView.setAdapter(mListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        // Set up progress indicator
        final ScrollChildSwipeRefreshLayout swipeRefreshLayout =
                (ScrollChildSwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );
        // Set the scrolling view in the custom SwipeRefreshLayout.
        swipeRefreshLayout.setScrollUpChild(recyclerView);
        swipeRefreshLayout.setOnRefreshListener(() -> mPresenter.loadData(true));

        return root;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (getView() == null) {
            return;
        }
        final SwipeRefreshLayout srl =
                (SwipeRefreshLayout) getView().findViewById(R.id.refresh_layout);

        // Make sure setRefreshing() is called after the layout is done with everything else.
        srl.post(() -> srl.setRefreshing(active));
    }

    @Override
    public void showData(List<MainItem> datas) {
//        Toast.makeText(getContext(), "data", Toast.LENGTH_SHORT).show();
//        mListAdapter.setData(datas);
//        mListAdapter.notifyDataSetChanged();
        this.datas.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    public class ItemHolder extends BaseHolder<MainItem> {

        private Button name;

        public ItemHolder(View itemView) {
            super(itemView);
            name = (Button) itemView.findViewById(R.id.name);
        }

        @Override
        public void setData(MainItem data) {
            name.setText(data.getName());
        }
    }


    public class ItemAdapter extends DefaultAdapter<MainItem> {

        public ItemAdapter(List<MainItem> infos) {
            super(infos);
        }

        public void setData(List<MainItem> datas) {
//            mInfos.clear();
//            mInfos.addAll(datas);
            mInfos = datas;
//            notifyDataSetChanged();
        }

        @Override
        public BaseHolder<MainItem> getHolder(View v) {
            return new ItemHolder(v);
        }

        @Override
        public int getLayoutId() {
            return R.layout.item_main;
        }
    }
}
