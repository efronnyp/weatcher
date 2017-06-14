package com.levarech.weatcher.model;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by EFR on 11/04/2017.
 * "hourly_forecast" item model class.
 */

public class HourlyForecast extends RealmObject {
    @SerializedName("FCTTIME")
    public FCTTIME fcttime;
    public Unit temp;
    public Unit dewpoint;
    public String condition;
    public String icon;
    public String icon_url;
    public String fctcode;
    public String sky;
    public Unit wspd;
    public WindDirection wdir;
    public String wx;
    public String uvi;
    public String humidity;
    public Unit windchill;
    public Unit heatindex;
    public Unit feelslike;
    public Unit qpf;
    public Unit snow;
    public String pop;
    public Unit mslp;
}
