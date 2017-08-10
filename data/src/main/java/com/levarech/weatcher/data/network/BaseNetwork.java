package com.levarech.weatcher.data.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by efronny on 7/5/17.
 * Base class for network data source that using retrofit.
 */

public abstract class BaseNetwork<T> {

    private T retrofitService;

    protected BaseNetwork() {
    }

    private void initService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(this.getBaseUrl())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        retrofitService = retrofit.create(getServiceClass());
    }

    protected T getService() {
        if (retrofitService == null) {
            initService();
        }

        return retrofitService;
    }

    protected abstract String getBaseUrl();

    protected abstract Class<T> getServiceClass();
}
