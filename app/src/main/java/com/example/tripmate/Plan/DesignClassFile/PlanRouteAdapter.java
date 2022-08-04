package com.example.tripmate.Plan.DesignClassFile;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.tripmate.Plan.DataClassFile.PlanRouteDataModel;
import com.example.tripmate.Plan.HttpPlanRouteDelete;
import com.example.tripmate.Plan.PlanTripActivity;
import com.example.tripmate.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class PlanRouteAdapter extends Section {

    private static ArrayList<PlanRouteDataModel> mItems = new ArrayList<>();
    private final String date;
    final String start = PlanTripActivity.startDate;
    final String end = PlanTripActivity.endDate;
    private final ClickListener clickListener;

    public PlanRouteAdapter(String date,@NonNull final ClickListener clickListener) {
        // call constructor with layout resources for this Section header and items
        super(SectionParameters.builder()
                .itemResourceId(R.layout.activity_plan_trip_item)
                .headerResourceId(R.layout.plan_trip_header)
                .build());
        this.date = date;
        this.clickListener = clickListener;
        // this.mItems.add(data);
    }

    public PlanRouteAdapter(String date) {
        // call constructor with layout resources for this Section header and items
        super(SectionParameters.builder()
                .itemResourceId(R.layout.activity_plan_trip_item)
                .headerResourceId(R.layout.plan_trip_header)
                .build());
        this.date = date;
        this.clickListener = null;
        // this.mItems.add(data);
    }

    @Override
    public int getContentItemsTotal() {
        return mItems.size(); // number of items of this section
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(final View view) {
        // return a custom instance of ViewHolder for the items of this section

        return new PlanRouteViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Log.i("dsfsdfsdgsgsg",date);
        Log.i("dsfsdfsdgsgsg",mItems.get(position).getPlandate());
        PlanRouteViewHolder itemHolder = (PlanRouteViewHolder) holder;

        String mapX = Double.toString(mItems.get(position).getLocationx());
        String mapY = Double.toString(mItems.get(position).getLocationy());
        // bind your view here
        String temp = Integer.toString(mItems.get(position).getIndex());
        //String temp2 = Integer.toString(mItems.get(position).getTotalPrice());
        itemHolder.trip_item_numbering.setText(temp);
        itemHolder.trip_item_route.setText(mItems.get(position).getTitle());
        //itemHolder.trip_item_price.setText(temp2);
        itemHolder.trip_item_memo.setText(mItems.get(position).getMemo());
        Log.i("ttt3t33tt3","222222222222");


        //메모추가
//        itemHolder.trip_memo_add.setOnClickListener(new View.OnClickListener() {
//            final String memo = mItems.get(position).getMemo();
//            @Override
//            public void onClick(View v) {
//                final EditText et = new EditText(v.getContext());
//                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
//                builder.setTitle("메모").setView(et);
//                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int id) {
//                        String value = et.getText().toString();
//                    }
//                });
//                AlertDialog alert = builder.create();
//                alert.show();
//            }
//        });

        //삭제
        itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            final String plandate = mItems.get(position).getPlandate();
            final String title = mItems.get(position).getTitle();
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                builder1.setTitle("삭제").setMessage("삭제하시겠습니까?");
                builder1.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                builder1.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String result = null;
                        HttpPlanRouteDelete httpPlanRouteDelete = new HttpPlanRouteDelete();
                        HttpPlanRouteDelete.SendTask sendTask = httpPlanRouteDelete.new SendTask();
                        try{
                            result = sendTask.execute(plandate, title).get();
                            if("success".equals(result)){
                                final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                builder.setTitle("삭제").setMessage("삭제 되었습니다.");
                                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                        PlanTripActivity.getInstance().getDate(start, end);
                                        PlanTripActivity.getInstance().Init();
                                    }
                                });
                                builder.create().show();
                            }
                        } catch (ExecutionException | InterruptedException e) { e.printStackTrace(); }
                    }
                }); builder1.show();
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(final View view) {
        // return an empty instance of ViewHolder for the headers of this section
        return new PlanRouteHeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(final RecyclerView.ViewHolder holder) {
        final PlanRouteHeaderViewHolder headerHolder = (PlanRouteHeaderViewHolder) holder;

        StringTokenizer token1 = new StringTokenizer(date, "/");
        String[] temp = new String[3];
        int count = 0;
        while (token1.hasMoreTokens()) {
            temp[count++] = token1.nextToken();
        }

        for(int i = 0; i < temp.length; i++) {
            Log.i("32432423423",temp[i]);
        }

        headerHolder.trip_wrap_yy.setText(temp[0]);
        headerHolder.trip_wrap_mm.setText(temp[1]);
        headerHolder.trip_wrap_nn.setText(temp[2]);

        headerHolder.trip_btn.setOnClickListener(v ->
                clickListener.onHeaderRootViewClicked(date,this)
        );
    }

    public void addItem(PlanRouteDataModel item) {
        mItems.add(item);
        System.out.println("들어왔돠돠돠돠 ");
    }

    public static void addlist(ArrayList<PlanRouteDataModel> list) {
        mItems.addAll(list);
    }

    public interface ClickListener {
        void onHeaderRootViewClicked(@NonNull final String sectionTitle, @NonNull final PlanRouteAdapter section);
    }
}