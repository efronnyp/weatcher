package com.levarech.weatcher.presenter;

import com.levarech.weatcher.domain.city.CityCondition;
import com.levarech.weatcher.domain.city.interactor.DeleteSpecificCity;
import com.levarech.weatcher.domain.city.interactor.GetCurrentLocationWeather;
import com.levarech.weatcher.domain.city.interactor.GetSavedCityCondition;
import com.levarech.weatcher.domain.interactor.params.LocationParams;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by efronny on 7/6/17.
 * City list weather presenter.
 */

public class CityListPresenter implements CityListContract.Presenter {

    private GetSavedCityCondition getSavedCityCondition;
    private GetCurrentLocationWeather getCurrentLocationWeather;
    private DeleteSpecificCity deleteSpecificCity;
    private CityListContract.View view;

    @Inject
    CityListPresenter(GetSavedCityCondition getSavedCityCondition,
                      GetCurrentLocationWeather getCurrentLocationWeather,
                      DeleteSpecificCity deleteSpecificCity) {
        this.getSavedCityCondition = getSavedCityCondition;
        this.getCurrentLocationWeather = getCurrentLocationWeather;
        this.deleteSpecificCity = deleteSpecificCity;
    }

    @Override
    public void onStart() {
        //don't know where to start
    }

    @Override
    public void onDestroy() {
        getSavedCityCondition.dispose();
        getCurrentLocationWeather.dispose();
        deleteSpecificCity.dispose();
    }

    @Override
    public void setView(CityListContract.View view) {
        this.view = view;
    }

    @Override
    public void getSavedCityCondition() {
        view.showLoading();
        getSavedCityCondition.execute(new Subscriber<List<CityCondition>>() {
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
            public void onNext(List<CityCondition> cityConditionList) {
                view.onReceivedCityList(cityConditionList);
            }
        });
    }

    @Override
    public void getCurrentLocationWeather(double currentLatitude, double currentLongitude) {
        getCurrentLocationWeather.execute(new Subscriber<CityCondition>() {
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
                view.onReceivedCurrentLocationConditions(cityCondition);
            }
        }, LocationParams.from(currentLatitude, currentLongitude));
    }

    @Override
    public void deleteSavedCity(String cityId) {
        deleteSpecificCity.execute(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                //no-op
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                view.showError(e.getMessage());
            }

            @Override
            public void onNext(Boolean status) {
                //no-op
            }
        }, DeleteSpecificCity.Params.construct(cityId));
    }
}
