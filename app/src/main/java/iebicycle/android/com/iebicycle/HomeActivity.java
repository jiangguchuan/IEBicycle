package iebicycle.android.com.iebicycle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import iebicycle.android.com.iebicycle.DeleteMenu.OnButtonClicked;


public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        final DeleteMenu deleteMenu = new DeleteMenu(this);
        deleteMenu.init();
        deleteMenu.setOnButtonClickListener(new OnButtonClicked() {
            @Override
            public void onMenuBtnClicked() {
                if (deleteMenu.isShowing()) {
                    deleteMenu.hide();
                } else {
                    deleteMenu.show();
                }
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new MotormeterFragment()).commit();
    }

}
