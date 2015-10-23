package iebicycle.android.com.iebicycle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import iebicycle.android.com.iebicycle.layout.BottomMenu;
import iebicycle.android.com.iebicycle.service.WeatherService;
import iebicycle.android.com.iebicycle.utils.WebAccessTools;

import iebicycle.android.com.iebicycle.layout.BottomMenu.OnButtonClicked;


public class HomeActivity extends Activity {

    private WebAccessTools mWebTools;
    private ImageView mTravelledMileageView;
    private BottomMenu mBottomMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mBottomMenu = new BottomMenu(this).create();
        mBottomMenu.setOnButtonClickListener(new OnButtonClicked() {
            @Override
            public void onMenuBtnClicked() {
                if (mBottomMenu.isShowing()) {
                    mBottomMenu.hide();
                } else {
                    mBottomMenu.show();
                }
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new MotormeterFragment()).commit();

        Intent intent = new Intent(this, WeatherService.class);
        this.startService(intent);
//        mWebTools = new WebAccessTools(this);
//
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                String webContent = mWebTools.getWebContent("http://api.openweathermap.org/data/2.5/weather?q=Chengdu,CN&APPID=fa149c9090de1fc1cc549629fa577524");
//                Log.d("xxx", "webContent:" + webContent);
//            }
//        }.start();
    }

    @Override
    public void onBackPressed() {
        if (mBottomMenu.isShowing()) {
            mBottomMenu.hide();
        } else {
            super.onBackPressed();
        }
    }
}
