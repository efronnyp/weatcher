package com.levarech.weatcher.data.city.repository.source;

import com.levarech.weatcher.data.city.CityConditionEntity;

import java.util.List;

import rx.Observable;

/**
 * Created by efronny on 6/21/17.
 * Contract for both Remote and Local data source.
 */

public interface CityDataStore {
    /**
     * Get weather condition of saved cities.
     */
    Observable<List<CityConditionEntity>> getSavedCityWeather();

    /**
     * Save new city to database.
     */
    boolean saveCityCondition(CityConditionEntity cityConditionEntity);

    /**
     * Delete saved city from database.
     */
    Observable<Boolean> deleteSavedCity(String cityId);

    /**
     * Save user's current location, and immediately retrieve the weather condition information.
     */
    Observable<CityConditionEntity> getCurrentLocationWeather(
            double currentLatitude, double currentLongitude
    );

    /*
     * Just save the user's current location, and will not retrieve the weather conditions from API.<br>
     * If you want to save and retrieve user's current location weather,
     * use {@link #getCurrentLocationWeather(double, double)} method instead.
     */
    /*Observable<Boolean> saveCurrentLocation(
            double currentLatitude, double currentLongitude
    );*/

    /**
     * Get weather condition information of this location.
     */
    Observable<CityConditionEntity> getWeatherConditionByLocation(
            double latitude, double longitude
    );

    /**
     * Query weather condition of a city by country code and city name.
     */
    Observable<CityConditionEntity> getWeatherConditionByName(
            String countryCode, String cityName
    );
}
