package com.example.tripmate.Main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tripmate.R;

public class mainRecyclerViewHolder extends RecyclerView.ViewHolder  {

    protected TextView main_place_start;
    protected TextView main_date_start;
    protected TextView main_date_end;

    public mainRecyclerViewHolder(View itemView) {
        super(itemView);
        main_place_start = itemView.findViewById(R.id.main_place_start);
        main_date_start = itemView.findViewById(R.id.main_date_start);
        main_date_end = itemView.findViewById(R.id.main_date_end);
    }
}
