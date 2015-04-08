package iebicycle.android.com.iebicycle.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class CommonUtils {

    public static float getScreenDensity(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.density;
    }
}
