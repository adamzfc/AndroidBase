package com.adamzfc.androidbase.test.slide;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adamzfc.androidbase.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adamzfc on 4/19/17.
 */

public class TestSlidePageAdapter extends PagerAdapter {
    private List<View> mListData;
    private int[] mColors = {Color.GREEN, Color.YELLOW, Color.RED};

    public TestSlidePageAdapter(Context context) {
        mListData = new ArrayList<>();
        for (int index = 0; index < 3; index ++) {
            View page = LayoutInflater.from(context).inflate(R.layout.item_test_slide_page, null);
            mListData.add(page);
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mListData.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView view = (TextView) mListData.get(position).findViewById(R.id.tv_content);
        view.setText("ViewPager" + position);
        view.setBackgroundColor(mColors[position % mColors.length]);
        container.addView(mListData.get(position), container.getLayoutParams());
        return mListData.get(position);
    }
}
