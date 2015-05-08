package iebicycle.android.com.iebicycle;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MotormeterFragment extends Fragment {

    TextView mTimeView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.motormeter_fragment, container, false);
        mTimeView = (TextView) view.findViewById(R.id.time);
        DateFormat df = new SimpleDateFormat("HH:mm");
        mTimeView.setText(df.format(new Date(System.currentTimeMillis())));
        return view;
    }
}
