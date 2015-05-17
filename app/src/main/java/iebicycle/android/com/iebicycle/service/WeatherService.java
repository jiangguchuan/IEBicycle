package iebicycle.android.com.iebicycle.service;
/**
 * 天气情况信息服务
 * */

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import iebicycle.android.com.iebicycle.utils.LocationInfo;
import iebicycle.android.com.iebicycle.utils.WeaterInfoParser;
import iebicycle.android.com.iebicycle.utils.WeatherInfo;
import iebicycle.android.com.iebicycle.utils.WebAccessTools;

public class WeatherService extends IntentService implements LocationListener {

    private static final String TAG = "WeatherService";

    public static final String WEATHER_ACTION = "com.aebicycle.weather.ACTION_UPDATE_DATA";

    private static final String WEATHER_GET_CITY = "http://api.openweathermap.org/data/2.5/find?";
    private static final String WEATHER_LAT = "lat=";
    private static final String WEATHER_LON = "lon=";
    private static final String WEATHER_CNT = "cnt=";
    private static final String WEATHER_GET_CONDITION = "http://api.openweathermap.org/data/2.5/weather?q=";
    private static final String WEATHER_IMG = "http://openweathermap.org/img/w/";
    private static final String WEATHER_APPID = "APPID=fa149c9090de1fc1cc549629fa577524";

    public static final String EXTRA_CITY_NAME = "city_name";
    public static final String EXTRA_WEATHER_INFO = "weather_info";

    private Location location;
    private LocationManager locationManager;

    public static String city;

    private WeatherInfo mWeatherInfo;
    private LocationInfo mLoctionInfo;
    private WebAccessTools mWebTools;
//    private ArrayList<Forecast> forecast4Day;
//    private ArrayList<ForecastTen> forecast10Day;

    public WeatherService() {
        super("WeatherService");
        Log.d(TAG, "new WeatherService");
        mWeatherInfo = new WeatherInfo();
        mWebTools = new WebAccessTools(this);
//        forecast4Day = new ArrayList<Forecast>();
//        forecast10Day = new ArrayList<ForecastTen>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        location = locationManager
                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Log.d(TAG, "onCreate:" + location);
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // 子线程中执行
        // 发送网络请求
        Log.d(TAG, "onHandleIntent:" + location);
        if (location != null) {
            // 纬度
            double lat = location.getLatitude();
            // 经度
            double lon = location.getLongitude();

            try {
                // 28.13725106,112.99305086/长沙
                getCity(lat, lon);
                Log.d(TAG, "纬度：" + lat + "  " + "经度：" + lon);
                Log.d(TAG, "城市：" + mLoctionInfo.getCity());
                getCondition(mLoctionInfo.getCity());
                getImage(mWeatherInfo.getIconName());

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        //发送广播
        Intent result = new Intent(WEATHER_ACTION);
        result.putExtra(EXTRA_WEATHER_INFO, mWeatherInfo);
        sendBroadcast(result);
    }

    private void getCondition(String cityInfo) throws ClientProtocolException,
            IOException, JSONException {
        StringBuffer buffer = new StringBuffer();
        buffer.append(cityInfo)
                .append("&")
                .append(WEATHER_APPID);
        String data = mWebTools.getWebContent(WEATHER_GET_CONDITION + buffer.toString());
        Log.d(TAG, data);

        WeaterInfoParser weaterInfoParser = new WeaterInfoParser();
        mLoctionInfo = weaterInfoParser.parserLocation(data);
        Log.d(TAG, mLoctionInfo.getCity() + ":" + mLoctionInfo.getCountry());
        mWeatherInfo = weaterInfoParser.parserWeather(data);
        Log.d(TAG, mWeatherInfo.getTemp() + ":" + mWeatherInfo.getIconName());
    }

    //长沙天气示例  http://api.wunderground.com/api/<span style="font-family:Arial, Helvetica, sans-serif;">您的key</span>/geolookup/lang:CN/q/28.13725106,112.99305086.json
    private void getCity(double lat, double lon)
            throws ClientProtocolException, IOException, JSONException {

        StringBuffer buffer = new StringBuffer();
        buffer.append(WEATHER_LAT)
                .append(lat)
                .append("&")
                .append(WEATHER_LON)
                .append(lon)
                .append("&")
                .append(WEATHER_APPID);
        String data = mWebTools.getWebContent(WEATHER_GET_CITY + buffer.toString());
        Log.d(TAG, data);

        WeaterInfoParser weaterInfoParser = new WeaterInfoParser();
        String cityData = weaterInfoParser.parserFirstCity(data);
        mLoctionInfo = weaterInfoParser.parserLocation(cityData);
        Log.d(TAG, mLoctionInfo.getCity() + ":" + mLoctionInfo.getCountry());
        mWeatherInfo = weaterInfoParser.parserWeather(cityData);
        Log.d(TAG, mWeatherInfo.getTemp() + ":" + mWeatherInfo.getIconName());

    }

    public void getImage(String code) {
        HttpURLConnection con = null;
        InputStream is = null;
        try {
            Log.d(TAG, WEATHER_IMG + code);
            con = (HttpURLConnection) (new URL(WEATHER_IMG + code)).openConnection();
            Log.d(TAG, WEATHER_IMG + code + "&" + WEATHER_APPID);
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();
            // Let's read the response
            is = con.getInputStream();
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while (is.read(buffer) != -1)
                baos.write(buffer);
            Log.d(TAG, baos.size() + "");
            mWeatherInfo.setIconData(baos.toByteArray());
        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Throwable t) {
            }
            try {
                con.disconnect();
            } catch (Throwable t) {
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        // TODO Auto-generated method stub
        this.location = location;
        Log.d(TAG, "location:" + this.location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub

    }
}
