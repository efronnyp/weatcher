package com.levarech.weatcher.data.city.repository.source.network.response;

/**
 * Created by EFR on 19/03/2017.
 * Observation location of where this weather data collected from.
 */

class ObservationLocation {

    private String full;

    private String city;

    private String state;

    private String country;

    private String country_iso3166;

    private String latitude;

    private String longitude;

    public String getFull() {
        return full;
    }

    public ObservationLocation setFull(String full) {
        this.full = full;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ObservationLocation setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public ObservationLocation setState(String state) {
        this.state = state;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public ObservationLocation setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCountryIso3166() {
        return country_iso3166;
    }

    public ObservationLocation setCountryIso3166(String country_iso3166) {
        this.country_iso3166 = country_iso3166;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public ObservationLocation setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public ObservationLocation setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }
}
