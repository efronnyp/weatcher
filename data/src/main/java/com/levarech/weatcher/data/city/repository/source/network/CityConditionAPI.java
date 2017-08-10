package com.levarech.weatcher.data.city.repository.source.network;

import com.levarech.weatcher.data.city.repository.source.network.response.ConditionsResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by efronny on 7/3/17.
 * API Contract to Get City Conditions Data from Wunderground Web Service.
 */

interface CityConditionAPI {

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
}
