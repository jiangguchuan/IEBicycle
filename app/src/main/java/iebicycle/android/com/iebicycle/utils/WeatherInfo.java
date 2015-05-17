package iebicycle.android.com.iebicycle.utils;

import java.io.Serializable;

/**
 * Created by zhao on 2015/5/10.
 */
public class WeatherInfo implements Serializable{

    private int mWeatherId;
    private String mDescription;
    private String mCondition;
    private String mIconName;

    private int mHumidity;
    private int mPressure;
    private float mTemp;
    private byte[] mIconData;

//    private float mWindSpeed;
//    private float mWindDeg;
//
//    private int mCloudsPerc;

    public int getWeatherId()
    {
        return this.mWeatherId;
    }

    public void setWeatherId(int weatherId)
    {
        this.mWeatherId = weatherId;
    }

    public String getDescription()
    {
        return this.mDescription;
    }

    public void setDescription(String description)
    {
        this.mDescription = description;
    }

    public String getCondition()
    {
        return this.mCondition;
    }

    public void setCondition(String condition)
    {
        this.mCondition = condition;
    }

    public String getIconName()
    {
        return this.mIconName;
    }

    public void setIconName(String iconName)
    {
        this.mIconName = iconName;
    }

    public byte[] getIconData()
    {
        return this.mIconData;
    }

    public void setIconData(byte[] iconData)
    {
        this.mIconData = iconData;
    }

    public int getHumidity()
    {
        return this.mHumidity;
    }

    public void setHumidity(int humidity)
    {
        this.mHumidity = humidity;
    }

    public int getPressure()
    {
        return this.mPressure;
    }

    public void setPressure(int pressure)
    {
        this.mPressure = pressure;
    }
    public float getTemp()
    {
        return this.mTemp;
    }

    public void setTemp(float temp)
    {
        this.mTemp = temp;
    }
}
