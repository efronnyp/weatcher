package com.levarech.weatcher.view;

import com.levarech.weatcher.model.local.CityConditions;

import java.util.List;

/**
 * Created by EFR on 17/03/2017.
 * Interface to represent weather data to view.
 */

public interface WeatherAddView extends BaseView {

    void onNewCitySavedSuccessfully(CityConditions conditions);
}
