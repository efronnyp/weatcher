package com.levarech.weatcher.presenter;

import com.levarech.weatcher.model.DisplayLocation;
import com.levarech.weatcher.model.local.CityConditions;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by EFR on 16/03/2017.
 * Weather data repository that will retrieve data from local database if available and still fresh,
 * otherwise remote data will be downloaded.
 */

public class WeatherDataRepository implements BaseDataSource {

    /**
     * The age of weather conditions data of a city is 10 minutes.
     */
    private static final long CITY_CONDITIONS_EXPIRY = 10L * 60L * 1000L;
    private LocalDataSource mLocalDataSource;
    private RemoteDataSource mRemoteDataSource;

    public WeatherDataRepository(LocalDataSource localDataSource,
                                 RemoteDataSource remoteDataSource) {
        this.mLocalDataSource = localDataSource;
        this.mRemoteDataSource = remoteDataSource;
    }

    /**
    * Fetch all saved cities and it's weather conditions data.
    */
    public Observable<List<CityConditions>> getSavedCitiesCondition() {
        return mLocalDataSource.getSavedCitiesConditions()
            .flatMap(new Func1<List<CityConditions>, Observable<List<CityConditions>>>() {
                @Override
                public Observable<List<CityConditions>> call(List<CityConditions> cityConditionsList) {
                    return Observable.from(cityConditionsList)
                        .flatMap(new Func1<CityConditions, Observable<CityConditions>>() {
                            @Override
                            public Observable<CityConditions> call(CityConditions conditions) {
                                long dataAge = System.currentTimeMillis() - conditions.lastUpdated;
                                if (dataAge > CITY_CONDITIONS_EXPIRY) {
                                    //Have to retrieve again from server if already more than usable age
                                    DisplayLocation location =
                                            conditions.currentObservation.display_location;
                                    return updateConditionsFromRemote(location.latitude, location.longitude,
                                            conditions.cityId, conditions.currentCity);
                                } else {
                                    return Observable.just(conditions);
                                }
                            }
                        }).toList();
                }
                });
    }

    /**
     * Get weather conditions of selected city id
     */
    public Observable<CityConditions> getCityConditionsById(String cityId) {
        return mLocalDataSource.getSavedCityConditions(cityId)
                .flatMap(new Func1<CityConditions, Observable<CityConditions>>() {
                    @Override
                    public Observable<CityConditions> call(CityConditions cityConditions) {
                        long dataAge = System.currentTimeMillis() - cityConditions.lastUpdated;
                        if (dataAge > CITY_CONDITIONS_EXPIRY) {
                            //To old to eat a peanut?
                            DisplayLocation location =
                                    cityConditions.currentObservation.display_location;
                            return updateConditionsFromRemote(location.latitude,
                                    location.longitude, cityConditions.cityId, cityConditions.currentCity);
                        } else {
                            return Observable.just(cityConditions);
                        }
                    }
                });
    }

    /**
     * Get current location weather conditions.
     */
    public Observable<CityConditions> getCurrentLocationsWeather(String currentLat, String currentLon) {
        return mLocalDataSource.getCurrentCityConditions()
            .flatMap(new Func1<CityConditions, Observable<CityConditions>>() {
                @Override
                public Observable<CityConditions> call(CityConditions cityConditions) {
                    if (cityConditions != null) {
                        long dataAge = System.currentTimeMillis() - cityConditions.lastUpdated;
                        if (dataAge > CITY_CONDITIONS_EXPIRY) {
                            return updateConditionsFromRemote(currentLat, currentLon,
                                    cityConditions.cityId, cityConditions.currentCity);
                        } else {
                            return Observable.just(cityConditions);
                        }
                    } else {
                        return mRemoteDataSource.getCityConditions(currentLat, currentLon)
                                .doOnNext(remoteCityConditions -> {
                                    remoteCityConditions.currentCity= true;
                                    mLocalDataSource.saveCityConditionsData(remoteCityConditions);
                                });
                    }
                }
            });
    }

    /**
     * Immediately get the current weather conditions of this city and then save to local.
     */
    public Observable<CityConditions> getAndSaveNewCityConditions(String currentLatitude,
                                                                  String currentLongitude) {
        return mRemoteDataSource
                .getCityConditions(currentLatitude, currentLongitude)
                .doOnNext(conditions -> mLocalDataSource.saveCityConditionsData(conditions));
    }

    /**
     * Immediately get the current weather conditions of this city by city name and country code,
     * and then save to local.
     */
    public Observable<CityConditions> getAndSaveNewCityConditionsByName(String countryCode,
                                                                        String cityName) {
        return mRemoteDataSource
                .getCityConditionsByName(countryCode, cityName)
                .doOnNext(conditions -> mLocalDataSource.saveCityConditionsData(conditions));
    }

    /**
     * Private method to refresh weather data conditions from server.
     */
    private Observable<CityConditions> updateConditionsFromRemote(String latitude,
                                                                  String longitude,
                                                                  String cityId,
                                                                  boolean currentCity) {
        return mRemoteDataSource.getCityConditions(latitude, longitude)
                .doOnNext(newConditions -> {
                    newConditions.cityId = cityId;
                    newConditions.currentCity = currentCity;
                    mLocalDataSource.updateCityConditionsData(newConditions);
                });
    }

    @Override
    public void onDestroy() {
        mLocalDataSource.onDestroy();
        mRemoteDataSource.onDestroy();
    }
}
