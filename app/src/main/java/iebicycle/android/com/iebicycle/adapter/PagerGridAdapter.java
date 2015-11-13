package iebicycle.android.com.iebicycle.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import iebicycle.android.com.iebicycle.layout.NonScrollableGridView;

/**
 * Created by Guchuan on 2015/10/26.
 */
public abstract class PagerGridAdapter<T extends Object> extends PagerAdapter {

    private Context mContext;
    private List<NonScrollableGridView> mViewList = new ArrayList<>();
    private List<T> mData;
    private int mPageItemCount;

    public PagerGridAdapter(List<T> data, Context context, int column, int raw) {
        mData = data;
        mContext = context;
        mPageItemCount = column * raw;
        for(int i = 0; i < data.size() / (column * raw) + 1; i++) {
            mViewList.add(getGridView(i, column, raw));
        }
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

    private NonScrollableGridView getGridView(int pageNum, int width, int height) {
        NonScrollableGridView gridView =  (NonScrollableGridView) getGridView();
        gridView.setNumColumns(width);
        GridAdapter adapter = new GridAdapter(pageNum, width * height);
        int i = mData.size() - pageNum * width * height;
        gridView.setAdapter(adapter);
        return gridView;
    };

    private final class GridAdapter extends BaseAdapter{

        private int mPageNum;

        public GridAdapter(int pageNum, int pagerCount) {
            mPageNum = pageNum;
        }

        @Override
        public int getCount() {
            int i = mData.size() - mPageItemCount * mPageNum;
            return i < mPageItemCount ? i : mPageItemCount;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (position + mPageItemCount * mPageNum < mData.size()) {
                view = getItemView(position + mPageItemCount * mPageNum);
            }
            return view;
        }
    }

    public int getPageItemCount() {
        return mPageItemCount;
    }

    public List<NonScrollableGridView> getGridViews() {
        return mViewList;
    }

    public abstract View getItemView(int position);

    public abstract View getGridView();
}
