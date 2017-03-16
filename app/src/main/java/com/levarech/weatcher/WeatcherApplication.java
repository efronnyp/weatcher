package com.levarech.weatcher;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by EFR on 16/03/2017.
 * Weatcher Application Configuration.
 */

public class WeatcherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
    }
}
