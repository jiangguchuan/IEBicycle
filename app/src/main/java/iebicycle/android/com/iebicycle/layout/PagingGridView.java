package iebicycle.android.com.iebicycle.layout;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import java.util.List;

import iebicycle.android.com.iebicycle.R;
import iebicycle.android.com.iebicycle.adapter.PagerGridAdapter;

/**
 * Created by Guchuan on 2015/5/22.
 */
public class PagingGridView extends LinearLayout {

    public final static int INVALID_SELECTOR_INDEX = -1;

    private Context mContext;
    private ViewPager mViewPager;
    private SimpleViewPagerIndicator mIndicator = null;
    private OnItemClickedListener mListener;
    private PagerGridAdapter mAdapter;


    public interface OnItemClickedListener {
        void onItemClicked(int position);
    }

    public PagingGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.paging_gridview, this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mIndicator = (SimpleViewPagerIndicator) findViewById(R.id.page_indicator);
    }

    public void setAdapter(final PagerGridAdapter adapter) {
        mAdapter = adapter;
        mViewPager.setAdapter(adapter);
        mIndicator.setViewPager(mViewPager);
        mIndicator.notifyDataSetChanged();
    }

    public void setOnItemClickedListener(OnItemClickedListener l) {
        mListener = l;
        if (mListener != null) {
            List<NonScrollableGridView> viewList = mAdapter.getGridViews();
            for (NonScrollableGridView gridView : viewList) {
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (mListener != null) {
                            mListener.onItemClicked(mAdapter.getPageItemCount() * mViewPager.getCurrentItem() + position);
                        }
                    }
                });
            }
        }
    }

    public void setItemChecked(int position) {
        List<NonScrollableGridView> viewList = mAdapter.getGridViews();
        int pageItemCount = mAdapter.getPageItemCount();
        int pageNum = position / pageItemCount;
        int pageIndex = position % pageItemCount;
        mViewPager.setCurrentItem(pageNum);
        for(int i = 0; i < viewList.size() ; i++) {
            GridView gridView = viewList.get(i);
            if (gridView.getChoiceMode() == GridView.CHOICE_MODE_SINGLE) {
                gridView.setItemChecked(pageNum == i ? pageIndex : INVALID_SELECTOR_INDEX, true);
            }
        }
    }

    public int getCheckedItemPosition() {
        List<NonScrollableGridView> viewList = mAdapter.getGridViews();
        for(int i = 0; i < viewList.size(); i++) {
            GridView gridView = viewList.get(i);
            if (gridView.getChoiceMode() == GridView.CHOICE_MODE_SINGLE) {
                int checkedPosition = gridView.getCheckedItemPosition();
                if (checkedPosition != INVALID_SELECTOR_INDEX) {
                    return i * mAdapter.getPageItemCount() + checkedPosition;
                }
            }
        }
        return INVALID_SELECTOR_INDEX;
    }

}
