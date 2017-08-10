package com.levarech.weatcher;

import android.support.multidex.MultiDexApplication;

import com.levarech.weatcher.internal.di.components.ApplicationComponent;
import com.levarech.weatcher.internal.di.components.DaggerApplicationComponent;
import com.levarech.weatcher.internal.di.modules.ApplicationModule;

import io.realm.Realm;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by EFR on 16/03/2017.
 * Weatcher Application Configuration.
 */

public class WeatcherApplication extends MultiDexApplication {

    private ApplicationComponent applicationComponent;

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

        /* Dagger injector initialization */
        initInjector();
    }

    private void initInjector() {
        if (applicationComponent == null) {
            applicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
