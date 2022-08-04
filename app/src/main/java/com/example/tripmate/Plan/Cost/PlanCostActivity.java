package com.example.tripmate.Plan.Cost;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tripmate.Plan.SelectPlanList;
import com.example.tripmate.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class PlanCostActivity extends AppCompatActivity {
    public static String code, start, end;
    private String place;
    private TextView place_title, total_price;
    private ImageButton back, costAdd;
    private static RecyclerView recyclerView, recyclerView2;
    private PlanCostAdapter planCostAdapter;
    private static PlanCostActivity instance;
    private ArrayList<String> dateList;
    private SectionedRecyclerViewAdapter sectionAdapter;
    private boolean flag = false;
    private int price1, price2;
    ///private int total = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_cost);

        Intent intent = getIntent();
        code = intent.getStringExtra("plancode");
        place = intent.getStringExtra("place");
        start = intent.getStringExtra("start");
        end = intent.getStringExtra("end");

        recyclerView = (RecyclerView)findViewById(R.id.rc_cost1);
        init();

        recyclerView2 = (RecyclerView) findViewById(R.id.rc_cost2);
        getDate(start,end);
        init2();

        place_title = (TextView)findViewById(R.id.cost_title);
        place_title.setText(place);

        costAdd = (ImageButton)findViewById(R.id.trip_cost_add);
        costAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PlanCostActivity.this, CostAddActivity1.class);
                intent1.putExtra("plancode", code);
                startActivity(intent1);
            }
        });

        back = (ImageButton)findViewById(R.id.cost_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        total_price = (TextView)findViewById(R.id.total_cost);
        total();
    }

    public void init(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        SelectPlanList data = new SelectPlanList();
        planCostAdapter = new PlanCostAdapter(data.getCostlist1(getPlanCode()));
        recyclerView.setAdapter(planCostAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);

        int price1 = 0;
        for(int i = 0; i < data.getCostlist1(getPlanCode()).size(); i++){
            int price = data.getCostlist1(getPlanCode()).get(i).getCostPrice();
            price1 += price;
            System.out.println("djfi343ejsflj29382 " + price1);
        }
        price(price1);
    }

    public int price(int price){
        this.price1 = price;
        return price1;
    }

    public int price2(int price){
        this.price2 = price;
        return price2;
    }

    public int total(){
        int total = price1 + price2;
        total_price.setText(String.valueOf(total));
        return total;
    }

    //날짜 계산해서 가져오기 시작날짜-마지막날짜사이의 날짜들이 나옴
    public ArrayList<String> getDate(String start, String end) {
        final String DATE_PATTERN = "yy/MM/dd";
        String inputStartDate = start;
        String inputEndDate = end;
        dateList = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

        try {
            Date startDate = sdf.parse(inputStartDate);
            Date endDate = sdf.parse(inputEndDate);

            Date currentDate = startDate;

            while (currentDate.compareTo(endDate) <= 0) {
                dateList.add(sdf.format(currentDate));
                Calendar c = Calendar.getInstance();
                c.setTime(currentDate);
                c.add(Calendar.DAY_OF_MONTH, 1);
                currentDate = c.getTime();
            }

            for (String date : dateList) {
                Log.i("dateView",date);
            }
            return dateList;
        } catch (Exception e) {
        }
        return dateList;
    }

    public void init2(){
        CostSectionAdapter.removeAllItems();
        SelectPlanList data = new SelectPlanList();
        ArrayList<CostModel> list = new ArrayList<>();
        list = data.getCostlist2(getPlanCode());  //24, 25일 데이터

        int price1 = 0;
        sectionAdapter = new SectionedRecyclerViewAdapter();
        for(int i = 0; i < dateList.size(); i++) {
            sectionAdapter.addSection(new CostSectionAdapter(dateList.get(i))); // 24일헤더, 25일헤더
            for (int j = 0; j < list.size(); j++) {
                if (dateList.get(i).equals(list.get(j).getCostDate())) {
                    System.out.println("dfjkdjfdkfjkd " + dateList.get(i));
                    System.out.println("같ㅇ늠ㅁㅁㅁㅁㅁㅁㅁㅁㅁ");
                    CostSectionAdapter.addItem(list);
                    System.out.println("dfjkdjfdkfjqwe67879kd " + list.get(j).getCostPrice());
                    int price = list.get(j).getCostPrice();
                    price1 += price;
                    System.out.println("dfjkdjfdkfjkds2323232 " + price1);
                } else {
                    System.out.println("dfjkdjfd " + dateList.get(i));
                    System.out.println("다름ㅁㅁㅁㅁㅁㅁ");
                }
            }
        }
        price2(price1);
        sectionAdapter.notifyDataSetChanged();
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        recyclerView2.setAdapter(sectionAdapter);

    }

    public static PlanCostActivity getInstance() {
        if(instance == null){
            instance = new PlanCostActivity();
            return instance;
        }
        return instance;
    }

    public String getPlanCode() {
        return code;
    }
}
