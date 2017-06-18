package com.levarech.weatcher.presenter;

import android.content.Context;

import com.levarech.weatcher.R;
import com.levarech.weatcher.api.WeatherAPI;
import com.levarech.weatcher.model.HourlyForecast;
import com.levarech.weatcher.model.local.CityConditions;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by EFR on 17/03/2017.
 * Controller to get weather data from remote server.
 */

class RemoteDataSource implements BaseDataSource {

    private WeatherAPI weatherAPI;

    @Inject
    RemoteDataSource(Context context) {
        String url = WeatherAPI.WEATHER_API_URL
                .replace("{api_key}", context.getString(R.string.wunderground_api_key));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherAPI = retrofit.create(WeatherAPI.class);
    }

    Observable<CityConditions> getCityConditions(String latitude, String longitude) {
        return weatherAPI.getCurrentByLocation(latitude, longitude)
                .map(conditionsResponse -> {
                    //Construct CityConditions data from ConditionsResponse returned from API
                    CityConditions cityConditions = new CityConditions();
                    //cityConditions.cityId = to be generated before insert;
                    cityConditions.currentObservation = conditionsResponse.current_observation;
                    cityConditions.observationTimestamp = System.currentTimeMillis();
                    return cityConditions;
                })
                .subscribeOn(Schedulers.newThread());
    }

    Observable<CityConditions> getCityConditionsByName(String countryCode, String cityName) {
        return weatherAPI.getCurrentByCityName(countryCode, cityName)
                .map(conditionsResponse -> {
                    CityConditions cityConditions = new CityConditions();
                    //cityConditions.cityId = to be generated before insert;
                    cityConditions.currentObservation = conditionsResponse.current_observation;
                    cityConditions.observationTimestamp = System.currentTimeMillis();
                    return cityConditions;
                })
                .subscribeOn(Schedulers.newThread());
    }

    Observable<List<HourlyForecast>> getHourlyForecastByLocation(String latitude, String longitude) {
        return weatherAPI.getHourlyForecastByLocation(latitude, longitude)
                .map(hourlyForecastResponse -> hourlyForecastResponse.hourly_forecast)
                .subscribeOn(Schedulers.newThread());
    }

    @Override
    public void onDestroy() {
        // no-op
    }
}
