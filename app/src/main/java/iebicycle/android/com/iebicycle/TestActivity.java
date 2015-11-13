package iebicycle.android.com.iebicycle;

import android.app.Activity;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import iebicycle.android.com.iebicycle.animation.Rotate3dAnimation;
import iebicycle.android.com.iebicycle.layout.DynamicMenu;


public class TestActivity extends Activity {
    private TextView tv;
    private Button btn;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        tv = (TextView) findViewById(R.id.tv);
        tv.setText(String.valueOf(count));
        btn = (Button) findViewById(R.id.next_btn);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                applyRotation(0, 90);
            }
        });

    }

    private void applyRotation(float start, float end) {
        // 计算中心点
        final float centerX = tv.getWidth() / 2.0f;
        final float centerY = tv.getHeight() / 2.0f;

        final Rotate3dAnimation rotation = new Rotate3dAnimation(start, end,
                centerX, centerY, 310, true);
        rotation.setDuration(500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        // 设置监听
        rotation.setAnimationListener(new DisplayNextView());

        tv.startAnimation(rotation);
    }

    private final class DisplayNextView implements Animation.AnimationListener {

        public void onAnimationStart(Animation animation) {
        }

        // 动画结束
        public void onAnimationEnd(Animation animation) {
            tv.post(new SwapViews());
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    private final class SwapViews implements Runnable {

        public void run() {
            final float centerX = tv.getWidth() / 2.0f;
            final float centerY = tv.getHeight() / 2.0f;
            Rotate3dAnimation rotation = null;

            tv.requestFocus();

            rotation = new Rotate3dAnimation(90, 180, centerX, centerY, 310.0f,
                    false);
            rotation.setDuration(500);
            rotation.setFillAfter(true);
            rotation.setInterpolator(new DecelerateInterpolator());
            // 开始动画
            tv.startAnimation(rotation);
            tv.setText(String.valueOf(count++));
        }
    }
}
