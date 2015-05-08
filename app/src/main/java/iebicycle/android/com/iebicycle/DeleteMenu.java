package iebicycle.android.com.iebicycle;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DeleteMenu extends LinearLayout implements OnClickListener {

    private Activity mActivity;
    private LinearLayout mContainer;
    private TextView mDelete;
    private TextView mCancel;
    private boolean mIsShowing;

    public DeleteMenu(Context context) {
        super(context);
        mActivity = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.main_menu_layout, this);
        mContainer = (LinearLayout) findViewById(R.id.menu_container);
    }

    @Override
    public void onClick(View view) {
    }

    public DeleteMenu init() {
        mActivity.addContentView(this, new LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return this;
    }

    public void show() {
        TranslateAnimation showAnimation = new TranslateAnimation(0, 0, 400, 0);
        showAnimation.setDuration(2000);
        showAnimation.setInterpolator(new AccelerateInterpolator());
        mContainer.setAnimation(showAnimation);
        mContainer.setVisibility(View.VISIBLE);
        mIsShowing = true;
    }

    public void hide() {
        TranslateAnimation hideAnimation = new TranslateAnimation(0, 0, 0, 400);
        hideAnimation.setDuration(2000);
        hideAnimation.setInterpolator(new AccelerateInterpolator());
        mContainer.setAnimation(hideAnimation);
        mContainer.setVisibility(View.GONE);
        mIsShowing = false;
    }

    public boolean isShowing() {
        return mIsShowing;
    }
}
