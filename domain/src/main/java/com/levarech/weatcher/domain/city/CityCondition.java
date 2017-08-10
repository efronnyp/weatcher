package com.levarech.weatcher.domain.city;

/**
 * Created by EFR on 16/03/2017.
 * A model class to map current weather conditions json.
 */

public class CityCondition {

    private String city_id;

    private String city_name;

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

    /**
     * City ID
     */
    public String getCityId() {
        return city_id;
    }

    /**
     * Set city ID value. The ID will be random UUID that generated automatically.
     */
    public CityCondition setCityId(String city_id) {
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
    public CityCondition setCityName(String city_name) {
        this.city_name = city_name;
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
    public CityCondition setLocalEpoch(Long local_epoch) {
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
    public CityCondition setLocalTzShort(String local_tz_short) {
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
    public CityCondition setLocalTzLong(String local_tz_long) {
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
    public CityCondition setLocalTzOffset(String local_tz_offset) {
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
    public CityCondition setWeather(String weather) {
        this.weather = weather;
        return this;
    }

    /**
     * Get weather icon to represent this city's condition.
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Set weather icon to represent this city's condition.
     */
    public CityCondition setIcon(String icon) {
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
    public CityCondition setTemperatureString(String temperature_string) {
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
    public CityCondition setTempF(String temp_f) {
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
    public CityCondition setTempC(String temp_c) {
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
    public CityCondition setCurrentCity(boolean currentCity) {
        this.currentCity = currentCity;
        return this;
    }
}
