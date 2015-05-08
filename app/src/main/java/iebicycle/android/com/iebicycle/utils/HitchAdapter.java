package iebicycle.android.com.iebicycle.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import iebicycle.android.com.iebicycle.R;

public class HitchAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mHitchList;
    private LayoutInflater mInflater;

    public HitchAdapter(Context context, List<String> hitchList) {
        this.mContext = context;
        this.mHitchList = hitchList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public int getCount() {
        return mHitchList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.hitch_item, null);
            holder = new ViewHolder();
            holder.mHitchName = (TextView) convertView.findViewById(R.id.hitch_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        String hitchName = mHitchList.get(position);
        if (hitchName != null && !hitchName.isEmpty()) {
            holder.mHitchName.setText(hitchName);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView mHitchName;
    }
}
