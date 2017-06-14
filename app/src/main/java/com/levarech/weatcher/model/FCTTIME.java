package com.levarech.weatcher.model;

import io.realm.RealmObject;

/**
 * Created by EFR on 16/05/2017.
 * FCTTIME model class, appeared on HourlyForecast response.
 */

class FCTTIME extends RealmObject {
    public String hour;
    public String hour_padded;
    public String min;
    public String min_unpadded;
    public String sec;
    public String year;
    public String mon;
    public String mon_padded;
    public String mon_abbrev;
    public String mday;
    public String mday_padded;
    public String yday;
    public String isdst;
    public String epoch;
    public String pretty;
    public String civil;
    public String month_name;
    public String month_name_abbrev;
    public String weekday_name;
    public String weekday_name_night;
    public String weekday_name_abbrev;
    public String weekday_name_unlang;
    public String weekday_name_night_unlang;
    public String ampm;
    public String tz;
    public String age;
    public String UTCDATE;
}
