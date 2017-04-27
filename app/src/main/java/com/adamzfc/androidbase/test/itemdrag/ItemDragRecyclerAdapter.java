package com.adamzfc.androidbase.test.itemdrag;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by adamzfc on 4/26/17.
 */

public class ItemDragRecyclerAdapter extends RecyclerView.Adapter<ItemDragRecyclerHolder> implements ItemTouchHelperAdapter {

    private static final String[] STRINGS = new String[] {
            "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"
    };

    private List<String> mDatas = new ArrayList<>();

    public ItemDragRecyclerAdapter() {
        mDatas.addAll(Arrays.asList(STRINGS));
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        String prev = mDatas.remove(fromPosition);
        mDatas.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public ItemDragRecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        ItemDragRecyclerHolder itemDragRecyclerHolder =
                new ItemDragRecyclerHolder(parent.getContext(), view);
        return itemDragRecyclerHolder;
    }

    @Override
    public void onBindViewHolder(ItemDragRecyclerHolder holder, int position) {
        holder.getTextView(android.R.id.text1).setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
