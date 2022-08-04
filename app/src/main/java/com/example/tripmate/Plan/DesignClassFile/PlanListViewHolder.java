package com.example.tripmate.Plan.DesignClassFile;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tripmate.R;

public class PlanListViewHolder extends RecyclerView.ViewHolder {
    protected TextView txtPlace, txtStart, txtEnd, txtTitle;
    protected ImageButton imageButton;
    protected View viewer;

    PlanListViewHolder(View itemView)
    {
        super(itemView);

        txtPlace = itemView.findViewById(R.id.tv_loc);
        txtStart = itemView.findViewById(R.id.tv_startday);
        txtEnd = itemView.findViewById(R.id.tv_endday);
        txtTitle = itemView.findViewById(R.id.tv_title);
        imageButton = itemView.findViewById(R.id.img_tripshow);

        viewer = itemView;
    }
}
