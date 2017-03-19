package com.levarech.weatcher.model;

import io.realm.RealmObject;

/**
 * Created by EFR on 19/03/2017.
 * City location info of current_observation instance.
 */

public class DisplayLocation extends RealmObject {
    public String full;
    public String city;
    public String state;
    public String state_name;
    public String country;
    public String country_iso3166;
    public String zip;
    public String magic;
    public String wmo;
    public String latitude;
    public String longitude;
}
