package com.levarech.weatcher.model.remote;

import com.levarech.weatcher.model.CurrentObservation;

/**
 * Created by EFR on 18/03/2017.
 * Model of weather conditions json returned from /conditions service.
 */

public class ConditionsResponse {
    public ResponseData response;
    public CurrentObservation current_observation;
}
