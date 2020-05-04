package com.software4bikers.motorcyclerun.models;

public class BikerLocation {

    private String lat;
    private String lng;

    public BikerLocation(String lat, String lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public String getLat(){
        return this.lat;
    }
    public String getLng(){
        return this.lng;
    }
}
