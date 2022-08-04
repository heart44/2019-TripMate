package com.example.tripmate.Plan;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripmate.Plan.Cost.PlanCostActivity;
import com.example.tripmate.Plan.DataClassFile.PlanRouteDataModel;
import com.example.tripmate.Plan.DesignClassFile.PlanKeywordListAdapter;
import com.example.tripmate.Plan.DesignClassFile.PlanRouteAdapter;
import com.example.tripmate.R;
import com.example.tripmate.SaveSharedPreference;
import com.example.tripmate.TourAPI.TripDataInfo;
import com.example.tripmate.fragmentActivity2;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import okhttp3.internal.Internal;


//액티비티2에서 리스트 클릭시 넘어오는 화면

public class PlanTripActivity extends AppCompatActivity implements PlanRouteAdapter.ClickListener {

    private static PlanTripActivity instance;
    private static RecyclerView recyclerView;
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    private SectionedRecyclerViewAdapter sectionedAdapter;
    private TextView trip_add,plan_title, trip_cost;
    private Button btn;
    private ImageButton back;
    private TMapView tMapView;
    private TMapMarkerItem markerItem1;
    private TMapPolyLine tline;
    private ArrayList<ResultTitle> model;
    private ArrayList<ResultMap> mapModel = new ArrayList<>();
    private ResultMap map;

    //기본적인 셋팅을 위한 데이터셋
    private ArrayList<String> dateList;
    private static String plancode;
    private String placeValue;
    public static String startDate;
    public static String endDate;
    private static String userid;

    //여행 일정 추가를 위한 데이터셋
    private String title;
    private String placeValue2 = " ";
    private String locationx;
    private String locationy;
    private String img;
    private String totalPrice;
    private String memo;
    private int index = 1;
    private boolean flag = false;


