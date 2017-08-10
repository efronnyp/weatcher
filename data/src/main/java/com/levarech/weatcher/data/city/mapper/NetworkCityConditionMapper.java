package com.levarech.weatcher.data.city.mapper;

import com.levarech.weatcher.data.city.CityConditionEntity;
import com.levarech.weatcher.data.city.repository.source.network.response.ConditionsResponse;
import com.levarech.weatcher.data.city.repository.source.network.response.CurrentObservation;
import com.levarech.weatcher.data.city.repository.source.network.response.DisplayLocation;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by efronny on 7/5/17.
 * Mapper to transform weather conditions response from wunderground API {@link ConditionsResponse}
 * to {@link CityConditionEntity}
 */
@Singleton
public class NetworkCityConditionMapper {

    @Inject
    public NetworkCityConditionMapper() {
    }

    public CityConditionEntity transform(ConditionsResponse response) {
        CityConditionEntity cityConditionEntity = null;

        if (response != null) {
            cityConditionEntity = new CityConditionEntity();
            CurrentObservation observation = response.current_observation;
            DisplayLocation displayLocation = observation.getDisplayLocation();

            cityConditionEntity.setCityName(displayLocation.getCity());

            if (displayLocation.getLatitude() != null) {
                cityConditionEntity.setLatitude(Double.valueOf(displayLocation.getLatitude()));
            }

            if (displayLocation.getLongitude() != null) {
                cityConditionEntity.setLongitude(Double.valueOf(displayLocation.getLongitude()));
            }

            cityConditionEntity.setLocalEpoch(observation.getLocalEpoch());
            cityConditionEntity.setLocalTzShort(observation.getLocalTzShort());
            cityConditionEntity.setLocalTzLong(observation.getLocalTzLong());
            cityConditionEntity.setLocalTzOffset(observation.getLocalTzOffset());
            cityConditionEntity.setWeather(observation.getWeather());
            cityConditionEntity.setIcon(observation.getIcon());
            cityConditionEntity.setTemperatureString(observation.getTemperatureString());
            cityConditionEntity.setTempF(observation.getTempF());
            cityConditionEntity.setTempC(observation.getTempC());
        }

        return cityConditionEntity;
    }
}
