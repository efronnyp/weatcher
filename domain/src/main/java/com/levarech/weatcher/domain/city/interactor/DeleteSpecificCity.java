package com.levarech.weatcher.domain.city.interactor;

import com.levarech.weatcher.domain.UseCase;
import com.levarech.weatcher.domain.city.repository.CityRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by efronny on 7/6/17.
 * Delete saved city by cityId use case.
 */

public class DeleteSpecificCity extends UseCase<Boolean, DeleteSpecificCity.Params> {

    private CityRepository repository;

    @Inject
    public DeleteSpecificCity(CityRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Observable<Boolean> buildUseCaseObservable(Params params) {
        return repository.deleteSavedCity(params.cityId);
    }

    public static class Params {
        private String cityId;

        private Params(String cityId) {
            this.cityId = cityId;
        }

        public static Params construct(String cityId) {
            return new Params(cityId);
        }
    }
}