    private ArrayList<PlanRouteDataModel> mItems = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_trip);

        //기본적으로 사용자 화면에서 가지고 와야 할 정보들
        Intent intent = getIntent();
        plancode = intent.getExtras().getString("plancode");
        //PlanListAdapter 넘어온 place 값
        placeValue = intent.getExtras().getString("place");
        startDate = intent.getExtras().getString("startDate");
        endDate = intent.getExtras().getString("endDate");
        userid = SaveSharedPreference.getUserName(this).toString();
        this.getDate(startDate,endDate);


        //타이틀 설정 부분
        plan_title = (TextView)findViewById(R.id.place_title);
        plan_title.setText(placeValue);

        //tmap연결
        LinearLayout linearTmap = (LinearLayout)findViewById(R.id.ln_tmap);
        tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey("0b480565-acb3-4bcb-a808-20854b70cc21");
        tMapView.setCompassMode(true);
        tMapView.setIconVisibility(true);
        tMapView.setZoomLevel(15);
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        tMapView.setSightVisible(true);
        location();
        linearTmap.addView(tMapView);

        //일정 추가하기 버튼
        trip_add = (TextView)findViewById(R.id.tv_tripadd);
        trip_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, PlanTripKeywordActivity.class);
                intent.putExtra("tripplace", placeValue);
                startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            }
        });

        //AddListActivity에서 넘어온 값
        Intent intent1 = getIntent();
        placeValue = intent1.getStringExtra("place");

        //가계부
        trip_cost = (TextView)findViewById(R.id.tv_costadd);
        trip_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(PlanTripActivity.this, PlanCostActivity.class);
                intent2.putExtra("plancode", plancode);
                intent2.putExtra("place", placeValue);
                intent2.putExtra("start", startDate);
                intent2.putExtra("end", endDate);
                startActivity(intent2);
            }
        });

        //데이트에 관한 리스트 업로드
        recyclerView = findViewById(R.id.lv_trip);
        getDate(startDate,endDate);
        Init();

        //결과 전송 버튼
        btn = (Button)findViewById(R.id.fab_result_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("23423423423","111111111111");
                for(int i = 0; i < mItems.size(); i++) {

                    String userid1 = mItems.get(i).getUserid();
                    String plancode1 = mItems.get(i).getPlancode();
                    String date1 = mItems.get(i).getPlandate();
                    String title1 = mItems.get(i).getTitle();
                    String locationx1 = Double.toString(mItems.get(i).getLocationx());
                    String locationy1 = Double.toString(mItems.get(i).getLocationy());
                    String memo1 = mItems.get(i).getMemo();
                    String index1 = Integer.toString(mItems.get(i).getIndex());

                    HttpPlanRouteAdd httpRouteListAdd = new HttpPlanRouteAdd();
                    HttpPlanRouteAdd.SendTask sendTask = httpRouteListAdd.new SendTask();
                    Log.i("23423423423","222222222222");

                    String result = null;

                try {
                    result = sendTask.execute(userid1,plancode1,date1,title1,locationx1,locationy1,memo1,index1,"0").get();
                    Log.i("23423423423","333333333333");
                    if("success".equals(result)){
                        Log.i("23423423423","4444444444");
                        //onBackPressed();

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                }
                fragmentActivity2.getInstance().init();
                finish();
                }

        });

        //뒤로가기 버튼
        back = (ImageButton)findViewById(R.id.trip_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public String getUId(){
        return userid;
    }
    public String getpCode(){
        return plancode;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK
                TripDataInfo info = (TripDataInfo)data.getSerializableExtra("itemInfo");
                title = info.getTitle();
                locationx = info.getMapX();
                locationy = info.getMapY();
                img  = info.getImgURL();
            }
        }
    }

    public void Init() {
        sectionedAdapter = new SectionedRecyclerViewAdapter();

        // mItems.add(n);
        for(int i = 0; i < dateList.size(); i++) {
            sectionedAdapter.addSection(new PlanRouteAdapter(dateList.get(i),this));

        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sectionedAdapter);




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

    //플러스 버튼 눌렀을 때 발생하는 이벤트 정의 부분
    @Override
    public void onHeaderRootViewClicked(@NonNull String sectionTitle, @NonNull PlanRouteAdapter section) {
        try{
            Double mapx = null;
            Double mapy = null;

            if(!flag){
                flag = true;
                SelectPlanList data = new SelectPlanList();
                ArrayList<PlanRouteDataModel> list = new ArrayList<>();
                list = data.getRoute(getUId(), getpCode());

                Log.i("487539lshghe45656", sectionTitle);
                for (int j = 0; j < list.size(); j++){
                    PlanRouteDataModel item = new PlanRouteDataModel(list.get(j).getUserid(), list.get(j).getPlancode(), list.get(j).getPlandate(), list.get(j).getTitle(), list.get(j).getLocationx(), list.get(j).getLocationy(), list.get(j).getMemo(), list.get(j).getIndex());
                    if(sectionTitle.equals(list.get(j).getPlandate())){
                        Log.i("487539lshghle",list.get(j).getPlandate());
                        mItems.add(item);
                        section.addItem(item);
                    }
                    else
                        Log.i("4875234sfdg1212121212","다름");
                }
                sectionedAdapter.notifyDataSetChanged();

                for (int i = 0; i < data.getRoute(getUId(), getpCode()).size(); i++){
                    mapx = data.getRoute(getUId(), getpCode()).get(i).getLocationx();
                    mapy = data.getRoute(getUId(),getpCode()).get(i).getLocationy();
                    if(mapx == null || mapy == null) {
                        return;
                    } else {
                        marker(String.valueOf(mapx), String.valueOf(mapy));
                    }
                }
            }

            mapx = Double.parseDouble(locationx);
            mapy = Double.parseDouble(locationy);

            //
            PlanRouteDataModel item = new PlanRouteDataModel(userid, plancode, sectionTitle, title, mapx, mapy, "", index);
            mItems.add(item);

            section.addItem(item);
            sectionedAdapter.notifyDataSetChanged();
            item = null;

            marker(locationx, locationy);

        } catch (Exception e) {
            if(mItems.isEmpty()) {
                AlertDialog.Builder alert = new AlertDialog.Builder(PlanTripActivity.this);
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();     //닫기
                    }
                });
                alert.setMessage("현재 가져온 여행 일정이 없습니다.");
                alert.show();
            }
        }
    }

    //받은 좌표 배열에 넣어 마커찍기
    public void marker(String mapX, String mapY){
        map = new ResultMap(Double.parseDouble(mapX), Double.parseDouble(mapY));
        mapModel.add(map);

        tline = new TMapPolyLine();
        tline.setLineColor(Color.BLUE);
        tline.setLineWidth(2);
        for(int i = 0; i < mapModel.size(); i++){
            markerItem1 = new TMapMarkerItem();
            TMapPoint point = new TMapPoint(mapModel.get(i).getY(), mapModel.get(i).getX());
            markerItem1.setPosition(0.5f, 1.0f);
            markerItem1.setTMapPoint(point);
            markerItem1.setVisible(TMapMarkerItem.VISIBLE);
            tMapView.setCenterPoint(mapModel.get(i).getX(), mapModel.get(i).getY());
            tMapView.addMarkerItem("markerItem"+i, markerItem1);
            tline.addLinePoint(point);
        }
        tMapView.addTMapPolyLine("line1", tline);
    }

    //아이콘 설정


    //지역별 지도
    private void location(){
        if(placeValue.equals("부산") || placeValue2.equals("부산")){
            tMapView.setCenterPoint(129.07556, 35.17944, true);
        } else if(placeValue.equals("대구") || placeValue2.equals("대구")){
            tMapView.setCenterPoint(128.60250, 35.87222, true);
        } else if(placeValue.equals("전주") || placeValue2.equals("전주")) {
            tMapView.setCenterPoint(127.15000, 35.82500, true);
        } else if(placeValue.equals("서울") || placeValue2.equals("서울")){
            tMapView.setCenterPoint(126.97806, 37.56667, true);
        } else if(placeValue.equals("광주") || placeValue2.equals("광주")){
            tMapView.setCenterPoint(127.25639, 37.41750, true);
        } else if(placeValue.equals("포항") || placeValue2.equals("포항")){
            tMapView.setCenterPoint(129.34167, 36.01944, true);
        } else if(placeValue.equals("경주") || placeValue2.equals("경주")){
            tMapView.setCenterPoint(129.22472, 35.85611, true);
        } else if(placeValue.equals("제주") || placeValue2.equals("제주")){
            tMapView.setCenterPoint(126.51667, 33.50000, true);
        } else if(placeValue.equals("남해") || placeValue2.equals("남해")){
            tMapView.setCenterPoint(127.8924234, 34.8376721, true);
        } else if(placeValue.equals("통영") || placeValue2.equals("통영")){
            tMapView.setCenterPoint(128.42917, 34.85556, true);
        } else if(placeValue.equals("속초") || placeValue2.equals("속초")){
            tMapView.setCenterPoint(128.59194, 38.20694, true);
        } else if(placeValue.equals("춘천") || placeValue2.equals("춘천")){
            tMapView.setCenterPoint(128.873749, 37.757687, true);
        } else if(placeValue.equals("강릉") || placeValue2.equals("강릉")){
            tMapView.setCenterPoint(128.88333, 37.75000, true);
        }
    }

    //타이틀 클래스
    public static class ResultTitle{
        String title;

        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
    }

    //좌표 클래스
    public class ResultMap{
        Double x;
        Double y;

        public ResultMap(Double x, Double y){
            this.x = x;
            this.y = y;
        }
        public Double getX() { return x; }
        public void setX(Double x) { this.x = x; }
        public Double getY() { return y; }
        public void setY(Double y) { this.y = y; }
    }
    public static PlanTripActivity getInstance() {
        if(instance == null){
            instance = new PlanTripActivity();
            return instance;
        }
        return instance;
    }

}
