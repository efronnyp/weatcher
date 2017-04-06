package com.levarech.weatcher.presenter;

import android.content.Context;

import com.levarech.weatcher.view.BaseView;
import com.levarech.weatcher.view.WeatherAddView;
import com.levarech.weatcher.view.WeatherMonitorView;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by EFR on 16/03/2017.
 * Weather data provider and will decide whether to use local data or request a new one from remote.
 */

public class WeatherPresenter implements Presenter {

    private BaseView mView;
    private CompositeSubscription mSubscription;
    private WeatherDataRepository mRepository;

    public WeatherPresenter(BaseView view, Context context) {
        this.mView = view;
        mSubscription = new CompositeSubscription();
        mRepository = new WeatherDataRepository(new LocalDataSource(), new RemoteDataSource(context));
    }

    /**
     * Get saved cities including current city weather from local, or refresh it if already old.
     */
    public void getSavedCitiesCondition() {
        mView.showLoading();
        mSubscription.clear();
        Subscription subscription = mRepository
                .getSavedCitiesCondition()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        cityConditionsList -> ((WeatherMonitorView) mView).onReceivedCityList(cityConditionsList),
                        // onError
                        throwable -> {
                            throwable.printStackTrace();
                            mView.showError(throwable.getMessage());
                        },
                        // onComplete
                        () -> mView.hideLoading()
                );
        mSubscription.add(subscription);
    }

    public void getCurrentLocationWeather(double currentLatitude, double currentLongitude) {
        mView.showLoading();
        //mSubscription.clear();
        Subscription subscription = mRepository
                .getCurrentLocationsWeather(
                        Double.toString(currentLatitude), Double.toString(currentLongitude))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        cityConditions -> ((WeatherMonitorView) mView).onReceivedCurrentLocationConditions(cityConditions),
                        // onError
                        throwable -> {
                            throwable.printStackTrace();
                            mView.showError(throwable.getMessage());
                        },
                        // onComplete
                        () -> mView.hideLoading()
                );
        mSubscription.add(subscription);
    }

    public void getSavedCityDetail(String cityId) {

    }

    /**
     * Update current location latitude and longitude.
     */
    public void saveCurrentLocation(double currentLatitude, double currentLongitude) {
        mRepository.saveCurrentLocation(Double.toString(currentLatitude), Double.toString(currentLongitude));
    }

    /**
     * Get city conditions from API using location detail, and then save it to local.
     */
    public void saveNewCity(double currentLatitude, double currentLongitude) {
        mView.showLoading();
        createSubscription(
                mRepository.getAndSaveNewCityConditions(
                        Double.toString(currentLatitude), Double.toString(currentLongitude)),
                conditions -> ((WeatherAddView) mView).onNewCitySavedSuccessfully(conditions)
        );
    }

    public void saveCityByName(String countryCode, String cityName) {
        mView.showLoading();
        createSubscription(
                mRepository.getAndSaveNewCityConditionsByName(countryCode, cityName),
                conditions -> ((WeatherAddView) mView).onNewCitySavedSuccessfully(conditions)
        );
    }

    public void deleteSavedCity(String cityId) {
        mRepository.deleteSavedCity(cityId);
    }

    private <T> void createSubscription(Observable<T> observable, Action1<? super  T> doOnNext) {
        Subscription subscription = observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        doOnNext,
                        // onError
                        throwable -> {
                            throwable.printStackTrace();
                            mView.showError(throwable.getMessage());
                        },
                        // onComplete
                        () -> mView.hideLoading()
                );
        mSubscription.add(subscription);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {
        mSubscription.unsubscribe();
    }

    @Override
    public void onDestroy() {
        mSubscription.unsubscribe();
        mRepository.onDestroy();
    }
}
