package com.levarech.weatcher.model.remote;

import com.levarech.weatcher.model.HourlyForecast;

import java.util.List;

/**
 * Created by EFR on 11/04/2017.
 * Hourly forecast response data returned from /hourly
 */

public class HourlyForecastResponse {
    public ResponseData response;
    public List<HourlyForecast> hourly_forecast;
}
