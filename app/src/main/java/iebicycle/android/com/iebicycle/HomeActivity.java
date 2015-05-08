package iebicycle.android.com.iebicycle;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        DeleteMenu deleteMenu = new DeleteMenu(this);
        deleteMenu.init();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new NavigationFragment()).commit();
    }

}
