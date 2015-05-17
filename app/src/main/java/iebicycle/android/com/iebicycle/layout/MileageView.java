package iebicycle.android.com.iebicycle.layout;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by zhao on 2015/5/11.
 */
public class MileageView extends ImageView {

    public MileageView(Context context)
    {
        super(context);
    }

    public MileageView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MileageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLocation(int x, int y) {
        Log.d("xxxx", x + ":" + y + ":" + this.getWidth() + ":" + this.getHeight());
        this.setFrame(y, x, y + 40, x + 40);
    }
}
