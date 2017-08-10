package com.levarech.weatcher.data.city.mapper;

import com.levarech.weatcher.data.city.CityConditionEntity;
import com.levarech.weatcher.data.city.repository.source.local.RealmCityConditionEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by efronny on 7/4/17.
 * Mapper to transform {@link RealmCityConditionEntity} to {@link CityConditionEntity}
 */
@Singleton
public class RealmCityConditionMapper {

    @Inject
    public RealmCityConditionMapper() {
    }

    public CityConditionEntity transform(RealmCityConditionEntity realmCityConditionEntity) {
        CityConditionEntity cityConditionEntity = null;

        if (realmCityConditionEntity != null) {
            cityConditionEntity = new CityConditionEntity()
                    .setCityId(realmCityConditionEntity.getCityId())
                    .setCityName(realmCityConditionEntity.getCityName())
                    .setLatitude(realmCityConditionEntity.getLatitude())
                    .setLongitude(realmCityConditionEntity.getLongitude())
                    .setLocalEpoch(realmCityConditionEntity.getLocalEpoch())
                    .setLocalTzShort(realmCityConditionEntity.getLocalTzShort())
                    .setLocalTzLong(realmCityConditionEntity.getLocalTzLong())
                    .setLocalTzOffset(realmCityConditionEntity.getLocalTzOffset())
                    .setWeather(realmCityConditionEntity.getWeather())
                    .setIcon(realmCityConditionEntity.getIcon())
                    .setTemperatureString(realmCityConditionEntity.getTemperatureString())
                    .setTempF(realmCityConditionEntity.getTempF())
                    .setTempC(realmCityConditionEntity.getTempC())
                    .setCurrentCity(realmCityConditionEntity.isCurrentCity())
                    .setObservationTimestamp(realmCityConditionEntity.getObservationTimestamp());
        }

        return cityConditionEntity;
    }

    public RealmCityConditionEntity transform(CityConditionEntity cityConditionEntity) {
        RealmCityConditionEntity realmCity = null;

        if (cityConditionEntity != null) {
            realmCity = new RealmCityConditionEntity()
                    .setCityName(cityConditionEntity.getCityName())
                    .setLatitude(cityConditionEntity.getLatitude())
                    .setLongitude(cityConditionEntity.getLongitude())
                    .setLocalEpoch(cityConditionEntity.getLocalEpoch())
                    .setLocalTzShort(cityConditionEntity.getLocalTzShort())
                    .setLocalTzLong(cityConditionEntity.getLocalTzLong())
                    .setLocalTzOffset(cityConditionEntity.getLocalTzOffset())
                    .setWeather(cityConditionEntity.getWeather())
                    .setIcon(cityConditionEntity.getIcon())
                    .setTemperatureString(cityConditionEntity.getTemperatureString())
                    .setTempF(cityConditionEntity.getTempF())
                    .setTempC(cityConditionEntity.getTempC())
                    .setCurrentCity(cityConditionEntity.isCurrentCity())
                    .setObservationTimestamp(cityConditionEntity.getObservationTimestamp());

            if (cityConditionEntity.getCityId() != null) {
                realmCity.setCityId(cityConditionEntity.getCityId());
            }
        }

        return realmCity;
    }
}
