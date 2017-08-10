package com.levarech.weatcher.data.city;

/**
 * Created by EFR on 16/03/2017.
 * City Condition Entity Model for data layer.
 */

public class CityConditionEntity {

    private String city_id;

    private String city_name;

    private double latitude;

    private double longitude;

    private Long local_epoch;

    private String local_tz_short;

    private String local_tz_long;

    private String local_tz_offset;

    private String weather;

    private String icon;

    private String temperature_string;

    private String temp_f;

    private String temp_c;

    private boolean currentCity;

    private long observationTimestamp;

    /**
     * City ID
     */
    public String getCityId() {
        return city_id;
    }

    /**
     * Set city ID value. The ID will be random UUID that generated automatically.
     */
    public CityConditionEntity setCityId(String city_id) {
        this.city_id = city_id;
        return this;
    }

    /**
     * The name of this city.
     */
    public String getCityName() {
        return city_name;
    }

    /**
     * Set city name.
     */
    public CityConditionEntity setCityName(String city_name) {
        this.city_name = city_name;
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
    public CityConditionEntity setLatitude(double latitude) {
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
    public CityConditionEntity setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    /**
     * Local time in epoch format for this city.
     */
    public Long getLocalEpoch() {
        return local_epoch;
    }

    /**
     * Set local epoch value.
     */
    public CityConditionEntity setLocalEpoch(Long local_epoch) {
        this.local_epoch = local_epoch;
        return this;
    }

    /**
     * Local timezone in short format, ex: "PST"
     */
    public String getLocalTzShort() {
        return local_tz_short;
    }

    /**
     * Set abbreviated local timezone.
     */
    public CityConditionEntity setLocalTzShort(String local_tz_short) {
        this.local_tz_short = local_tz_short;
        return this;
    }

    /**
     * Local timezone in long format, ex: "America/Los_Angeles"
     */
    public String getLocalTzLong() {
        return local_tz_long;
    }

    /**
     * Set local timezone fullname.
     */
    public CityConditionEntity setLocalTzLong(String local_tz_long) {
        this.local_tz_long = local_tz_long;
        return this;
    }

    /**
     * Local timezone offset, ex: "-0700"
     */
    public String getLocalTzOffset() {
        return local_tz_offset;
    }

    /**
     * Set local timezone offset value.
     */
    public CityConditionEntity setLocalTzOffset(String local_tz_offset) {
        this.local_tz_offset = local_tz_offset;
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
    public CityConditionEntity setWeather(String weather) {
        this.weather = weather;
        return this;
    }

    /**
     * Get weather icon code to be displayed.
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Set weather icon code to represent this city conditions.
     */
    public CityConditionEntity setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    /**
     * Temperature value in string and have both Fahrenheit and Celcius value, ex: "66.3 F (19.1 C)"
     */
    public String getTemperatureString() {
        return temperature_string;
    }

    /**
     * Set complete temperature string (in both Fahrenheit and Celcius value).
     */
    public CityConditionEntity setTemperatureString(String temperature_string) {
        this.temperature_string = temperature_string;
        return this;
    }

    /**
     * Temperature value in Fahrenheit, ex: 66.3
     */
    public String getTempF() {
        return temp_f;
    }

    /**
     * Set temperature value in Fahrenheit.
     */
    public CityConditionEntity setTempF(String temp_f) {
        this.temp_f = temp_f;
        return this;
    }

    /**
     * Temperature value in Celcius, ex: 19.1
     */
    public String getTempC() {
        return temp_c;
    }

    /**
     * Set temperature value in Celcius.
     */
    public CityConditionEntity setTempC(String temp_c) {
        this.temp_c = temp_c;
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
    public CityConditionEntity setCurrentCity(boolean currentCity) {
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
    public CityConditionEntity setObservationTimestamp(long observationTimestamp) {
        this.observationTimestamp = observationTimestamp;
        return this;
    }
}
