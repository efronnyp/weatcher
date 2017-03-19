package com.levarech.weatcher.presenter;

import com.levarech.weatcher.model.local.CityConditions;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by EFR on 17/03/2017.
 * Controller to manage local weather data using Realm engine.
 */

public class LocalDataSource implements BaseDataSource {

    private Realm mRealm;

    LocalDataSource() {
        mRealm = Realm.getDefaultInstance();
    }

    public Observable<List<CityConditions>> getSavedCitiesConditions() {
        //Realm realm = Realm.getDefaultInstance();
        RealmResults<CityConditions> results = mRealm.where(CityConditions.class)
                .equalTo("currentCity", false)
                .findAll();
        return results.asObservable()
                .flatMap(new Func1<RealmResults<CityConditions>, Observable<List<CityConditions>>>() {
                    @Override
                    public Observable<List<CityConditions>> call(RealmResults<CityConditions> results1) {
                        return Observable.just(results1);
                    }
                });
        /*return mRealm.where(CityConditions.class)
                .equalTo("currentCity", false)
                .findAllAsync()
                .asObservable()
                .filter(new Func1<RealmResults<CityConditions>, Boolean>() {
                    @Override
                    public Boolean call(RealmResults<CityConditions> results) {
                        return results.isLoaded();
                    }
                })
                .flatMap(new Func1<RealmResults<CityConditions>, Observable<List<CityConditions>>>() {
                    @Override
                    public Observable<List<CityConditions>> call(RealmResults<CityConditions> cityConditionses) {
                        return Observable.from(cityConditionses).toList();
                    }
                });*/
    }

    /*public Observable<CityConditions> getSavedCityConditions(String latitude, String longitude) {
        Realm realm = Realm.getDefaultInstance();
        CityConditions cityConditions = realm.where(CityConditions.class)
                .equalTo("currentObservation.display_location.latitude", latitude)
                .equalTo("currentObservation.display_location.latitude", longitude)
                .findFirst();
        return cityConditions != null ? cityConditions.asObservable() : Observable.just(null);
    }*/

    public Observable<CityConditions> getSavedCityConditions(String cityId) {
        Realm realm = Realm.getDefaultInstance();
        CityConditions cityConditions = realm.where(CityConditions.class)
                .equalTo("currentObservation.display_location.latitude", cityId)
                .findFirst();
        return Observable.just(cityConditions);
    }

    public Observable<CityConditions> getCurrentCityConditions() {
        CityConditions cityConditions = mRealm.where(CityConditions.class)
                .equalTo("currentCity", true)
                .findFirst();

        return cityConditions != null ? cityConditions.asObservable() : Observable.just(null);
        /*return mRealm.where(CityConditions.class)
                .equalTo("currentCity", true)
                .findFirstAsync()
                .asObservable()
                .filter(realmObject -> {
                    return realmObject.isLoaded();
                })
                .switchIfEmpty(Observable.just(new CityConditions()))
                .flatMap(new Func1<RealmObject, Observable<CityConditions>>() {
                    @Override
                    public Observable<CityConditions> call(RealmObject realmObject) {
                        return Observable.just((CityConditions) realmObject);
                    }
                });*/
    }

    public void updateCityConditionsData(CityConditions cityConditions) {
        /*Observable.just(cityConditions)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(conditions ->
                        mRealm.executeTransactionAsync(realm -> realm.insertOrUpdate(conditions)),
                        Throwable::printStackTrace
                );*/
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransactionAsync(realm1 ->
            realm1.insertOrUpdate(cityConditions)
        );
    }

    public void saveCityConditionsData(CityConditions cityConditions) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            cityConditions.cityId = UUID.randomUUID().toString();
            realm1.insert(cityConditions);
        });
    }

    @Override
    public void onDestroy() {
        mRealm.close();
    }
}
