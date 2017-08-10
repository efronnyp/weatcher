package com.levarech.weatcher.presenter;

import com.levarech.weatcher.domain.city.CityCondition;
import com.levarech.weatcher.view.BaseView;

/**
 * Created by efronny on 7/10/17.
 * Contract that must be implemented by Add New City controller (SearchCityActivity).
 */

public interface AddNewCityContract {

    interface View extends BaseView {

        void onNewCitySavedSuccessfully(CityCondition conditions);
    }

    interface Presenter extends BasePresenter<View> {

        void saveNewCityByLocation(double latitude, double longitude);

        void saveNewCityByName(String countryCode, String cityName);
    }
}
