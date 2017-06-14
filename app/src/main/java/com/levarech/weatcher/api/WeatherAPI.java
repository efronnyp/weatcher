package com.levarech.weatcher.api;

import com.levarech.weatcher.model.remote.ConditionsResponse;
import com.levarech.weatcher.model.remote.HourlyForecastResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by EFR on 16/03/2017.
 * Interface to communicate with WeatherUnderground API Service.
 */

public interface WeatherAPI {
    String WEATHER_API_URL = "http://api.wunderground.com/api/{api_key}/";

    /**
     * Get current weather conditions using country code and city name as parameter. <br>
     * Example: {@code http://api.wunderground.com/api/api_key/conditions/q/ID/Jakarta_Selatan.json}
     * @param countryCode ISO 3166 Country Code
     * @param cityName The Name of The City
     */
    @GET("conditions/q/{country_code}/{city_name}.json")
    Observable<ConditionsResponse> getCurrentByCityName(@Path("country_code") String countryCode,
                                                        @Path("city_name") String cityName);

    /**
     * Get current weather conditions using location latitude and longitude as parameter. <br>
     * Example: {@code http://api.wunderground.com/api/api_key/conditions/q/-6.292616,106.783556.json}
     * @param latitude Latitude value of location
     * @param longitude Longitude value of location
     */
    @GET("conditions/q/{lat},{lon}.json")
    Observable<ConditionsResponse> getCurrentByLocation(@Path("lat") String latitude,
                                                        @Path("lon") String longitude);

    /**
     * Get weather forecast for next 10 days using country code and city name as parameter. <br>
     * Example: {@code http://api.wunderground.com/api/api_key/forecast10day/q/ID/Jakarta_Selatan.json}
     * @param countryCode ISO 3166 Country Code
     * @param cityName The Name of The City
     */
    @GET("forecast10day/q/{country_code}/{city_name}.json")
    Observable<String> getForecastForNext10Days(@Path("country_code") String countryCode,
                                                @Path("city_name") String cityName);

    /**
     * Get weather forecast hourly for today and tomorrow. <br>
     * Example: {@code http://api.wunderground.com/api/api_key/hourly/q/-6.207978,106.843465.json}
     * @param latitude Latitude value of desired location
     * @param longitude Longitude value of desired location
     */
    @GET("hourly/q/{lat},{lon}.json")
    Observable<HourlyForecastResponse> getHourlyForecastByLocation(@Path("lat") String latitude,
                                                                   @Path("lon") String longitude);
}
