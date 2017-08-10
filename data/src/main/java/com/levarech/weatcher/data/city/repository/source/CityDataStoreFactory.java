package com.levarech.weatcher.data.city.repository.source;

import com.levarech.weatcher.data.city.mapper.NetworkCityConditionMapper;
import com.levarech.weatcher.data.city.mapper.RealmCityConditionMapper;
import com.levarech.weatcher.data.city.repository.source.local.RealmDataStore;
import com.levarech.weatcher.data.city.repository.source.network.WundergroundDataSource;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by efronny on 6/29/17.
 * Data Store Factory to Provide City Conditions data.
 */
@Singleton
public class CityDataStoreFactory {

    private RealmCityConditionMapper realmMapper;
    private NetworkCityConditionMapper networkMapper;

    @Inject
    public CityDataStoreFactory(RealmCityConditionMapper realmMapper,
                                NetworkCityConditionMapper networkMapper) {
        this.realmMapper = realmMapper;
        this.networkMapper = networkMapper;
    }

    public CityDataStore create() {
        return new RealmDataStore(realmMapper);
    }

    public CityDataStore createRemoteDataStore() {
        return new WundergroundDataSource(networkMapper);
    }
}
