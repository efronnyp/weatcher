package com.levarech.weatcher.presenter;

import android.content.Context;

import com.levarech.weatcher.R;
import com.levarech.weatcher.api.WeatherService;
import com.levarech.weatcher.model.local.CityConditions;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by EFR on 17/03/2017.
 * Controller to get weather data from remote server.
 */

public class RemoteDataSource implements BaseDataSource {

    private WeatherService weatherService;

    public RemoteDataSource(Context context) {
        String url = WeatherService.WEATHER_API_URL
                .replace("{api_key}", context.getString(R.string.wunderground_api_key));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        weatherService = retrofit.create(WeatherService.class);
    }

    public Observable<CityConditions> getCityConditions(String latitude, String longitude) {
        return weatherService.getCurrentByLocation(latitude, longitude)
                .flatMap(conditionsResponse -> {
                    //Construct CityConditions data from ConditionsResponse returned from API
                    CityConditions cityConditions = new CityConditions();
                    //cityConditions.cityId = to be generated before insert;
                    cityConditions.currentObservation = conditionsResponse.current_observation;
                    cityConditions.lastUpdated = System.currentTimeMillis();
                    return Observable.just(cityConditions);
                })
                .subscribeOn(Schedulers.newThread());
    }

    public Observable<CityConditions> getCityConditionsByName(String countryCode, String cityName) {
        return weatherService.getCurrentByCityName(countryCode, cityName)
                .map(conditionsResponse -> {
                    CityConditions cityConditions = new CityConditions();
                    //cityConditions.cityId = to be generated before insert;
                    cityConditions.currentObservation = conditionsResponse.current_observation;
                    cityConditions.lastUpdated = System.currentTimeMillis();
                    return cityConditions;
                })
                .subscribeOn(Schedulers.newThread());
    }

    @Override
    public void onDestroy() {
        // no-op
    }
}
