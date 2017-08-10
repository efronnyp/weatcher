package com.levarech.weatcher.presenter;

import com.levarech.weatcher.domain.city.CityCondition;
import com.levarech.weatcher.view.BaseView;

import java.util.List;

/**
 * Created by efronny on 7/6/17.
 * Contract for both city list view and city list presenter
 */

public interface CityListContract {

    interface View extends BaseView {

        void onReceivedCityList(List<CityCondition> citiesConditions);

        void onReceivedCurrentLocationConditions(CityCondition conditions);
    }

    interface Presenter extends BasePresenter<View> {

        void getSavedCityCondition();

        void getCurrentLocationWeather(double currentLatitude, double currentLongitude);

        void deleteSavedCity(String cityId);
    }
}
