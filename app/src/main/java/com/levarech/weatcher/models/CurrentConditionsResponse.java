package com.levarech.weatcher.models;

/**
 * Created by EFR on 16/03/2017.
 * A model class to map current weather conditions json.
 */

public class CurrentConditionsResponse {
    public ResponseData response;

    public class ImageInfo {
        public String url;
        public String title;
        public String link;
    }

    public class DisplayLocation {
        public String full;
        public String city;
        public String state;
        public String state_name;
        public String country;
        public String country_iso3166;
        public String zip;
        public String latitude;
        public String longitude;
    }

    public class ObservationLocation {
        public String full;
        public String city;
        public String state;
        public String country;
        public String country_iso3166;
        public String latitude;
        public String longitude;
    }

    public class CurrentObservation {
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
        public long temp_f;
        public long temp_c;
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
}
