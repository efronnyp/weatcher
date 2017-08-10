package com.levarech.weatcher.data.city.repository.source.local;

import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by EFR on 16/03/2017.
 * City Condition Entity Model for data layer.
 */

public class RealmCityConditionEntity extends RealmObject {

    @PrimaryKey
    private String cityId;

    private String cityName;

    private double latitude;

    private double longitude;

    private Long localEpoch;

    private String localTzShort;

    private String localTzLong;

    private String localTzOffset;

    private String weather;

    private String icon;

    private String temperatureString;

    private String tempF;

    private String tempC;

    private boolean currentCity;

    private long observationTimestamp;

    public RealmCityConditionEntity() {
        cityId = UUID.randomUUID().toString();
    }

    /**
     * City ID
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * Set city ID value. The ID will be random UUID that generated automatically.
     */
    public RealmCityConditionEntity setCityId(String city_id) {
        this.cityId = city_id;
        return this;
    }

    /**
     * The name of this city.
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * Set city name.
     */
    public RealmCityConditionEntity setCityName(String city_name) {
        this.cityName = city_name;
        return this;
    }

    /**
     * This city latitude value.
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * Set this city latitude value.
     */
    public RealmCityConditionEntity setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }

    /**
     * This city longitude value.
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * Set this city longitude value.
     */
    public RealmCityConditionEntity setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    /**
     * Local time in epoch format for this city.
     */
    public Long getLocalEpoch() {
        return localEpoch;
    }

    /**
     * Set local epoch value.
     */
    public RealmCityConditionEntity setLocalEpoch(Long local_epoch) {
        this.localEpoch = local_epoch;
        return this;
    }

    /**
     * Local timezone in short format, ex: "PST"
     */
    public String getLocalTzShort() {
        return localTzShort;
    }

    /**
     * Set abbreviated local timezone.
     */
    public RealmCityConditionEntity setLocalTzShort(String local_tz_short) {
        this.localTzShort = local_tz_short;
        return this;
    }

    /**
     * Local timezone in long format, ex: "America/Los_Angeles"
     */
    public String getLocalTzLong() {
        return localTzLong;
    }

    /**
     * Set local timezone fullname.
     */
    public RealmCityConditionEntity setLocalTzLong(String local_tz_long) {
        this.localTzLong = local_tz_long;
        return this;
    }

    /**
     * Local timezone offset, ex: "-0700"
     */
    public String getLocalTzOffset() {
        return localTzOffset;
    }

    /**
     * Set local timezone offset value.
     */
    public RealmCityConditionEntity setLocalTzOffset(String local_tz_offset) {
        this.localTzOffset = local_tz_offset;
        return this;
    }

    /**
     * Weather information, ex: "Partly Cloudy"
     */
    public String getWeather() {
        return weather;
    }

    /**
     * Set weather information.
     */
    public RealmCityConditionEntity setWeather(String weather) {
        this.weather = weather;
        return this;
    }

    /**
     * Get weather icon to be displayed.
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Set appropriate weather icon of this city.
     */
    public RealmCityConditionEntity setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Temperature value in string and have both Fahrenheit and Celcius value, ex: "66.3 F (19.1 C)"
     */
    public String getTemperatureString() {
        return temperatureString;
    }

    /**
     * Set complete temperature string (in both Fahrenheit and Celcius value).
     */
    public RealmCityConditionEntity setTemperatureString(String temperature_string) {
        this.temperatureString = temperature_string;
        return this;
    }

    /**
     * Temperature value in Fahrenheit, ex: 66.3
     */
    public String getTempF() {
        return tempF;
    }

    /**
     * Set temperature value in Fahrenheit.
     */
    public RealmCityConditionEntity setTempF(String temp_f) {
        this.tempF = temp_f;
        return this;
    }

    /**
     * Temperature value in Celcius, ex: 19.1
     */
    public String getTempC() {
        return tempC;
    }

    /**
     * Set temperature value in Celcius.
     */
    public RealmCityConditionEntity setTempC(String temp_c) {
        this.tempC = temp_c;
        return this;
    }

    /**
     * A flag to indicate whether this city is current location.
     */
    public boolean isCurrentCity() {
        return currentCity;
    }

    /**
     * Indicate whether this city is current or not.
     */
    public RealmCityConditionEntity setCurrentCity(boolean currentCity) {
        this.currentCity = currentCity;
        return this;
    }

    /**
     * Last update timestamp of observation data.
     */
    public long getObservationTimestamp() {
        return observationTimestamp;
    }

    /**
     * Set observation timestamp of this city.
     */
    public RealmCityConditionEntity setObservationTimestamp(long observationTimestamp) {
        this.observationTimestamp = observationTimestamp;
        return this;
    }
}
