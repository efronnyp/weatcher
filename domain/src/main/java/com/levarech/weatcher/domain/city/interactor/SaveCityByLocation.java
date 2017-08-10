package com.levarech.weatcher.domain.city.interactor;

import com.levarech.weatcher.domain.UseCase;
import com.levarech.weatcher.domain.city.CityCondition;
import com.levarech.weatcher.domain.city.repository.CityRepository;
import com.levarech.weatcher.domain.interactor.params.LocationParams;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by efronny on 6/21/17.
 * Save new city of specified Location detail.
 */

public class SaveCityByLocation extends UseCase<CityCondition, LocationParams> {

    private CityRepository cityRepository;

    @Inject
    public SaveCityByLocation(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @Override
    protected Observable<CityCondition> buildUseCaseObservable(LocationParams locationParams) {
        return cityRepository.saveCityByLocation(
                locationParams.getLatitude(),
                locationParams.getLongitude()
        );
    }
}
