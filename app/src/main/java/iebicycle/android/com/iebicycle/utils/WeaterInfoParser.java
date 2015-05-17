package iebicycle.android.com.iebicycle.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author i-zqluo
 *         解析从网上获取的天气信息的工具类
 */
public class WeaterInfoParser {

    private static final float TEMP_K_TO_C = 275.15f;
    /**
     * parseWeather
     */

    public LocationInfo parserLocation(String data) {
        try {
            JSONObject jObj = new JSONObject(data);
            LocationInfo location = new LocationInfo();

            JSONObject coordObj = getObject("coord", jObj);
            location.setLat(getFloat("lat", coordObj));
            location.setLon(getFloat("lon", coordObj));

            JSONObject sysObj = getObject("sys", jObj);
            location.setCountry(getString("country", sysObj));
            location.setCity(getString("name", jObj));
            return location;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public WeatherInfo parserWeather(String data) {
        // We get weather info (This is an array)
        try {
            JSONObject jObj = new JSONObject(data);
            JSONArray jArr = jObj.getJSONArray("weather");
            WeatherInfo weatherInfo = new WeatherInfo();

            // We use only the first value
            JSONObject JSONWeather = jArr.getJSONObject(0);
            weatherInfo.setWeatherId(getInt("id", JSONWeather));
            weatherInfo.setDescription(getString("description", JSONWeather));
            weatherInfo.setCondition(getString("main", JSONWeather));
            weatherInfo.setIconName(getString("icon", JSONWeather) + ".png");

            JSONObject mainObj = getObject("main", jObj);
            weatherInfo.setHumidity(getInt("humidity", mainObj));
            weatherInfo.setPressure(getInt("pressure", mainObj));
            weatherInfo.setTemp(getFloat("temp", mainObj) - TEMP_K_TO_C);

            /* TBD
            // Wind
            JSONObject wObj = getObject("wind", jObj);
            weatherInfo.setWindSpeed(getFloat("speed", wObj));
            weatherInfo.setWindDeg(getFloat("deg", wObj));

            // Clouds
            JSONObject cObj = getObject("clouds", jObj);
            weatherInfo.setcloudsPerc(getInt("all", cObj));
            */
            return weatherInfo;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String parserFirstCity(String citysData) {
        try {
            JSONObject jObj = new JSONObject(citysData);
            JSONArray jArr = jObj.getJSONArray("list");
            return jArr.getJSONObject(0).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JSONObject getObject(String tagName, JSONObject jObj) throws JSONException {
        JSONObject subObj = jObj.getJSONObject(tagName);
        return subObj;
    }

    private static String getString(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getString(tagName);
    }

    private static float getFloat(String tagName, JSONObject jObj) throws JSONException {
        return (float) jObj.getDouble(tagName);
    }

    private static int getInt(String tagName, JSONObject jObj) throws JSONException {
        return jObj.getInt(tagName);
    }
}
