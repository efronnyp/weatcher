package com.levarech.weatcher.presenter;

import android.content.Context;

import com.levarech.weatcher.view.WeatherView;

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

    private WeatherView mView;
    private CompositeSubscription mSubscription;
    private WeatherDataRepository mRepository;

    public WeatherPresenter(WeatherView view, Context context) {
        this.mView = view;
        mSubscription = new CompositeSubscription();
        mRepository = new WeatherDataRepository(new LocalDataSource(), new RemoteDataSource(context));
    }

    public void getSavedCitiesCondition() {
        mView.showLoading();
        createSubscription(mRepository.getSavedCitiesCondition(),
                cityConditionsList -> mView.onReceivedCityList(cityConditionsList));
    }

    public void getCurrentLocationWeather(double currentLatitude, double currentLongitude) {
        mView.showLoading();
        createSubscription(
                // observable
                mRepository.getCurrentLocationsWeather(Double.toString(currentLatitude),
                        Double.toString(currentLongitude)),
                // doOnNext
                cityConditions -> mView.onReceivedCurrentLocationConditions(cityConditions)
        );
    }

    public void getSavedCityDetail(String cityId) {

    }

    public void saveNewCity(double currentLatitude, double currentLongitude) {
        mView.showLoading();
        createSubscription(
                mRepository.getAndSaveNewCityConditions(
                        Double.toString(currentLatitude), Double.toString(currentLongitude)),
                conditions -> mView.onNewCitySuccessfullySaved(conditions)
        );
    }

    public void saveCityByName(String countryCode, String cityName) {
        mView.showLoading();
        createSubscription(
                mRepository.getAndSaveNewCityConditionsByName(countryCode, cityName),
                conditions -> mView.onNewCitySuccessfullySaved(conditions)
        );
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
        if (mSubscription.hasSubscriptions()) {
            mSubscription.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {
        if (mSubscription.hasSubscriptions()) {
            mSubscription.unsubscribe();
        }
        mRepository.onDestroy();
    }
}
