package com.xu.hookmeup.Model;

/**
 * Created by marcin on 20.11.16.
 */

public class Location {
    private String city;
    private String country;
    private String zip;

    private float latitude;
    private float longitude;

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setLatitude(float lat) {
        this.latitude = lat;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLongitude(float lon) {
        this.longitude = lon;
    }

    public float getLongitude() {
        return longitude;
    }
}
