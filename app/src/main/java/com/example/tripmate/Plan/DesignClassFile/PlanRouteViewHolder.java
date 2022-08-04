package com.example.tripmate.Plan.DesignClassFile;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tripmate.R;

public class PlanRouteViewHolder extends RecyclerView.ViewHolder {

    TextView trip_item_numbering;
    TextView trip_item_route;
    TextView trip_item_price;
    TextView trip_item_memo;
    Button trip_memo_add;

    public PlanRouteViewHolder(View itemView) {
        super(itemView);

        trip_item_numbering = itemView.findViewById(R.id.trip_item_numbering);
        trip_item_route = itemView.findViewById(R.id.trip_item_route);
        //trip_item_price = itemView.findViewById(R.id.trip_item_price);
        trip_item_memo = itemView.findViewById(R.id.trip_item_memo);
        trip_memo_add = itemView.findViewById(R.id.memo_add);

    }
}
