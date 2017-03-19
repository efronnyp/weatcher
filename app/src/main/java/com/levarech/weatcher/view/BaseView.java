package com.levarech.weatcher.view;

/**
 * Created by EFR on 17/03/2017.
 * Interface representing View in a model view presenter pattern.
 */

public interface BaseView {
    /**
     * Show a view with a progress bar indicating a loading process.
     */
    void showLoading();

    /**
     * Hide a loading view.
     */
    void hideLoading();

    /**
     * Show an error message
     *
     * @param message A string representing an error.
     */
    void showError(String message);
}
