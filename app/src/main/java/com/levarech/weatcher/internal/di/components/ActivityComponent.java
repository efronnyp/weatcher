package com.levarech.weatcher.internal.di.components;

import com.levarech.weatcher.internal.di.modules.ActivityModule;
import com.levarech.weatcher.view.activity.MainActivity;
import com.levarech.weatcher.view.activity.SearchCityActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by EFR on 18/06/2017.
 * Dagger Component for Weather Activity.
 */
@Singleton
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class})
public interface ActivityComponent {
    void injek(MainActivity mainActivity);
    void injek(SearchCityActivity searchCityActivity);
}
