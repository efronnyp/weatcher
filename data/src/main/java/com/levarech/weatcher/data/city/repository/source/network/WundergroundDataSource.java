package com.levarech.weatcher.data.city.repository.source.network;

import android.support.annotation.NonNull;

import com.levarech.weatcher.data.BuildConfig;
import com.levarech.weatcher.data.city.CityConditionEntity;
import com.levarech.weatcher.data.city.mapper.NetworkCityConditionMapper;
import com.levarech.weatcher.data.city.repository.source.CityDataStore;
import com.levarech.weatcher.data.city.repository.source.network.response.ConditionsResponse;
import com.levarech.weatcher.data.network.BaseNetwork;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by efronny on 7/4/17.
 * Weather condition data provider from API service.
 */

public class WundergroundDataSource extends BaseNetwork<CityConditionAPI>
        implements CityDataStore {

    private NetworkCityConditionMapper mapper;

    public WundergroundDataSource(NetworkCityConditionMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public Observable<List<CityConditionEntity>> getSavedCityWeather() {
        return Observable.just(new ArrayList<>());
    }

    @Override
    public boolean saveCityCondition(CityConditionEntity cityConditionEntity) {
        return false;
    }

    @Override
    public Observable<Boolean> deleteSavedCity(String cityId) {
        return Observable.just(false);
    }

    @Override
    public Observable<CityConditionEntity> getCurrentLocationWeather(
            double currentLatitude, double currentLongitude) {
        return getWeatherConditionByLocation(currentLatitude, currentLongitude);
    }

    @Override
    public Observable<CityConditionEntity> getWeatherConditionByLocation(
            double latitude, double longitude) {
        return getService()
                .getCurrentByLocation(String.valueOf(latitude), String.valueOf(longitude))
                .compose(applyNewThreadAndMapResponse());
    }

    @Override
    public Observable<CityConditionEntity> getWeatherConditionByName(
            String countryCode, String cityName) {
        return getService()
                .getCurrentByCityName(countryCode, cityName)
                .compose(applyNewThreadAndMapResponse());
    }

    @Override
    protected String getBaseUrl() {
        return BuildConfig.BASE_URL;
    }

    @Override
    protected Class<CityConditionAPI> getServiceClass() {
        return CityConditionAPI.class;
    }

    @NonNull
    private Observable.Transformer<ConditionsResponse, CityConditionEntity>
    applyNewThreadAndMapResponse() {
        return conditionsResponseObservable -> conditionsResponseObservable
                .map(response -> mapper.transform(response)
                        .setObservationTimestamp(System.currentTimeMillis()))
                .subscribeOn(Schedulers.newThread());
    }
}
