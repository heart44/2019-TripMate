package com.example.tripmate.Plan.Cost;

import com.example.tripmate.R;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

public class CostSectionAdapter extends Section {
    private static final String TAG = CostSectionAdapter.class.getSimpleName();
    public String title;
    private static ArrayList<CostModel> costlist = new ArrayList<>();
    final String code = PlanCostActivity.code;
    final String start = PlanCostActivity.start;
    final String end = PlanCostActivity.end;
    //private final ClickListener clickListener;
    private int[] costImg = new int[]{
            R.drawable.ic_cost_category1, //숙박
            R.drawable.ic_cost_category2, //교통
            R.drawable.ic_cost_category3, //관광
            R.drawable.ic_cost_category4, //쇼핑
            R.drawable.ic_cost_category5, //식비
            R.drawable.ic_cost_category6}; //기타

    public CostSectionAdapter(String title ) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.trip_cost_item)
                .headerResourceId(R.layout.trip_cost_header)
                .build());
        this.title = title;
        //this.clickListener = clickListener;
        //this.costlist = costlist;
    }
    @Override
    public int getContentItemsTotal() {
       return costlist.size();
    }
    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.i("32432423423","000000000000000000");
        ItemViewHolder iHolder = (ItemViewHolder)holder;
        final int pos = position;
        Log.i("dsfsdfsdgsgsg",title);
        Log.i("dsfsdfsdgsgsg",costlist.get(pos).getCostDate());
        if(title.equals(costlist.get(pos).getCostDate())){
            if (costlist.get(pos).getCostCategory() == 0) {
                iHolder.costImg.setImageResource(costImg[0]);
            } else if (costlist.get(pos).getCostCategory() == 1) {
                iHolder.costImg.setImageResource(costImg[1]);
            } else if (costlist.get(pos).getCostCategory() == 2) {
                iHolder.costImg.setImageResource(costImg[2]);
            } else if (costlist.get(pos).getCostCategory() == 3) {
                iHolder.costImg.setImageResource(costImg[3]);
            } else if (costlist.get(pos).getCostCategory() == 4) {
                iHolder.costImg.setImageResource(costImg[4]);
            } else {
                iHolder.costImg.setImageResource(costImg[5]);
            }
            iHolder.costTitle.setText(costlist.get(pos).getCostTitle());
            System.out.println("돈돈ㄷ녿녿"+costlist.get(pos).getCostPrice());
            iHolder.costPrice.setText(Integer.toString(costlist.get(pos).getCostPrice()));
            if(costlist.get(pos).getCostType() == 0){
                iHolder.costType.setText("현금");
            }else if(costlist.get(pos).getCostType() == 1){
                iHolder.costType.setText("카드");
            }
        } else {
            iHolder.costTitle.setText(null);
            iHolder.costPrice.setText(null);
            iHolder.costType.setText(null);
        }

        Log.i("32432423423","11111111111");

        //수정
        iHolder.itemView.setOnClickListener(new View.OnClickListener() {
            final String costCode = costlist.get(pos).getCostCode();
            final String planCode = costlist.get(pos).getPlancode();
            final String costDate = costlist.get(pos).getCostDate();
            final String costTitle = costlist.get(pos).getCostTitle();
            final int costPrice = costlist.get(pos).getCostPrice();
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CostUpdateActivity2.class);
                intent.putExtra("costcode", costCode);
                intent.putExtra("plancode", planCode);
                intent.putExtra("date", costDate);
                intent.putExtra("start", start);
                intent.putExtra("end", end);
                intent.putExtra("title", costTitle);
                intent.putExtra("price", costPrice);
                v.getContext().startActivity(intent);
            }
        });
    }
    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder hHolder = (HeaderViewHolder)holder;
        hHolder.cost_date.setText(title);

        hHolder.cost_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CostAddActivity2.class);
                intent.putExtra("plancode", code);
                intent.putExtra("start", start);
                intent.putExtra("end", end);
                System.out.println("코드넘어왔뉘위위우 " +code);
                intent.putExtra("date", title);
                v.getContext().startActivity(intent);
            }
        });
    }

    public static void addItem(ArrayList<CostModel> list){
        costlist.addAll(list);
        System.out.println("들어왔돠돠돠돠 ");
    }

    public static void removeAllItems() {
        costlist.clear();
    }
}

