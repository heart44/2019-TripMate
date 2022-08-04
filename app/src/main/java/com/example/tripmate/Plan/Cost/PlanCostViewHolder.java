package com.example.tripmate.Plan.Cost;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tripmate.R;

public class PlanCostViewHolder extends RecyclerView.ViewHolder {
    protected TextView costTitle, costPrice, costType;
    protected ImageView costImg;
    protected View viewer;

    public PlanCostViewHolder(@NonNull View itemView) {
        super(itemView);

        costImg = itemView.findViewById(R.id.cost_img);
        costTitle = itemView.findViewById(R.id.cost_title);
        costPrice = itemView.findViewById(R.id.cost_price);
        costType = itemView.findViewById(R.id.cost_type);

        viewer = itemView;
    }
}
