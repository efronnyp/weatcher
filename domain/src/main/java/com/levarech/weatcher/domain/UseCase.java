package com.levarech.weatcher.domain;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).
 *
 * By convention each UseCase implementation will return the result using a {@link CompositeSubscription}
 * that will execute its job in a background thread and will post the result in the UI thread.
 */
public abstract class UseCase<T, Params> {

    private final CompositeSubscription subscriptions;

    protected UseCase() { this.subscriptions = new CompositeSubscription(); }

    /**
    * Builds an {@link Observable} which will be used when executing the current {@link UseCase}.
    */
    protected abstract Observable<T> buildUseCaseObservable(Params params);

    /**
     * Execute this use case with no parameter.
     */
    public void execute(Subscriber<T> subscriber) {
        execute(subscriber, null);
    }

    /**
     * Executes the current use case.
     */
    public void execute(Subscriber<T> subscriber, Params params) {
        final Subscription subscription = buildUseCaseObservable(params)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
        addSubscription(subscription);
    }

    /**
     * Unsubscribe from current {@link CompositeSubscription}.
     */
    public void dispose() {
        if (subscriptions.hasSubscriptions()) {
            subscriptions.unsubscribe();
        }
    }

    /**
     * Register {@link Subscription} to subscriptions manager.
     */
    private void addSubscription(Subscription subscription) {
          subscriptions.add(subscription);
    }
}
