package com.levarech.weatcher.internal.di.components;

import android.content.Context;

import com.levarech.weatcher.internal.di.modules.ApplicationModule;

import dagger.Component;

/**
 * Created by efronny on 7/7/17.
 * Weatcher application component for dagger.
 */
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    Context context();
}
