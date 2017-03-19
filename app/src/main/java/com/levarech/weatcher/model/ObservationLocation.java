package com.levarech.weatcher.model;

import io.realm.RealmObject;

/**
 * Created by EFR on 19/03/2017.
 * Observation location of where this weather data collected from.
 */

public class ObservationLocation extends RealmObject {
    public String full;
    public String city;
    public String state;
    public String country;
    public String country_iso3166;
    public String latitude;
    public String longitude;
}
