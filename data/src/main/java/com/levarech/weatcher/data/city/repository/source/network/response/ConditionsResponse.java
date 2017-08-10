package com.levarech.weatcher.data.city.repository.source.network.response;

import com.levarech.weatcher.data.network.response.ResponseData;

/**
 * Created by EFR on 18/03/2017.
 * Model of weather conditions json returned from /conditions service.
 */

public class ConditionsResponse {
    public ResponseData response;
    public CurrentObservation current_observation;
}
