package com.adamzfc.androidbase;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.adamzfc.androidbase.main.MainActivity;
import com.adamzfc.androidbase.test.camera.TestCameraActivity;
import com.adamzfc.androidbase.test.drawable.TestDrawableActivity;
import com.adamzfc.androidbase.test.event.TestEventActivity;
import com.adamzfc.androidbase.test.fragment.TestFragmentActivity;
import com.adamzfc.androidbase.test.itemdrag.TestItemDragActivity;
import com.adamzfc.androidbase.test.labelview.TestLabelViewActivity;
import com.adamzfc.androidbase.test.magicfloat.TestMagicFloatViewActivity;
import com.adamzfc.androidbase.test.rxjava2.TestRxjava2Activity;
import com.adamzfc.androidbase.test.slide.TestSlideActivity;
import com.adamzfc.androidbase.test.sqlite.TestDbActivity;
import com.adamzfc.base.BaseRecyclerAdapter;
import com.adamzfc.base.BaseRecyclerHolder;
import com.adamzfc.router.annotation.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import common.CommonAcitivity;
import common.constants.IntentC;

/**
 * Index
 * Created by adamzfc on 4/14/17.
 */
@Router(IntentC.INDEX)
public class IndexActivity extends CommonAcitivity {
    private static final int PERMISSION_CODE_SD = 0x0010;
    @SuppressWarnings("membername")
    private Logger logger;
    @Override
    protected void initView() {
        logger = LoggerFactory.getLogger(IndexActivity.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED
                    != PermissionChecker.checkCallingOrSelfPermission(this,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_CODE_SD);
            }
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        List<IndexItem> datas = new ArrayList<>();
        datas.add(new IndexItem("Retrofit测试", MainActivity.class));
        datas.add(new IndexItem("Activity事件派发测试", TestEventActivity.class));
        datas.add(new IndexItem("Drawable", TestDrawableActivity.class));
        datas.add(new IndexItem("SlideLayout", TestSlideActivity.class));
        datas.add(new IndexItem("LabelView", TestLabelViewActivity.class));
        datas.add(new IndexItem("MagicFloatView", TestMagicFloatViewActivity.class));
        datas.add(new IndexItem("TestCamera", TestCameraActivity.class));
        datas.add(new IndexItem("TestFragment", TestFragmentActivity.class));
        datas.add(new IndexItem("TestRxjava2", TestRxjava2Activity.class));
        datas.add(new IndexItem("TestItemDrag", TestItemDragActivity.class));
        datas.add(new IndexItem("TestDb", TestDbActivity.class));

        BaseRecyclerAdapter<IndexItem> adapter = new BaseRecyclerAdapter<IndexItem>(this, datas) {
            @Override
            protected void bindData(BaseRecyclerHolder holder, int position, IndexItem indexItem) {
                Button button = holder.getButton(R.id.button);
                button.setText(indexItem.getName());
                button.setOnClickListener(v -> {
                    logger.info("startActivity {} {}", indexItem.getName(), indexItem.getClz());
                    startActivity(new Intent(IndexActivity.this, indexItem.getClz()));
                });
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
