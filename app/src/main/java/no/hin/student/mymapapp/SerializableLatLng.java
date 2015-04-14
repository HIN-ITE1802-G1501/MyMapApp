package no.hin.student.mymapapp;

import java.io.Serializable;

/**
 * Created by Aleksander on 14.04.2015.
 */
public class SerializableLatLng implements Serializable
{
    public double latitude;
    public double longtitude;

    public SerializableLatLng(double latitude, double longtitude)
    {
        this.latitude = latitude;
        this.longtitude = longtitude;
    }
}
