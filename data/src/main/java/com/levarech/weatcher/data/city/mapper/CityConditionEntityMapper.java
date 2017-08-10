package com.levarech.weatcher.data.city.mapper;

import com.levarech.weatcher.data.city.CityConditionEntity;
import com.levarech.weatcher.domain.city.CityCondition;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by efronny on 7/5/17.
 * Mapper to transform {@link CityConditionEntity} to {@link CityCondition}
 */
@Singleton
public class CityConditionEntityMapper {

    @Inject
    public CityConditionEntityMapper() {
    }

    public CityCondition transform(CityConditionEntity cityConditionEntity) {
        CityCondition cityCondition = null;

        if (cityConditionEntity != null) {
            cityCondition = new CityCondition()
                    .setCityId(cityConditionEntity.getCityId())
                    .setCityName(cityConditionEntity.getCityName())
                    .setLocalEpoch(cityConditionEntity.getLocalEpoch())
                    .setLocalTzShort(cityConditionEntity.getLocalTzShort())
                    .setLocalTzLong(cityConditionEntity.getLocalTzLong())
                    .setLocalTzOffset(cityConditionEntity.getLocalTzOffset())
                    .setWeather(cityConditionEntity.getWeather())
                    .setIcon(cityConditionEntity.getIcon())
                    .setTemperatureString(cityConditionEntity.getTemperatureString())
                    .setTempF(cityConditionEntity.getTempF())
                    .setTempC(cityConditionEntity.getTempC())
                    .setCurrentCity(cityConditionEntity.isCurrentCity());
        }

        return cityCondition;
    }
}
