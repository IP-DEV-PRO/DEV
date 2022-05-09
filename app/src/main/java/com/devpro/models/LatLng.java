package com.devpro.models;

public class LatLng {
    private final double latitude;
    private final double longitude;

    public LatLng() {
        latitude = 0.0d;
        longitude = 0.0d;
    }

    public LatLng(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
