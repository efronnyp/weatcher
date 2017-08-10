package com.levarech.weatcher.data.city.repository.source.network.response;

/**
 * Created by EFR on 19/03/2017.
 * City location info of current_observation instance.
 */

public class DisplayLocation {

    private String full;

    private String city;

    private String state;

    private String state_name;

    private String country;

    private String country_iso3166;

    private String zip;

    private String magic;

    private String wmo;

    private String latitude;

    private String longitude;

    public String getFull() {
        return full;
    }

    public DisplayLocation setFull(String full) {
        this.full = full;
        return this;
    }

    public String getCity() {
        return city;
    }

    public DisplayLocation setCity(String city) {
        this.city = city;
        return this;
    }

    public String getState() {
        return state;
    }

    public DisplayLocation setState(String state) {
        this.state = state;
        return this;
    }

    public String getStateName() {
        return state_name;
    }

    public DisplayLocation setStateName(String state_name) {
        this.state_name = state_name;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public DisplayLocation setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCountryIso3166() {
        return country_iso3166;
    }

    public DisplayLocation setCountryIso3166(String country_iso3166) {
        this.country_iso3166 = country_iso3166;
        return this;
    }

    public String getZip() {
        return zip;
    }

    public DisplayLocation setZip(String zip) {
        this.zip = zip;
        return this;
    }

    public String getMagic() {
        return magic;
    }

    public DisplayLocation setMagic(String magic) {
        this.magic = magic;
        return this;
    }

    public String getWmo() {
        return wmo;
    }

    public DisplayLocation setWmo(String wmo) {
        this.wmo = wmo;
        return this;
    }

    public String getLatitude() {
        return latitude;
    }

    public DisplayLocation setLatitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public String getLongitude() {
        return longitude;
    }

    public DisplayLocation setLongitude(String longitude) {
        this.longitude = longitude;
        return this;
    }
}
