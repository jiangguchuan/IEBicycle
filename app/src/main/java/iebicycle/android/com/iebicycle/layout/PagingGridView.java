package iebicycle.android.com.iebicycle.layout;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import iebicycle.android.com.iebicycle.R;

/**
 * Created by Guchuan on 2015/5/22.
 */
public class PagingGridView extends LinearLayout {

    private Context mContext;
    private ViewPager mViewPager;
    private SimpleViewPagerIndicator mIndicator = null;

    public PagingGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.paging_gridview, this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mIndicator = (SimpleViewPagerIndicator) findViewById(R.id.page_indicator);

        List<NonScrollableGridView> gridViewList = new ArrayList<NonScrollableGridView>();
        for(int i = 0; i < 10; i++) {
            NonScrollableGridView view = new NonScrollableGridView(mContext);
            gridViewList.add(view);
        }
        TypePagerAdapter adapter = new TypePagerAdapter(gridViewList);
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
        mIndicator.notifyDataSetChanged();
    }


    private final class TypePagerAdapter extends PagerAdapter {

        private List<NonScrollableGridView> mViewList = null;

        public TypePagerAdapter(List<NonScrollableGridView> viewList) {
            mViewList = viewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(mViewList.get(position), 0);
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
