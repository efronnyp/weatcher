package com.levarech.weatcher.model;

import io.realm.RealmObject;

/**
 * Created by EFR on 16/05/2017.
 * Unit that represents temperature in degree celsius and degree fahrenheit
 */

class Unit extends RealmObject {
    public String english;
    public String metric;
}
