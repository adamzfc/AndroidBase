package com.adamzfc.androidbase.test.itemdrag;

import android.content.Context;
import android.graphics.Color;
import android.view.View;

import com.adamzfc.base.BaseRecyclerHolder;

/**
 * Created by adamzfc on 4/26/17.
 */

public class ItemDragRecyclerHolder extends BaseRecyclerHolder implements ItemTouchHelperViewHolder {

    public ItemDragRecyclerHolder(Context context, View itemView) {
        super(context, itemView);
    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }
}
