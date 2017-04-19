package com.adamzfc.androidbase.test.slide;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.adamzfc.androidbase.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adamzfc on 4/19/17.
 */

public class TestSlideListAdapter extends BaseAdapter {
    private List<String> mListData;

    public TestSlideListAdapter() {
        mListData = new ArrayList<>();
        mListData.add("ListView 1");
        mListData.add("ListView 2");
        mListData.add("ListView 3");
        mListData.add("ListView 4");
        mListData.add("ListView 5");
        mListData.add("ListView 6");
        mListData.add("ListView 7");
        mListData.add("ListView 8");
        mListData.add("ListView 9");
        mListData.add("ListView 10");
        mListData.add("ListView 11");
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return mListData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // no viewholder
        @SuppressWarnings("all")
        final SlideLayout itemView = (SlideLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_test_slide_list, parent, false);
        TextView content = (TextView) itemView.findViewById(R.id.tv_content);
        TextView slide1 = (TextView) itemView.findViewById(R.id.tv_slide1);
        content.setText(mListData.get(position));
        content.setOnClickListener( v -> {
            if (itemView.getSlideState() == SlideLayout.STATE_OPEN) {
                itemView.smoothCloseSlide();
            } else {
                Toast.makeText(parent.getContext().getApplicationContext(), mListData.get(position),
                        Toast.LENGTH_SHORT).show();
            }
        });
        slide1.setOnClickListener( v ->
                Toast.makeText(parent.getContext().getApplicationContext(), "Slide Clicked",
                        Toast.LENGTH_SHORT).show());
        return itemView;
    }
}
