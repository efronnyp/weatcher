package com.levarech.weatcher.domain.city.repository;

import com.levarech.weatcher.domain.city.CityCondition;

import java.util.List;

import rx.Observable;

/**
 * Created by efronny on 6/20/17.
 * Repository for saved city.
 */

public interface CityRepository {
    /**
     * Acquire weather conditions of all saved cities.
     */
    Observable<List<CityCondition>> getSavedCityWeather();

    /**
     * Get current location's conditions data.
     */
    Observable<CityCondition> getCurrentLocationWeather(double currentLatitude, double currentLongitude);

    /**
     * Save city of known latitude longitude value.
     */
    Observable<CityCondition> saveCityByLocation(double latitude, double longitude);

    /**
     * Save city of known country code and city name.
     */
    Observable<CityCondition> saveCityByName(String countryCode, String cityName);

    /**
     * Delete saved city from local storage.
     */
    Observable<Boolean> deleteSavedCity(String cityId);

    /**
     * Acquire weather conditions detail of the specified city.
     */
    Observable<String> getWeatherDetail(String cityId);
}
