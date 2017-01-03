package com.cuvora.smartalarmclock.classes;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Created by Pranjal on 03-01-2017.
 * The Alarm class that stores the various fields of an alarm.
 */

public class Alarm implements Externalizable {
    private long timeToReach;
    private long timeToGetReady;

    //Location objects that store the latitude and longitude values along with a location's name.
    private Location startLocation;
    private Location endLocation;

    //The encoded integer depicting the alarm status for different weekdays.
    private int repeatCode;

    // The alarm switch.
    private boolean on;

    // Default constructor for Externalization
    public Alarm () {
        timeToReach = timeToGetReady = 0L;
        repeatCode = 0;
        startLocation = endLocation = null;
        on = false;
    }

    public Alarm (long timeToReach, long timeToGetReady, Location startLocation,
                  Location endLocation, int repeatCode) {
        this.timeToReach = timeToReach;
        this.timeToGetReady = timeToGetReady;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.repeatCode = repeatCode;
        this.on = true;
    }

    // Getter and Setter methods

    public boolean isOn() {
        return on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getRepeatCode() {
        return repeatCode;
    }

    public void setRepeatCode(int repeatCode) {
        this.repeatCode = repeatCode;
    }

    public long getTimeToGetReady() {
        return timeToGetReady;
    }

    public void setTimeToGetReady(long timeToGetReady) {
        this.timeToGetReady = timeToGetReady;
    }

    public Location getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(Location endLocation) {
        this.endLocation = endLocation;
    }

    public long getTimeToReach() {
        return timeToReach;
    }

    public void setTimeToReach(long timeToReach) {
        this.timeToReach = timeToReach;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(Location startLocation) {
        this.startLocation = startLocation;
    }

    //Generate the encoded integer that represents the alarm activation on different weekdays.
    //index 0 is for Monday, 1 for Tuesday and so on.
    public static int generateRepeatCode (boolean[] weekdays) {
        int repeatCode = 0;
        for (int i = 0; i < 7; i++) {
            if (weekdays[i]) {
                repeatCode++;
            }
            repeatCode = repeatCode << 1;
        }
        return repeatCode;
    }

    //Decode the code into individual booleans for different weekdays.
    public static boolean[] breakRepeatCode (int repeatCode) {
        boolean[] weekdays = new boolean[7];
        for (int i = 6; i >= 0; i--) {
            repeatCode = repeatCode >> 1;
            if ((repeatCode % 2) == 1) {
                weekdays[i] = true;
            } else {
                weekdays[i] = false;
            }
        }
        return weekdays;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(timeToReach);
        out.writeLong(timeToGetReady);
        out.writeInt(repeatCode);
        out.writeObject(startLocation);
        out.writeObject(endLocation);
        out.writeBoolean(on);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        timeToReach = in.readLong();
        timeToGetReady = in.readLong();
        repeatCode = in.readInt();
        startLocation = (Location) in.readObject();
        endLocation = (Location) in.readObject();
        on = in.readBoolean();
    }
}
