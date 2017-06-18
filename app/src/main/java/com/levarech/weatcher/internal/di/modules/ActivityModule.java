package com.levarech.weatcher.internal.di.modules;

import android.app.Activity;
import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by EFR on 18/06/2017.
 * Weather module to provide dependencies
 */
@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    Context provideContext() { return activity; }
}
