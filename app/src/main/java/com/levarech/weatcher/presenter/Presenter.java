package com.levarech.weatcher.presenter;

/**
 * Created by EFR on 16/03/2017.
 * Interface to communicate view with presenter.
 */

public interface Presenter {
    void subscribe();

    void unsubscribe();

    void onDestroy();
}
