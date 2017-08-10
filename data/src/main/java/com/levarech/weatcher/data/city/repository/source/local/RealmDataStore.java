package com.levarech.weatcher.data.city.repository.source.local;

import com.levarech.weatcher.data.city.CityConditionEntity;
import com.levarech.weatcher.data.city.mapper.RealmCityConditionMapper;
import com.levarech.weatcher.data.city.repository.source.CityDataStore;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by efronny on 6/29/17.
 * Data provider from local storage.
 */

public class RealmDataStore implements CityDataStore {

    private Realm mRealm;
    private RealmCityConditionMapper mMapper;

    public RealmDataStore(RealmCityConditionMapper mapper) {
        mRealm = Realm.getDefaultInstance();
        mMapper = mapper;
    }

    @Override
    public Observable<List<CityConditionEntity>> getSavedCityWeather() {
        return mRealm.where(RealmCityConditionEntity.class)
            .notEqualTo("currentCity", true)
            .findAllSortedAsync("cityName")
            .asObservable()
            .filter(RealmResults::isLoaded)
            .flatMap(new Func1<RealmResults<RealmCityConditionEntity>,
                    Observable<List<CityConditionEntity>>>() {
                @Override
                public Observable<List<CityConditionEntity>> call(
                        RealmResults<RealmCityConditionEntity> realmResults) {
                    return Observable.from(realmResults)
                        .map(realmEntity -> mMapper.transform(realmEntity))
                        .toList();
                }
            })
            .first();
    }

    @Override
    public boolean saveCityCondition(CityConditionEntity cityCondition) {
        try {
            return insertOrUpdate(mMapper.transform(cityCondition));
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Observable<Boolean> deleteSavedCity(String cityId) {
        return Observable.just(delete(cityId));
    }

    @Override
    public Observable<CityConditionEntity> getCurrentLocationWeather(
            double currentLatitude, double currentLongitude) {
        return mRealm.where(RealmCityConditionEntity.class)
            .equalTo("currentCity", true)
            .findFirstAsync()
            .asObservable()
            .filter(realmObject -> realmObject.isLoaded())
            .map(realmObject -> {
                if (realmObject.isValid())
                    return mMapper.transform((RealmCityConditionEntity) realmObject);
                else
                    return new CityConditionEntity().setCurrentCity(true);
            })
            .first();
    }

    @Override
    public Observable<CityConditionEntity> getWeatherConditionByLocation(
            double latitude, double longitude) {
        return mRealm.where(RealmCityConditionEntity.class)
            .equalTo("latitude", latitude)
            .equalTo("longitude", longitude)
            .findFirstAsync()
            .asObservable()
            .filter(realmObject -> realmObject.isLoaded())
            .map(realmObject -> mMapper.transform((RealmCityConditionEntity) realmObject));
    }

    @Override
    public Observable<CityConditionEntity> getWeatherConditionByName(
            String countryCode, String cityName) {
        return mRealm.where(RealmCityConditionEntity.class)
                .equalTo("cityName", cityName)
                .findFirstAsync()
                .asObservable()
                .filter(realmObject -> realmObject.isLoaded())
                .map(realmObject -> mMapper.transform((RealmCityConditionEntity) realmObject));
    }

    private boolean insertOrUpdate(final RealmCityConditionEntity cityConditionEntity) {
        mRealm.executeTransaction(realm ->
            realm.insertOrUpdate(cityConditionEntity)
        );
        return true;
    }

    private boolean delete(String cityId) {
        mRealm.where(RealmCityConditionEntity.class)
                .equalTo("cityId", cityId)
                .findFirst()
                .deleteFromRealm();

        return true;
    }
}
