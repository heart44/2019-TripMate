package com.example.tripmate;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.tripmate.Plan.DataClassFile.PlanRouteDataModel;
import com.example.tripmate.Plan.DesignClassFile.PlanRouteAdapter;
import com.example.tripmate.Plan.HttpPlanListAdd;
import com.example.tripmate.Plan.HttpPlanRouteAdd;
import com.example.tripmate.Plan.HttpPlanRouteList;
import com.example.tripmate.Plan.PlanTripActivity;
import com.example.tripmate.Plan.SelectPlanList;
import com.example.tripmate.Share.HttpShareInfoGetList;
import com.example.tripmate.Share.HttpShareRouteInfoList;
import com.example.tripmate.Share.ShareRecyclerAdapter;
import com.example.tripmate.Share.TripRouteDataInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class fragmentActivity3  extends Fragment {

    View shareFrament;
    private Button getBtn;
    private ShareRecyclerAdapter adapter;
    private ArrayList<TripRouteDataInfo> dataList = new ArrayList<TripRouteDataInfo>();
    private ArrayList<PlanRouteDataModel> RouteList = new ArrayList<PlanRouteDataModel>();
    private ArrayList<String> dateList = new ArrayList<String>();
    protected String userid;

    private SectionedRecyclerViewAdapter sectionedAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        shareFrament = inflater.inflate(R.layout.fragment_main3, container, false);

        init();

        return shareFrament;
    }

    private void init() {
        RecyclerView recyclerView = shareFrament.findViewById(R.id.Share_recycler);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());

        adapter = new ShareRecyclerAdapter(getData(), new ShareRecyclerAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {

                RouteList.addAll(jsonParserForGetRoutelist(dataList.get(position).getTripCode()));

                ArrayList<String> temp = new ArrayList<>();

                for(int i = 0; i < dataList.size(); i++) {
                    temp.add(RouteList.get(i).getPlandate());
                }

                String start = Collections.min(temp);
                String end = Collections.max(temp);

                String sendplace = dataList.get(0).getTripLocation();
                String sendtitle = dataList.get(0).getTripTitle();
                String sendstart = start;
                String sendend = end;
                String nickname = SaveSharedPreference.getNickName(getContext());
                HttpPlanListAdd httpPlanListAdd = new HttpPlanListAdd();
                HttpPlanListAdd.SendTask sendTask = httpPlanListAdd.new SendTask();
                String result = null;
                try {
                    result = sendTask.execute(nickname, sendplace, sendtitle, sendstart, sendend).get();
                    if("success".equals(result)){

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                for(int i = 0; i < RouteList.size(); i++) {

                    String userid1 = RouteList.get(i).getUserid();
                    String plancode1 = RouteList.get(i).getPlancode();
                    String date1 = RouteList.get(i).getPlandate();
                    String title1 = RouteList.get(i).getTitle();
                    String locationx1 = Double.toString(RouteList.get(i).getLocationx());
                    String locationy1 = Double.toString(RouteList.get(i).getLocationy());
                    String memo1 = RouteList.get(i).getMemo();
                    String index1 = Integer.toString(RouteList.get(i).getIndex());

                    HttpPlanRouteAdd httpRouteListAdd = new HttpPlanRouteAdd();
                    HttpPlanRouteAdd.SendTask sendTasks = httpRouteListAdd.new SendTask();
                    Log.i("23423423423","222222222222");

                    String results = null;

                    try {
                        results = sendTasks.execute(userid1,plancode1,date1,title1,locationx1,locationy1,memo1,index1,"0").get();
                        Log.i("23423423423","333333333333");
                        if("success".equals(results)){
                            Log.i("23423423423","4444444444");
                            //onBackPressed();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                } //여기까지 리스트 추가
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private ArrayList<TripRouteDataInfo> getData() {
        dataList = new ArrayList<>();
        dataList.addAll(jsonParserForGetSharelist());
        return dataList;
    }

    //제이슨 파싱해서 데이터 리스트 얻어오는 부분
    private ArrayList<TripRouteDataInfo> jsonParserForGetSharelist() {

        HttpShareInfoGetList httpShareInfoList = new HttpShareInfoGetList();
        HttpShareInfoGetList.sendTask send = httpShareInfoList.new sendTask();

        String result = null;

        ArrayList<TripRouteDataInfo> list = new ArrayList<TripRouteDataInfo>();

        try {
            result = send.execute().get();
            JSONArray jarray = null;

            jarray = new JSONObject(result).getJSONArray("list");

            if (jarray != null) {

                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jsonObject = jarray.getJSONObject(i);

                    String code = jsonObject.getString("planCode");
                    String place = jsonObject.getString("locationTitle");
                    String title = jsonObject.getString("shareTitle");
                    String contents = jsonObject.getString("content");
                    TripRouteDataInfo model = new TripRouteDataInfo(code, place, title, contents);
                    list.add(model);
                }

                return list;
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }

        return list;
    }


    //제이슨 파싱해서 데이터 리스트 얻어오는 부분
    private ArrayList<PlanRouteDataModel> jsonParserForGetRoutelist(String plancode) {

        HttpShareRouteInfoList httpRouteList = new HttpShareRouteInfoList();
        HttpShareRouteInfoList.sendTask send = httpRouteList.new sendTask();

        String result = null;

        ArrayList<PlanRouteDataModel> list = new ArrayList<PlanRouteDataModel>();

        try {
            result = send.execute(plancode).get();
            JSONArray jarray = null;

            jarray = new JSONObject(result).getJSONArray("list");

            if (jarray != null) {

                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jsonObject = jarray.getJSONObject(i);
                    String name = SaveSharedPreference.getUserName(getContext());

                    Log.i("234234234",name);
                    String userCode = name;
                    String planCode = jsonObject.getString("planCode");
                    String planDate = jsonObject.getString("planDate");
                    String title = jsonObject.getString("title");
                    String locationx = jsonObject.getString("locationx");
                    Double mapx =  Double.parseDouble(locationx);
                    String locationy = jsonObject.getString("locationy");
                    Double mapy =  Double.parseDouble(locationy);
                    String index = jsonObject.getString("index");
                    int index1 = Integer.parseInt(index);
                    String memo = jsonObject.getString("memo");

                    PlanRouteDataModel model = new PlanRouteDataModel(userCode,planCode,planDate,title,mapx,mapy,memo,index1);

                    list.add(model);
                }

                return list;
            }
        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }

        return list;
    }


    public ArrayList<String> getDate(String start, String end) {
        final String DATE_PATTERN = "yy/MM/dd";
        String inputStartDate = start;
        String inputEndDate = end;
        ArrayList<String>  date = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);

        try {
            Date startDate = sdf.parse(inputStartDate);
            Date endDate = sdf.parse(inputEndDate);

            Date currentDate = startDate;

            while (currentDate.compareTo(endDate) <= 0) {
                date.add(sdf.format(currentDate));
                Calendar c = Calendar.getInstance();
                c.setTime(currentDate);
                c.add(Calendar.DAY_OF_MONTH, 1);
                currentDate = c.getTime();
            }

            return date;
        } catch (Exception e) {
        }
        return date;
    }

}