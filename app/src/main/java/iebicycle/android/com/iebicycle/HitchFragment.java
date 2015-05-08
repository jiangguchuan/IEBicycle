package iebicycle.android.com.iebicycle;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import iebicycle.android.com.iebicycle.utils.HitchAdapter;

public class HitchFragment extends Fragment {

    private List<String> mHitchList;
    private ListView mHitchListView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hitch_fragment, container, false);
        mHitchListView = (ListView)view.findViewById(R.id.hitch_list);
        initList();

        HitchAdapter adapter = new HitchAdapter(getActivity(), mHitchList);
        mHitchListView.setAdapter(adapter);
        return view;
    }

    private void initList()
    {
        mHitchList = new ArrayList<String>();
        mHitchList.add("故障1");
        mHitchList.add("故障2");
        mHitchList.add("故障3");
        mHitchList.add("故障4");
        mHitchList.add("故障5");
        mHitchList.add("故障6");
        mHitchList.add("故障7");
        mHitchList.add("故障8");
    }
}
