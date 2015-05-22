package iebicycle.android.com.iebicycle.utils;

import java.io.Serializable;

/**
 * Created by zhao on 2015/5/14.
 */
public class LocationInfo implements Serializable{

    private String mCity;
    private float mLat;
    private float mLon;
    private String mCountry;

    public String getCity(){
        return this.mCity;
    }

    public void setCity(String city)
    {
        this.mCity = city;
    }

    public float getLat(){
        return this.mLat;
    }

    public void setLat(float lat)
    {
        this.mLat = lat;
    }

    public float getLon(){
        return this.mLon;
    }

    public void setLon(float lon)
    {
        this.mLon = lon;
    }

    public String getCountry(){
        return this.mCountry;
    }

    public void setCountry(String country)
    {
        this.mCountry = country;
    }
}
