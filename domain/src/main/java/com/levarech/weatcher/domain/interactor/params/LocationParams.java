package com.levarech.weatcher.domain.interactor.params;

import android.location.Location;

/**
 * Created by efronny on 6/21/17.
 * Parameter that can be used to represent current location.
 */

public class LocationParams {
    private double latitude;
    private double longitude;

    private LocationParams(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static LocationParams from(Location location) {
        return new LocationParams(location.getLatitude(), location.getLongitude());
    }

    public static LocationParams from(double currentLatitude, double currentLongitude) {
        return new LocationParams(currentLatitude, currentLongitude);
    }
}
