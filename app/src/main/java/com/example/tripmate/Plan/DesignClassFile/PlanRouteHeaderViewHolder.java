package com.example.tripmate.Plan.DesignClassFile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tripmate.R;


final public class PlanRouteHeaderViewHolder extends RecyclerView.ViewHolder {
    final TextView trip_wrap_yy;
    final TextView trip_wrap_mm;
    final TextView trip_wrap_nn;
    final ImageButton trip_btn;
    final View rootView;

    public PlanRouteHeaderViewHolder(View itemView) {
        super(itemView);
        rootView = itemView;
        trip_wrap_yy = (TextView)itemView.findViewById(R.id.trip_wrap_yy);
        trip_wrap_mm = (TextView)itemView.findViewById(R.id.trip_wrap_mm);
        trip_wrap_nn = (TextView)itemView.findViewById(R.id.trip_wrap_nn);
        trip_btn = itemView.findViewById(R.id.trip_route_add_Btn);
    }
}
