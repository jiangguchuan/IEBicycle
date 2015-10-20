package iebicycle.android.com.iebicycle.layout;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import iebicycle.android.com.iebicycle.R;

public class BottomMenu extends LinearLayout implements OnClickListener {

    public interface OnButtonClicked {
        void onMenuBtnClicked();
    }

    private Activity mActivity;
    private View mContainer;
    private TextView mDelete;
    private TextView mCancel;
    private boolean mIsShowing;
    private ImageView mMenuBtn;
    private OnButtonClicked mListener;

    public BottomMenu(Context context) {
        super(context);
        mActivity = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.main_menu_layout, this);
        mContainer = findViewById(R.id.menu_item_container);
        mMenuBtn = (ImageView) findViewById(R.id.menu_btn);
        mMenuBtn.setOnClickListener(this);
        findViewById(R.id.item_1).setOnClickListener(this);
        findViewById(R.id.item_2).setOnClickListener(this);
        findViewById(R.id.item_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_btn:
                if (mListener != null) {
                    mListener.onMenuBtnClicked();
                }
                break;
            case R.id.item_1:
                Toast.makeText(mActivity, "仪表盘", Toast.LENGTH_LONG).show();
                break;
            case R.id.item_2:
                Toast.makeText(mActivity, "导航", Toast.LENGTH_LONG).show();
                break;
            case R.id.item_3:
                Toast.makeText(mActivity, "故障处理", Toast.LENGTH_LONG).show();
                break;
        }
    }

    public BottomMenu init() {
        mActivity.addContentView(this, new LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return this;
    }

    public void show() {
        TranslateAnimation showAnimation = new TranslateAnimation(0, 0, 400, 0);
        showAnimation.setDuration(400);
        showAnimation.setInterpolator(new AccelerateInterpolator());
        mContainer.setAnimation(showAnimation);
        mContainer.setVisibility(View.VISIBLE);
        mIsShowing = true;
    }

    public void hide() {
        TranslateAnimation hideAnimation = new TranslateAnimation(0, 0, 0, 400);
        hideAnimation.setDuration(400);
        hideAnimation.setInterpolator(new AccelerateInterpolator());
        mContainer.setAnimation(hideAnimation);
        mContainer.setVisibility(View.GONE);
        mIsShowing = false;
    }

    public boolean isShowing() {
        return mIsShowing;
    }

    public void setOnButtonClickListener(OnButtonClicked l) {
        mListener = l;
    }
}
