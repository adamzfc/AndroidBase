package com.adamzfc.androidbase.test.itemdrag;

/**
 * Created by adamzfc on 4/26/17.
 */

public interface ItemTouchHelperAdapter {
    /**
     * Called when an item has been dragged far enough to trigger a move
     * @param fromPosition The start poistion of the moved item
     * @param toPosition The end position of the moved item
     */
    void onItemMove(int fromPosition, int toPosition);

    /**
     * Called when an item has been dismissed by a swipe
     * @param position The position of the item dismissed
     */
    void onItemDismiss(int position);
}
