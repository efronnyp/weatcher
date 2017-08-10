package com.levarech.weatcher.data.city.repository.source.network.response;

/**
 * Created by EFR on 18/03/2017.
 * Current observation data of a city.
 */

public class CurrentObservation {

    private ImageInfo image;

    private DisplayLocation display_location;

    private ObservationLocation observation_location;

    private String observation_time;

    private String observation_time_rfc822;

    private Long observation_epoch;

    private String local_time_rfc822;

    private Long local_epoch;

    private String local_tz_short;

    private String local_tz_long;

    private String local_tz_offset;

    private String weather;

    private String temperature_string;

    private String temp_f;

    private String temp_c;

    private String relative_humidity;

    private String wind_string;

    private String wind_dir;

    private String feelslike_string;

    private String feelslike_f;

    private String feelslike_c;

    private String visibility_mi;

    private String visibility_km;

    private String UV;

    private String precip_1hr_string;

    private String precip_1hr_in;

    private String precip_1hr_metric;

    private String precip_today_string;

    private String precip_today_in;

    private String precip_today_metric;

    private String icon;

    private String icon_url;

    public ImageInfo getImage() {
        return image;
    }

    public CurrentObservation setImage(ImageInfo image) {
        this.image = image;
        return this;
    }

    public DisplayLocation getDisplayLocation() {
        return display_location;
    }

    public CurrentObservation setDisplayLocation(DisplayLocation display_location) {
        this.display_location = display_location;
        return this;
    }

    public ObservationLocation getObservationLocation() {
        return observation_location;
    }

    public CurrentObservation setObservationLocation(ObservationLocation observation_location) {
        this.observation_location = observation_location;
        return this;
    }

    public String getObservationTime() {
        return observation_time;
    }

    public CurrentObservation setObservationTime(String observation_time) {
        this.observation_time = observation_time;
        return this;
    }

    public String getObservationTimeRFC822() {
        return observation_time_rfc822;
    }

    public CurrentObservation setObservationTimeRFC822(String observation_time_rfc822) {
        this.observation_time_rfc822 = observation_time_rfc822;
        return this;
    }

    public Long getObservationEpoch() {
        return observation_epoch;
    }

    public CurrentObservation setObservationEpoch(Long observation_epoch) {
        this.observation_epoch = observation_epoch;
        return this;
    }

    public String getLocalTimeRFC822() {
        return local_time_rfc822;
    }

    public CurrentObservation setLocalTimeRFC822(String local_time_rfc822) {
        this.local_time_rfc822 = local_time_rfc822;
        return this;
    }

    public Long getLocalEpoch() {
        return local_epoch;
    }

    public CurrentObservation setLocalEpoch(Long local_epoch) {
        this.local_epoch = local_epoch;
        return this;
    }

    public String getLocalTzShort() {
        return local_tz_short;
    }

    public CurrentObservation setLocalTzShort(String local_tz_short) {
        this.local_tz_short = local_tz_short;
        return this;
    }

    public String getLocalTzLong() {
        return local_tz_long;
    }

    public CurrentObservation setLocalTzLong(String local_tz_long) {
        this.local_tz_long = local_tz_long;
        return this;
    }

    public String getLocalTzOffset() {
        return local_tz_offset;
    }

    public CurrentObservation setLocalTzOffset(String local_tz_offset) {
        this.local_tz_offset = local_tz_offset;
        return this;
    }

    public String getWeather() {
        return weather;
    }

    public CurrentObservation setWeather(String weather) {
        this.weather = weather;
        return this;
    }

    public String getTemperatureString() {
        return temperature_string;
    }

    public CurrentObservation setTemperatureString(String temperature_string) {
        this.temperature_string = temperature_string;
        return this;
    }

    public String getTempF() {
        return temp_f;
    }

    public CurrentObservation setTempF(String temp_f) {
        this.temp_f = temp_f;
        return this;
    }

    public String getTempC() {
        return temp_c;
    }

    public CurrentObservation setTempC(String temp_c) {
        this.temp_c = temp_c;
        return this;
    }

    public String getRelativeHumidity() {
        return relative_humidity;
    }

    public CurrentObservation setRelativeHumidity(String relative_humidity) {
        this.relative_humidity = relative_humidity;
        return this;
    }

    public String getWindString() {
        return wind_string;
    }

    public CurrentObservation setWindString(String wind_string) {
        this.wind_string = wind_string;
        return this;
    }

    public String getWindDir() {
        return wind_dir;
    }

    public CurrentObservation setWindDir(String wind_dir) {
        this.wind_dir = wind_dir;
        return this;
    }

    public String getFeelslikeString() {
        return feelslike_string;
    }

    public CurrentObservation setFeelslikeString(String feelslike_string) {
        this.feelslike_string = feelslike_string;
        return this;
    }

    public String getFeelslikeF() {
        return feelslike_f;
    }

    public CurrentObservation setFeelslikeF(String feelslike_f) {
        this.feelslike_f = feelslike_f;
        return this;
    }

    public String getFeelslikeC() {
        return feelslike_c;
    }

    public CurrentObservation setFeelslikeC(String feelslike_c) {
        this.feelslike_c = feelslike_c;
        return this;
    }

    public String getVisibilityMi() {
        return visibility_mi;
    }

    public CurrentObservation setVisibilityMi(String visibility_mi) {
        this.visibility_mi = visibility_mi;
        return this;
    }

    public String getVisibilityKm() {
        return visibility_km;
    }

    public CurrentObservation setVisibilityKm(String visibility_km) {
        this.visibility_km = visibility_km;
        return this;
    }

    public String getUV() {
        return UV;
    }

    public CurrentObservation setUV(String UV) {
        this.UV = UV;
        return this;
    }

    public String getPrecip1hrString() {
        return precip_1hr_string;
    }

    public CurrentObservation setPrecip1hrString(String precip_1hr_string) {
        this.precip_1hr_string = precip_1hr_string;
        return this;
    }

    public String getPrecip1hrIn() {
        return precip_1hr_in;
    }

    public CurrentObservation setPrecip1hrIn(String precip_1hr_in) {
        this.precip_1hr_in = precip_1hr_in;
        return this;
    }

    public String getPrecip1hrMetric() {
        return precip_1hr_metric;
    }

    public CurrentObservation setPrecip1hrMetric(String precip_1hr_metric) {
        this.precip_1hr_metric = precip_1hr_metric;
        return this;
    }

    public String getPrecipTodayString() {
        return precip_today_string;
    }

    public CurrentObservation setPrecipTodayString(String precip_today_string) {
        this.precip_today_string = precip_today_string;
        return this;
    }

    public String getPrecipTodayIn() {
        return precip_today_in;
    }

    public CurrentObservation setPrecipTodayIn(String precip_today_in) {
        this.precip_today_in = precip_today_in;
        return this;
    }

    public String getPrecipTodayMetric() {
        return precip_today_metric;
    }

    public CurrentObservation setPrecipTodayMetric(String precip_today_metric) {
        this.precip_today_metric = precip_today_metric;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public CurrentObservation setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getIconUrl() {
        return icon_url;
    }

    public CurrentObservation setIconUrl(String icon_url) {
        this.icon_url = icon_url;
        return this;
    }
}
