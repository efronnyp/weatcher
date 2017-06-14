package com.levarech.weatcher.model.local;

import com.levarech.weatcher.model.CurrentObservation;
import com.levarech.weatcher.model.HourlyForecast;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by EFR on 16/03/2017.
 * A model class to map current weather conditions json.
 */

public class CityConditions extends RealmObject {
    /**
     * This city's id, generated internally.
     */
    @PrimaryKey public String cityId;

    /**
     * Current observation data.
     */
    public CurrentObservation currentObservation;

    /**
     * Hourly weather forecast data.
     */
    public RealmList<HourlyForecast> hourlyForecasts;

    /**
     * A flag to indicate whether this city is current location.
     */
    public boolean currentCity;

    /**
     * Last update timestamp of observation data. The value is taken from System.currentTimeMillis().
     */
    public long observationTimestamp;

    /**
     * Last update timestamp of hourly forecast data of this city.
     */
    public long hourlyForecastTimestamp;
}
