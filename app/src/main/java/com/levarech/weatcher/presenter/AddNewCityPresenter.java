package com.levarech.weatcher.presenter;

import com.levarech.weatcher.domain.city.CityCondition;
import com.levarech.weatcher.domain.city.interactor.SaveCityByLocation;
import com.levarech.weatcher.domain.city.interactor.SaveCityByName;
import com.levarech.weatcher.domain.interactor.params.LocationParams;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by efronny on 7/10/17.
 * Implementation of add new city presenter contract.
 */

public class AddNewCityPresenter implements AddNewCityContract.Presenter {

    private SaveCityByLocation saveCityByLocation;
    private SaveCityByName saveCityByName;
    private AddNewCityContract.View view;

    @Inject
    public AddNewCityPresenter(SaveCityByLocation saveCityByLocation,
                               SaveCityByName saveCityByName) {
        this.saveCityByLocation = saveCityByLocation;
        this.saveCityByName = saveCityByName;
    }

    @Override
    public void onStart() {
        //no-op
    }

    @Override
    public void onDestroy() {
        saveCityByLocation.dispose();
        saveCityByName.dispose();
    }

    @Override
    public void setView(AddNewCityContract.View view) {
        this.view = view;
    }

    @Override
    public void saveNewCityByLocation(double latitude, double longitude) {
        view.showLoading();
        saveCityByLocation.execute(new Subscriber<CityCondition>() {
            @Override
            public void onCompleted() {
                view.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.showError(e.getMessage());
            }

            @Override
            public void onNext(CityCondition cityCondition) {
                view.onNewCitySavedSuccessfully(cityCondition);
            }
        }, LocationParams.from(latitude, longitude));
    }

    @Override
    public void saveNewCityByName(String countryCode, String cityName) {
        view.showLoading();
        saveCityByName.execute(new Subscriber<CityCondition>() {
            @Override
            public void onCompleted() {
                view.hideLoading();
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.showError(e.getMessage());
            }

            @Override
            public void onNext(CityCondition cityCondition) {
                view.onNewCitySavedSuccessfully(cityCondition);
            }
        }, SaveCityByName.Params.from(countryCode, cityName));
    }
}
