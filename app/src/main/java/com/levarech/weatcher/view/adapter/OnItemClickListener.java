package com.levarech.weatcher.view.adapter;

import android.view.View;

/**
 * Created by EFR on 19/03/2017.
 * On item click interface for recycler view.
 */

public interface OnItemClickListener<T> {
    void onClick(View view, int i, T data);
}
