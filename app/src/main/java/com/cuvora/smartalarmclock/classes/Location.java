package com.cuvora.smartalarmclock.classes;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created by Pranjal on 03-01-2017.
 * The Location class for representing a location.
 */

public class Location implements Externalizable {
    private String name;

    // Latitude and Longitude values for the location.
    private double latitude;
    private double longitude;

    // Default constructor for Externalization.
    public Location () {
        name = null;
        latitude = longitude = 0.0;
    }

    public Location (String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    // Getters and Setters

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(name);
        out.writeDouble(latitude);
        out.writeDouble(longitude);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }
}
