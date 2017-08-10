package com.levarech.weatcher.internal.di.modules;

import android.content.Context;

import com.levarech.weatcher.data.city.repository.CityDataRepository;
import com.levarech.weatcher.domain.city.repository.CityRepository;
import com.levarech.weatcher.presenter.AddNewCityContract;
import com.levarech.weatcher.presenter.AddNewCityPresenter;
import com.levarech.weatcher.presenter.CityListContract;
import com.levarech.weatcher.presenter.CityListPresenter;
import com.levarech.weatcher.view.BaseActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by EFR on 18/06/2017.
 * Weather module to provide dependencies
 */
@Module
public class ActivityModule {
    private final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    Context provideContext() { return activity; }

    @Provides
    CityListContract.Presenter provideCityListPresenter(CityListPresenter presenter) {
        return presenter;
    }

    @Provides
    AddNewCityContract.Presenter provideAddNewCityPresenter(AddNewCityPresenter presenter) {
        return presenter;
    }

    @Provides
    CityRepository provideCityRepository(CityDataRepository cityDataRepository) {
        return cityDataRepository;
    }
}
