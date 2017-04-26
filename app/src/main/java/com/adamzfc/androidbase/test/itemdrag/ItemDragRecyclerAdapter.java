package com.adamzfc.androidbase.test.itemdrag;

import android.content.Context;

import com.adamzfc.base.BaseRecyclerAdapter;
import com.adamzfc.base.BaseRecyclerHolder;

import java.util.List;

/**
 * Created by adamzfc on 4/26/17.
 */

public class ItemDragRecyclerAdapter extends BaseRecyclerAdapter<String, ItemDragRecyclerHolder> implements ItemTouchHelperAdapter {


    public ItemDragRecyclerAdapter(Context context, List<String> list) {
        super(context, list);
    }

    @Override
    protected void bindData(ItemDragRecyclerHolder holder, int position, String s) {
        holder.getTextView(android.R.id.text1).setText(s);
    }

    @Override
    protected int getItemLayoutId(int viewType) {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        String prev = mData.remove(fromPosition);
        mData.add(toPosition > fromPosition ? toPosition - 1 : toPosition, prev);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(ItemDragRecyclerHolder holder, int position) {

    }
}
