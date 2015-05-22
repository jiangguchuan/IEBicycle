package iebicycle.android.com.iebicycle;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import iebicycle.android.com.iebicycle.service.WeatherService;
import iebicycle.android.com.iebicycle.utils.WeatherInfo;

public class MotormeterFragment extends Fragment {

    private static final String TAG = "MotormeterFragment";

    private static final String PREFERENCE_AE_BICYCLE = "ae_bicycle";
    private static final String KEY_WEATHER_ICON = "weather_icon";
    private static final String KEY_WEATHER_TEMP = "weather_temp";

    private Context mContext;
    private SharedPreferences mPreferences;

    private TextView mTimeView;

    private ImageView mWeatherIconView;
    private TextView mWeatherTempView;

    private ImageView mPhoneBatteryIconView;
    private TextView mPhoneBatteryView;

    private TextView mTravelledMileageView;
    private ImageView mTravelledMileageImgView;
    private TextView mTotalMileageView;
    private View mTotalMileageBarView;

    private WeatherInfo mWeatherInfo;

    private BroadcastReceiver weatherReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "update weather");
            mWeatherInfo = (WeatherInfo) intent
                    .getSerializableExtra(WeatherService.EXTRA_WEATHER_INFO);
            Bitmap icon = Bytes2Bimap(mWeatherInfo.getIconData());
            mWeatherIconView.setImageBitmap(icon);
            mWeatherTempView.setText((int)Math.floor(mWeatherInfo.getTemp()) + "℃");
            Log.d(TAG, "实时城市天气信息：\n" + mWeatherInfo.getTemp());
            if (icon != null) {
                saveImage(icon);
            }
        }
    };

    private BroadcastReceiver batteryReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            Log.d(TAG, "broadcast:" + level + ":" + scale);
            float batterPct = (level / (float) scale) * 100;
            mPhoneBatteryView.setText((int) batterPct + "%");
        }
    };

    private BroadcastReceiver timeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                Log.d(TAG, "update time");
                DateFormat df = new SimpleDateFormat("HH:mm");
                mTimeView.setText(df.format(new Date(System.currentTimeMillis())));
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = this.getActivity();
        mPreferences = mContext.getSharedPreferences(PREFERENCE_AE_BICYCLE,
                Activity.MODE_PRIVATE);
        View view = inflater.inflate(R.layout.motormeter_fragment, container, false);

        mTimeView = (TextView) view.findViewById(R.id.time);
        mWeatherIconView = (ImageView) view.findViewById(R.id.weather_image);
        mWeatherTempView = (TextView) view.findViewById(R.id.weather_temperature);

        mPhoneBatteryIconView = (ImageView) view.findViewById(R.id.battery_image);
        mPhoneBatteryView = (TextView) view.findViewById(R.id.battery);

        mTravelledMileageView = (TextView) view.findViewById(R.id.travelled_mileage);
        mTravelledMileageImgView = (ImageView) view.findViewById(R.id.travelled_image);
        mTotalMileageView = (TextView) view.findViewById(R.id.total_mileage);
        mTotalMileageBarView = (View) view.findViewById(R.id.mileage_bar);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter weatherFilter = new IntentFilter(WeatherService.WEATHER_ACTION);
        mContext.registerReceiver(weatherReceiver, weatherFilter);

        IntentFilter timeFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
        mContext.registerReceiver(timeReceiver, timeFilter);

        IntentFilter batteryFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryIntent = mContext.registerReceiver(batteryReceiver, batteryFilter);

        DateFormat df = new SimpleDateFormat("HH:mm");
        mTimeView.setText(df.format(new Date(System.currentTimeMillis())));

        if (mPreferences != null) {
            if (mPreferences.contains(KEY_WEATHER_ICON)) {
                mWeatherIconView.setImageBitmap(getIcon(mPreferences.getString(KEY_WEATHER_ICON, "")));
            }
            if (mPreferences.contains(KEY_WEATHER_TEMP)) {
                mWeatherTempView.setText((int)Math.floor(mPreferences.getFloat(KEY_WEATHER_TEMP, 0f)) + "℃");
            }
        }

        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        Log.d(TAG, "resume:" + level + ":" + scale);
        float batterPct = (level / (float) scale) * 100;
        mPhoneBatteryView.setText((int) batterPct + "%");

        moveLoction(30, 60);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(KEY_WEATHER_ICON, mWeatherInfo.getIconName());
        editor.putFloat(KEY_WEATHER_TEMP, mWeatherInfo.getTemp());
        editor.commit();

        mContext.unregisterReceiver(weatherReceiver);
        mContext.unregisterReceiver(batteryReceiver);
        mContext.unregisterReceiver(timeReceiver);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void moveLoction(float travelledMile, float totalMile)
    {
        mTravelledMileageView.setText(travelledMile + "km");
        mTotalMileageView.setText(totalMile + "km");
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mTravelledMileageImgView.getLayoutParams();
        Log.d(TAG, "width:" + (travelledMile / totalMile) * 1160);
        params.leftMargin = (int) (travelledMile / totalMile * 1160);
        mTravelledMileageImgView.setLayoutParams(params);
    }

    private Bitmap Bytes2Bimap(byte[] b) {
        if (b == null) {
            return null;
        }
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    private void saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "AeBicycle");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = mWeatherInfo.getIconName();
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap getIcon(String iconName)
    {
        File appDir = new File(Environment.getExternalStorageDirectory(), "AeBicycle");
        Log.d(TAG, Environment.getExternalStorageDirectory().toString());
        String path = Environment.getExternalStorageDirectory().toString() + "/AeBicycle/" + iconName;
        Log.d(TAG, path);
        File mFile = new File(path);
        if (mFile.exists()) {
            Bitmap bitmap=BitmapFactory.decodeFile(path);
            return bitmap;
        }
        return null;
    }
}
