package com.levarech.weatcher.view.adapter;

import android.view.View;

/**
 * Created by EFR on 06/04/2017.
 * Interface to be used when a RecyclerView item received long click event.
 */

public interface OnItemLongClickListener<T> {
    void onLongClick(View view, int i, T data);
}
