package iebicycle.android.com.iebicycle.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.GridView;

public class NonScrollableGridView extends GridView {
    public NonScrollableGridView(Context context) {
        super(context);
    }
    public NonScrollableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NonScrollableGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction() == MotionEvent.ACTION_MOVE){
            return true;        //禁止GridView进行滑动
        }
        return super.dispatchTouchEvent(ev);
    }
}
