package com.adamzfc.androidbase.test.slide;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ListView;

import com.adamzfc.androidbase.R;

/**
 * Created by adamzfc on 4/19/17.
 */

public class TestSlideActivity extends Activity {
    private ListView mListView;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_slide);

        mListView = (ListView) this.findViewById(R.id.listview);
        TestSlideListAdapter adapter = new TestSlideListAdapter();
        mListView.setAdapter(adapter);

        mViewPager = (ViewPager) this.findViewById(R.id.viewpager);
        TestSlidePageAdapter pageAdapter = new TestSlidePageAdapter(this);
        mViewPager.setAdapter(pageAdapter);
    }
}
