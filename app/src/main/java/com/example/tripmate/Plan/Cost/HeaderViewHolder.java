package com.example.tripmate.Plan.Cost;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tripmate.R;

public class HeaderViewHolder extends RecyclerView.ViewHolder{
    public TextView cost_wrap_yy, cost_wrap_mm, cost_wrap_nn, cost_date;
    public ImageButton cost_add;
    public HeaderViewHolder(View itemView) {
        super(itemView);
        cost_date = (TextView)itemView.findViewById(R.id.cost_date);
//        cost_wrap_yy = (TextView)itemView.findViewById(R.id.cost_wrap_yy);
//        cost_wrap_mm = (TextView)itemView.findViewById(R.id.cost_wrap_mm);
//        cost_wrap_nn = (TextView)itemView.findViewById(R.id.cost_wrap_nn);
        cost_add = (ImageButton)itemView.findViewById(R.id.cost_wrap_add);
    }
}
