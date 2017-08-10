package com.levarech.weatcher.internal.di.modules;

import android.app.Application;
import android.content.Context;

import com.levarech.weatcher.WeatcherApplication;

import dagger.Module;
import dagger.Provides;

/**
 * Created by efronny on 7/7/17.
 * Application module to be used with dagger.
 */
@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(WeatcherApplication application) {
        this.application = application;
    }

    @Provides
    Context provideApplicationContext() { return application; }
}
