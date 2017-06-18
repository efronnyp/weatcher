package com.levarech.weatcher.internal.di.components;

import com.levarech.weatcher.internal.di.modules.ActivityModule;
import com.levarech.weatcher.view.activity.MainActivity;
import com.levarech.weatcher.view.activity.SearchCityActivity;

import dagger.Component;

/**
 * Created by EFR on 18/06/2017.
 * Dagger Component for Weather Activity.
 */
@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(SearchCityActivity searchCityActivity);
}
