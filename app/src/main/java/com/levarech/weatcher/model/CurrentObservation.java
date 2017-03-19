package com.levarech.weatcher.model;

import io.realm.RealmObject;

/**
 * Created by EFR on 18/03/2017.
 * Current observation data of a city.
 */

public class CurrentObservation extends RealmObject {
    public ImageInfo image;
    public DisplayLocation display_location;
    public ObservationLocation observation_location;
    public String observation_time;
    public String observation_time_rfc822;
    public Long observation_epoch;
    public String local_time_rfc822;
    public Long local_epoch;
    public String local_tz_short;
    public String local_tz_long;
    public String local_tz_offset;
    public String weather;
    public String temperature_string;
    public int temp_f;
    public int temp_c;
    public String relative_humidity;
    public String wind_string;
    public String wind_dir;
    public String feelslike_string;
    public String feelslike_f;
    public String feelslike_c;
    public String visibility_mi;
    public String visibility_km;
    public String UV;
    public String precip_1hr_string;
    public String precip_1hr_in;
    public String precip_1hr_metric;
    public String precip_today_string;
    public String precip_today_in;
    public String precip_today_metric;
    public String icon;
    public String icon_url;
}
