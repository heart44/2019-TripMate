package com.example.tripmate.Plan.Cost;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tripmate.R;

import java.util.ArrayList;

public class PlanCostAdapter extends RecyclerView.Adapter<PlanCostViewHolder> {
    private ArrayList<CostModel> costlist;
    private int[] costImg = new int[]{
            R.drawable.ic_cost_category1, //숙박
            R.drawable.ic_cost_category2, //교통
            R.drawable.ic_cost_category3, //관광
            R.drawable.ic_cost_category4, //쇼핑
            R.drawable.ic_cost_category5, //식비
            R.drawable.ic_cost_category6}; //기타

    public PlanCostAdapter(){}

    public PlanCostAdapter(ArrayList<CostModel> datalist) {
        this.costlist = datalist;
    }

    @NonNull
    @Override
    public PlanCostViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.trip_cost_item, viewGroup, false);
        PlanCostViewHolder viewHolder = new PlanCostViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlanCostViewHolder viewHolder, int position) {
        final int pos = position;
        if (costlist.get(position).getCostCategory() == 0) {
            viewHolder.costImg.setImageResource(costImg[0]);
        } else if (costlist.get(position).getCostCategory() == 1) {
            viewHolder.costImg.setImageResource(costImg[1]);
        } else if (costlist.get(position).getCostCategory() == 2) {
            viewHolder.costImg.setImageResource(costImg[2]);
        } else if (costlist.get(position).getCostCategory() == 3) {
            viewHolder.costImg.setImageResource(costImg[3]);
        } else if (costlist.get(position).getCostCategory() == 4) {
            viewHolder.costImg.setImageResource(costImg[4]);
        } else {
            viewHolder.costImg.setImageResource(costImg[5]);
        }
        viewHolder.costTitle.setText(costlist.get(pos).getCostTitle());
        System.out.println("돈돈ㄷ녿녿"+costlist.get(pos).getCostPrice());
        viewHolder.costPrice.setText(Integer.toString(costlist.get(pos).getCostPrice()));
        if(costlist.get(position).getCostType() == 0){
            viewHolder.costType.setText("현금");
        }else if(costlist.get(position).getCostType() == 1){
            viewHolder.costType.setText("카드");
        }
        final String date = costlist.get(pos).getCostDate();
        //if(date.equals("0000-00-00 00:00:00"))


        //수정
        viewHolder.viewer.setOnClickListener(new View.OnClickListener() {
            final String costCode = costlist.get(pos).getCostCode();
            final String planCode = costlist.get(pos).getPlancode();
            final String costTitle = costlist.get(pos).getCostTitle();
            final int costPrice = costlist.get(pos).getCostPrice();
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CostUpdateActivity.class);
                intent.putExtra("costcode", costCode);
                intent.putExtra("plancode", planCode);
                intent.putExtra("title", costTitle);
                intent.putExtra("price", costPrice);
                v.getContext().startActivity(intent);
            }
        });
    }

    public void removeAllItems(){
        costlist.clear();
    }

    @Override
    public int getItemCount() {
        return costlist.size();
    }
}
