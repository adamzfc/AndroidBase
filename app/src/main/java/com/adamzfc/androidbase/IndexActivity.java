package com.adamzfc.androidbase;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.adamzfc.androidbase.test.event.TestEventActivity;
import com.adamzfc.base.BaseRecyclerAdapter;
import com.adamzfc.base.BaseRecyclerHolder;
import com.adamzfc.router.annotation.Router;

import java.util.ArrayList;
import java.util.List;

import common.CommonAcitivity;
import common.constants.IntentC;

/**
 * Created by adamzfc on 4/14/17.
 */
@Router(IntentC.INDEX)
public class IndexActivity extends CommonAcitivity {
    @Override
    protected void initView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        List<IndexItem> datas = new ArrayList<>();
        datas.add(new IndexItem("Activity事件派发测试", TestEventActivity.class));
        BaseRecyclerAdapter<IndexItem> adapter = new BaseRecyclerAdapter<IndexItem>(this, datas) {
            @Override
            protected void bindData(BaseRecyclerHolder holder, int position, IndexItem indexItem) {
                Button button = holder.getButton(R.id.button);
                button.setText(indexItem.getName());
                button.setOnClickListener(v -> startActivity(new Intent(IndexActivity.this, indexItem.getClz())));
            }

            @Override
            protected int getItemLayoutId(int viewType) {
                return R.layout.item_index;
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_index;
    }
}
