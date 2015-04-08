package iebicycle.android.com.iebicycle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout.LayoutParams;

import iebicycle.android.com.iebicycle.layout.LoginView;
import iebicycle.android.com.iebicycle.utils.CommonUtils;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = View.inflate(this, R.layout.splash_activity, null);
        setContentView(view);

        Animation animation = new AlphaAnimation(0.1f, 1.0f);
        animation.setDuration(2000);
        animation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                View loginView = new LoginView(SplashActivity.this);
                addContentView(loginView, new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                Animation translateAnimation = new TranslateAnimation(0, 0,
                        -300 * CommonUtils.getScreenDensity(SplashActivity.this), 0);
                translateAnimation.setDuration(1000);
                loginView.setAnimation(translateAnimation);
            }
        });
        view.setAnimation(animation);
    }
}
