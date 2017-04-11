package com.levarech.weatcher.presenter;

import com.levarech.weatcher.model.CurrentObservation;
import com.levarech.weatcher.model.DisplayLocation;
import com.levarech.weatcher.model.local.CityConditions;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by EFR on 17/03/2017.
 * Controller to manage local weather data using Realm engine.
 */

class LocalDataSource implements BaseDataSource {

    private Realm mRealm;

    LocalDataSource() {
        mRealm = Realm.getDefaultInstance();
    }

    Observable<List<CityConditions>> getSavedCitiesConditions() {
        return mRealm.where(CityConditions.class)
                .findAllSortedAsync("currentCity", Sort.DESCENDING)
                .asObservable()
                .filter(RealmResults::isLoaded)
                .flatMap(new Func1<RealmResults<CityConditions>, Observable<List<CityConditions>>>() {
                    @Override
                    public Observable<List<CityConditions>> call(RealmResults<CityConditions> results) {
                        return Observable.just(results);
                    }
                })
                .first();
    }

    Observable<CityConditions> getSavedCityConditions(String cityId) {
        CityConditions cityConditions = mRealm.where(CityConditions.class)
                .equalTo("cityId", cityId)
                .findFirst();
        return Observable.just(cityConditions);
    }

    Observable<CityConditions> getCurrentCityConditions() {
        return mRealm.where(CityConditions.class)
                .equalTo("currentCity", true)
                .findFirstAsync()
                .asObservable()
                .filter(realmObject -> realmObject.isLoaded())
                .map(realmObject -> (CityConditions) realmObject);
    }

    void insertOrUpdateCurrentLocation(String currentLatitude, String currentLongitude) {
        mRealm.executeTransaction(realm -> {
            CityConditions currentCity = realm
                    .where(CityConditions.class)
                    .equalTo("currentCity", true)
                    .findFirst();

            if (currentCity == null) {
                currentCity = new CityConditions();
                currentCity.cityId = UUID.randomUUID().toString();
                currentCity.currentCity = true;
                currentCity.currentObservation = new CurrentObservation(); // placeholder
                currentCity.currentObservation.display_location = new DisplayLocation(); // placeholder
            }
            currentCity.currentObservation.display_location.latitude = currentLatitude;
            currentCity.currentObservation.display_location.longitude = currentLongitude;
            realm.insertOrUpdate(currentCity);
        });
    }

    void updateCityConditionsData(CityConditions cityConditions) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 ->
            realm1.insertOrUpdate(cityConditions)
        );
        realm.close();
    }

    void saveCityConditionsData(CityConditions cityConditions) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            cityConditions.cityId = UUID.randomUUID().toString();
            realm1.insert(cityConditions);
        });
        realm.close();
    }

    void deleteCity(String cityId) {
        mRealm.executeTransaction(realm ->
                realm.where(CityConditions.class)
                    .equalTo("cityId", cityId)
                    .findFirst()
                    .deleteFromRealm());
    }

    @Override
    public void onDestroy() {
        mRealm.close();
    }
}
