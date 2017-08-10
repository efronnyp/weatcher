package com.levarech.weatcher.data.city.repository;

import com.levarech.weatcher.data.city.CityConditionEntity;
import com.levarech.weatcher.data.city.mapper.CityConditionEntityMapper;
import com.levarech.weatcher.data.city.repository.source.CityDataStoreFactory;
import com.levarech.weatcher.domain.city.CityCondition;
import com.levarech.weatcher.domain.city.repository.CityRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by efronny on 6/20/17.
 * City repository for data module.
 */

public class CityDataRepository implements CityRepository {

    private static final long CITY_CONDITIONS_EXPIRY = 10L * 60L * 1000L;

    private CityDataStoreFactory cityDataStoreFactory;
    private CityConditionEntityMapper cityConditionEntityMapper;

    @Inject
    public CityDataRepository(CityDataStoreFactory cityDataStoreFactory,
                              CityConditionEntityMapper cityConditionEntityMapper) {
        this.cityDataStoreFactory = cityDataStoreFactory;
        this.cityConditionEntityMapper = cityConditionEntityMapper;
    }

    @Override
    public Observable<List<CityCondition>> getSavedCityWeather() {
        return cityDataStoreFactory.create()
            .getSavedCityWeather()
            .flatMap(new Func1<List<CityConditionEntity>, Observable<CityConditionEntity>>() {
                @Override
                public Observable<CityConditionEntity> call(List<CityConditionEntity> cityList) {
                    return Observable.from(cityList);
                }
            })
            .flatMap(new Func1<CityConditionEntity, Observable<CityConditionEntity>>() {
                @Override
                public Observable<CityConditionEntity> call(CityConditionEntity cityCondition) {
                    long dataAge = System.currentTimeMillis() - cityCondition.getObservationTimestamp();
                    if (dataAge > CITY_CONDITIONS_EXPIRY) {
                        return cityDataStoreFactory.createRemoteDataStore()
                                .getWeatherConditionByLocation(cityCondition.getLatitude(),
                                        cityCondition.getLongitude())
                                .doOnNext(cityConditionEntity ->
                                    cityConditionEntity.setCityId(cityCondition.getCityId())
                                            .setCurrentCity(cityCondition.isCurrentCity()))
                                .compose(saveToLocalTransformer());
                    } else {
                        return Observable.just(cityCondition);
                    }
                }
            })
            .map(cityConditionEntity -> cityConditionEntityMapper.transform(cityConditionEntity))
            .toSortedList((city1, city2) -> city1.isCurrentCity() ? -1 : 1);
    }

    @Override
    public Observable<CityCondition> getCurrentLocationWeather(
            double currentLatitude, double currentLongitude) {
        return cityDataStoreFactory.create()
            .getCurrentLocationWeather(currentLatitude, currentLongitude)
            .flatMap(new Func1<CityConditionEntity, Observable<CityConditionEntity>>() {
                @Override
                public Observable<CityConditionEntity> call(CityConditionEntity cityConditionEntity) {
                    return cityDataStoreFactory.createRemoteDataStore()
                            .getWeatherConditionByLocation(currentLatitude, currentLongitude)
                            .doOnNext(remoteCityCondition ->
                                remoteCityCondition.setCityId(cityConditionEntity.getCityId())
                                    .setCurrentCity(cityConditionEntity.isCurrentCity()))
                            .compose(saveToLocalTransformer());
                }
            })
            .map(cityConditionEntity -> cityConditionEntityMapper.transform(cityConditionEntity));
    }

    @Override
    public Observable<CityCondition> saveCityByLocation(double latitude, double longitude) {
        return cityDataStoreFactory.createRemoteDataStore()
                .getWeatherConditionByLocation(latitude, longitude)
                .compose(saveToLocalTransformer())
                .map(cityConditionEntity -> cityConditionEntityMapper.transform(cityConditionEntity));
    }

    @Override
    public Observable<CityCondition> saveCityByName(String countryCode, String cityName) {
        return cityDataStoreFactory.createRemoteDataStore()
                .getWeatherConditionByName(countryCode, cityName)
                .compose(saveToLocalTransformer())
                .map(cityConditionEntity -> cityConditionEntityMapper.transform(cityConditionEntity));
    }

    @Override
    public Observable<Boolean> deleteSavedCity(String cityId) {
        return cityDataStoreFactory.create().deleteSavedCity(cityId);
    }

    @Override
    public Observable<String> getWeatherDetail(String cityId) {
        return null;
    }

    private Observable.Transformer<CityConditionEntity, CityConditionEntity> saveToLocalTransformer() {
        return cityConditionEntityObservable -> cityConditionEntityObservable
                .doOnNext(cityConditionEntity ->
                        cityDataStoreFactory.create()
                                .saveCityCondition(cityConditionEntity)
                );
    }
}
