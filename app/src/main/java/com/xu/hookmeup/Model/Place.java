package com.xu.hookmeup.Model;

/**
 * Created by marcin on 20.11.16.
 */

public class Place {
    private String name;
    private String zip;

    private Location location;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getLocation(){
        return location;
    }


}
