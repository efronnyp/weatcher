package com.levarech.weatcher.domain.city.interactor;

import com.levarech.weatcher.domain.UseCase;
import com.levarech.weatcher.domain.city.CityCondition;
import com.levarech.weatcher.domain.city.repository.CityRepository;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by efronny on 6/20/17.
 * Get saved city weather condition Use Case.
 */

public class GetSavedCityCondition extends UseCase<List<CityCondition>, Void> {

    private CityRepository cityRepository;

    @Inject
    public GetSavedCityCondition(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    protected Observable<List<CityCondition>> buildUseCaseObservable(Void notUsed) {
        return cityRepository.getSavedCityWeather();
    }
}
