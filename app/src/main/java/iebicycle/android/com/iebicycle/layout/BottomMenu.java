package iebicycle.android.com.iebicycle.layout;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import iebicycle.android.com.iebicycle.R;

public class BottomMenu extends LinearLayout implements OnClickListener, View.OnTouchListener {

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
    private int mLastX;
    private int mLastY;
    private int mScreenWidth;
    private int mScreenHeight;
    boolean isClick = false;
    private RelativeLayout mMenuLayout;
    private boolean mReLayout = false;

    public BottomMenu(Context context) {
        super(context);
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;
        mActivity = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.main_menu_layout, this);
        mMenuLayout = (RelativeLayout) findViewById(R.id.menu_layout);
        mContainer = findViewById(R.id.menu_item_container);
        addMenuBtn(context);
    }

    private void addMenuBtn(Context context) {
        mMenuBtn = new ImageView(context);
        mMenuBtn.setImageResource(R.drawable.menu_btn);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(120, 120);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        lp.leftMargin=30;
        lp.topMargin=30;
        mMenuBtn.setLayoutParams(lp);
        mMenuBtn.setId(R.id.menu_btn);
        mMenuLayout.addView(mMenuBtn);
        mMenuBtn.setOnClickListener(this);
        mMenuBtn.setOnTouchListener(this);
        mMenuBtn.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (mReLayout) {
                    mReLayout = false;
                    mMenuBtn.layout(oldLeft, oldTop, oldRight, oldBottom);
                }
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();
                return false;
            case MotionEvent.ACTION_MOVE:
                mReLayout = false;
                int dx = (int) event.getRawX() - mLastX;
                int dy = (int) event.getRawY() - mLastY;

                int left = v.getLeft() + dx;
                int top = v.getTop() + dy;
                int right = v.getRight() + dx;
                int bottom = v.getBottom() + dy;
                if (left < 0) {
                    left = 0;
                    right = left + v.getWidth();
                }
                if (right > mScreenWidth) {
                    right = mScreenWidth;
                    left = right - v.getWidth();
                }
                if (top < 0) {
                    top = 0;
                    bottom = top + v.getHeight();
                }
                if (isShowing()) {
                    if (bottom > mScreenHeight - 400) {
                        bottom = mScreenHeight - 400;
                        top = bottom - v.getHeight();
                    }
                } else {
                    if (bottom > mScreenHeight) {
                        bottom = mScreenHeight;
                        top = bottom - v.getHeight();
                    }
                }
                v.layout(left, top, right, bottom);
                mLastX = (int) event.getRawX();
                mLastY = (int) event.getRawY();
                isClick = true;
                return true;
            case MotionEvent.ACTION_UP:
                if (isClick) {
                    isClick = false;
                    return true;
                } else {
                    isClick = false;
                    return false;
                }
        }
        return isClick;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_btn:
                if (mListener != null) {
                    isClick = false;
                    mListener.onMenuBtnClicked();
                }
                break;
        }
    }

    public BottomMenu create() {
        mActivity.addContentView(this, new LayoutParams(ViewGroup.LayoutParams
                .MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return this;
    }

    public void show() {
        mReLayout = true;
        TranslateAnimation showAnimation = new TranslateAnimation(0, 0, 400, 0);
        showAnimation.setDuration(200);
        showAnimation.setInterpolator(new AccelerateInterpolator());
        mContainer.setAnimation(showAnimation);
        mContainer.setVisibility(View.VISIBLE);
        mIsShowing = true;
        if (mMenuBtn.getBottom() > mScreenHeight - 400) {
            showAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if (mMenuBtn.getBottom() > mScreenHeight - 400) {
                        TranslateAnimation ta = new TranslateAnimation(
                                0, 0, 0, (mScreenHeight - mMenuBtn.getBottom()) - 400);
                        ta.setDuration(100);
                        ta.setInterpolator(new AccelerateInterpolator());
                        mMenuBtn.setAnimation(ta);
                        ta.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {
                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mMenuBtn.layout(mMenuBtn.getLeft(), mScreenHeight - 400 - mMenuBtn.getHeight(),
                                        mMenuBtn.getRight(), mScreenHeight - 400);
                                mReLayout = false;
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                            }
                        });
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
        }
    }

    public void hide() {
        mReLayout = true;
        TranslateAnimation hideAnimation = new TranslateAnimation(0, 0, 0, 400);
        hideAnimation.setDuration(200);
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
