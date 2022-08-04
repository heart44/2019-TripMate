package com.example.tripmate.Main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripmate.Plan.DataClassFile.PlanListModel;
import com.example.tripmate.R;

import java.util.ArrayList;

public class MainRecyclerAdapter extends RecyclerView.Adapter<mainRecyclerViewHolder>{

    private ArrayList<PlanListModel> planList = new ArrayList<>();

    public MainRecyclerAdapter(ArrayList<PlanListModel> planList) {
        this.planList.addAll(planList);
    }

    @NonNull
    @Override
    public mainRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.fragment_recylcer_item, parent, false);
        mainRecyclerViewHolder viewHolder = new mainRecyclerViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull mainRecyclerViewHolder RecyclerViewHolder, int position) {
        RecyclerViewHolder.main_place_start.setText(planList.get(position).getPlanPlace());
        RecyclerViewHolder.main_date_start.setText(planList.get(position).getPlanStart());
        RecyclerViewHolder.main_date_end.setText(planList.get(position).getPlanEnd());
    }

    @Override
    public int getItemCount() {
        return planList.size();
    }

}
