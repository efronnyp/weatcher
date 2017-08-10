package com.levarech.weatcher.presenter;

/**
 * Created by EFR on 16/03/2017.
 * Interface to communicate view with presenter.
 */

interface BasePresenter<T> {

    void onStart();

    void onDestroy();

    void setView(T view);
}
