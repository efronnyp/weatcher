package com.levarech.weatcher.data.network.response;

import com.google.gson.JsonObject;

/**
 * Created by EFR on 16/03/2017.
 * Model class to map response json object from wunderground API service.
 */

public class ResponseData {
    public String version;
    public String termsofService;
    public JsonObject features;
}
