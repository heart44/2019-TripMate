package com.example.tripmate.Plan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.tripmate.R;

import java.util.ArrayList;

public class TripListAdapter extends BaseAdapter {
    private TextView textTitle, textMemo;
    ArrayList<TripModel> tripmodel = new ArrayList<TripModel>();

    public TripListAdapter(){
    }

    @Override
    public int getCount() {
        return tripmodel.size();
    }

    @Override
    public Object getItem(int position) {
        return tripmodel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView ==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.plan_trip_item, parent, false);
        }

        textTitle = convertView.findViewById(R.id.textTitle);
        textMemo = convertView.findViewById(R.id.tv_tripadd);

        TripModel listViewItem = tripmodel.get(position);

        textTitle.setText(listViewItem.getTitle());
        textMemo.setText(listViewItem.getMemo());

        return convertView;
    }

}
