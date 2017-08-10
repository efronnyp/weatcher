package com.levarech.weatcher.domain.city.interactor;

import com.levarech.weatcher.domain.UseCase;
import com.levarech.weatcher.domain.city.CityCondition;
import com.levarech.weatcher.domain.city.repository.CityRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by efronny on 7/10/17.
 * Use case for save new city by city name process.
 */

public class SaveCityByName extends UseCase<CityCondition, SaveCityByName.Params> {

    private CityRepository cityRepository;

    @Inject
    public SaveCityByName(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    protected Observable<CityCondition> buildUseCaseObservable(Params params) {
        return cityRepository.saveCityByName(params.countryCode, params.cityName);
    }

    public static class Params {

        private String countryCode;
        private String cityName;

        private Params(String countryCode, String cityName) {
            this.countryCode = countryCode;
            this.cityName = cityName;
        }

        public static Params from(String countryCode, String cityName) {
            return new Params(countryCode, cityName);
        }

    }
}
