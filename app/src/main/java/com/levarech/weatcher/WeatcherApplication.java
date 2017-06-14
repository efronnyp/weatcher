package com.levarech.weatcher;

import android.support.multidex.MultiDexApplication;

import io.realm.Realm;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by EFR on 16/03/2017.
 * Weatcher Application Configuration.
 */

public class WeatcherApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        /* Realm initialization */
        Realm.init(this);
        /* Calligraphy initialization to define default font face */
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Lato-Regular.ttf")
                .build()
        );
    }
}
