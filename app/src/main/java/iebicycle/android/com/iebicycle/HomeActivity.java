package iebicycle.android.com.iebicycle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import iebicycle.android.com.iebicycle.adapter.PagerGridAdapter;
import iebicycle.android.com.iebicycle.layout.BottomMenu;
import iebicycle.android.com.iebicycle.layout.NonScrollableGridView;
import iebicycle.android.com.iebicycle.layout.PagingGridView;
import iebicycle.android.com.iebicycle.service.WeatherService;
import iebicycle.android.com.iebicycle.utils.WebAccessTools;

import iebicycle.android.com.iebicycle.layout.BottomMenu.OnButtonClicked;


public class HomeActivity extends Activity {

    private WebAccessTools mWebTools;
    private ImageView mTravelledMileageView;
    private BottomMenu mBottomMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new MotormeterFragment()).commit();
        initBottomView();

        Intent intent = new Intent(this, WeatherService.class);
        this.startService(intent);
    }

    private void initBottomView() {
        mBottomMenu = new BottomMenu(this).create();
        PagingGridView pagingGridView =
                (PagingGridView) LayoutInflater.from(this).inflate(R.layout.home_menu, null);
        List<String> data = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            data.add(i + "");
        }
        MenuAdapter adapter = new MenuAdapter(data, this, 3, 2);
        pagingGridView.setAdapter(adapter);
        pagingGridView.setOnItemClickedListener(new PagingGridView.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                android.util.Log.e("jgc", "position -> " + position);
            }
        });
        mBottomMenu.setContentView(pagingGridView);
        mBottomMenu.setBackgroundColor(0x50505050);
        mBottomMenu.setOnButtonClickListener(new OnButtonClicked() {
            @Override
            public void onMenuBtnClicked() {
                if (mBottomMenu.isShowing()) {
                    mBottomMenu.hide();
                } else {
                    mBottomMenu.show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mBottomMenu.isShowing()) {
            mBottomMenu.hide();
        } else {
            super.onBackPressed();
        }
    }

    private class MenuAdapter extends PagerGridAdapter<String> {

        private List<String> mData;

        public MenuAdapter(List<String> data, Context context, int width, int height) {
            super(data, context, width, height);
            mData = data;
        }

        @Override
        public View getItemView(int position) {
            TextView view = new TextView(HomeActivity.this);
            view.setText(mData.get(position));
            view.setGravity(Gravity.CENTER);
            return view;
        }

        @Override
        public View getGridView() {
            NonScrollableGridView view = (NonScrollableGridView)
                    LayoutInflater.from(HomeActivity.this).inflate(R.layout.non_scroll_grid_view, null);
            return view;
        }
    }

}
