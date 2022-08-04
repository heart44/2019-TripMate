package com.example.tripmate.Location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.tripmate.R;
import com.skt.Tmap.TMapCircle;
import com.skt.Tmap.TMapGpsManager;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapTapi;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

//import com.gun0912.tedpermission.PermissionListener;
//import com.gun0912.tedpermission.TedPermission;



public class NearLocationFragment extends Fragment implements TMapGpsManager.onLocationChangedCallback {
    private View v;
    private ViewSwitcher switcher;

    private LocationManager locationManager;
    private static final int REQUEST_CODE_LOCATION = 2;

    //지도 화면 좌표 / 임의의 좌표로 설정해놓음/ 추후에 필요한 값 가져와줘야함

    private TMapView tMapView;
    private TMapCircle tMapCircle;
    private TMapPoint tMapPoint;


    private TMapGpsManager gpsMaager = null; //단말기 위치 탐색을 위한 변수(api 클래스)
    private Context mContext =null;

    private LocationInfoAPI api;

    private FloatingActionButton fab;
    private Button searchButton;

    private LocationListViewAdapter adapter;
    private ListView listView;

    private ArrayList<LocationDataInfo> arrayList;

    private Button SettingButton;
    //int seekNumber =0;
    int typeNum = 0;
    int radiusChange = 10000;

    private boolean isPerGranted = false;
    private boolean isitclicked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    //프레그먼트에 표시할 뷰를 레이아웃으로 부터 읽어오는역할 onCreateView
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.near_loaction_fragment, container, false);

        RelativeLayout linearLayoutTmap = v.findViewById(R.id.linearLayoutTmap);
        tMapView = new TMapView(getActivity());


        tMapView.setSKTMapApiKey("da76283a-f8ad-4e05-ab6b-94612df7b98e");
        tMapView.setLanguage(TMapView.LANGUAGE_KOREAN);
        tMapView.setIconVisibility(false); //현위치 아이콘표시
        tMapView.setZoomLevel(15); //줌레벨
        tMapView.setMapType(TMapView.MAPTYPE_STANDARD);

        tMapView.setCompassMode(false); //현재 보는 방향
        tMapView.setTrackingMode(true);

        //gpsMarker = new TMapGpsManager(v.getContext());
        //gpsMarker.setMinTime(1000); //위치가 옮겨질시 갱신 주기
        //gpsMarker.setMinDistance(5); //위치가 옮겨질시 갱신 거리
        //gpsMarker.setProvider(gpsMarker.NETWORK_PROVIDER); //연결된 인터넷으로 현위치를 받음 , 실내일때 유용
        //gpsMarker.setProvider(gpsMarker.GPS_PROVIDER); //GPS로 현위치를 받음

        // DisplayMetrics dm = getContext().getApplicationContext().getResources().getDisplayMetrics();
        // int width = dm.widthPixels;
        // int height = dm.heightPixels;


        //locationDialog = new LocationSettingDialog(getContext());
        // WindowManager.LayoutParams wm = locationDialog.getWindow().getAttributes(); //다이얼로그 높이 너비 설정
        // wm.copyFrom(locationDialog.getWindow().getAttributes());
        //  wm.width = width/2;
        // wm.height = height/2;

        SettingButton = v.findViewById(R.id.SettingButton);
        SettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("세팅 버튼을 누름");
                ShowDialog();

            }
        });


        searchButton= v.findViewById(R.id.SearchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("search 버튼을 누름");
                addTMapMarkerItem();
            }
        });


        adapter = new LocationListViewAdapter();
        listView = v.findViewById(R.id.location_list);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TMapTapi tMapTapi = new TMapTapi(getContext());
                for(int i = 0; i<position; i++) {
                    tMapTapi.invokeNavigate("목적지", (float) arrayList.get(position).getMapX(),(float) arrayList.get(position).getMapY(),0,true);
                }


            }
        });


        onSwitchButtonClick1(v); //리스트뷰전환 버튼 클릭이벤트 추가
        onSwitchButtonClick2(v); //맵뷰전환 버튼 클릭이벤트 추가

        onFabClick(v);

        linearLayoutTmap.addView(tMapView); //지도 추가


        return v;
    }


    public void onSwitchButtonClick1(View v) { //리스트뷰전환 버튼 클릭이벤트 추가
        Button switchListButton = v.findViewById(R.id.switchListButton);
        // Button switchMapButton = (Button)v.findViewById(R.id.switchMapButton);

        switcher = v.findViewById(R.id.switch_layout);


        if (switchListButton.getText().equals("ListView")) {
            switchListButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isitclicked == false) {
                        Toast toast = Toast.makeText(getContext(), "먼저 현재 위치를 조회하십시오", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
                        Toast toast = Toast.makeText(getContext(), "이미지 터치시 네비게이션으로 이동됩니다", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.TOP, 0, 300);
                        toast.show();
                        switcher.showNext();
                    }


                }
            });
        }

    }

    public void onSwitchButtonClick2(View v) { //맵뷰전환 버튼 클릭이벤트 추가
        Button switchMapButton = v.findViewById(R.id.switchMapButton);

        switcher = v.findViewById(R.id.switch_layout);

        if (switchMapButton.getText().equals("MapView")) {
            switchMapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switcher.showPrevious();
                }
            });
        }

    }


    public void onFabClick(View v){
        FloatingActionButton fab = v.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                isPerGranted = true;

                final LocationManager lm = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    return;
                }

                Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();


                tMapPoint = new TMapPoint(longitude,latitude);
                tMapPoint.setLatitude(latitude);
                tMapPoint.setLongitude(longitude);


                gpsMaager = new TMapGpsManager(getContext());
                gpsMaager.getLocation().setLatitude(latitude);
                gpsMaager.getLocation().setLongitude(longitude);

                gpsMaager.setMinTime(1000);
                gpsMaager.setMinDistance(5);
                gpsMaager.setProvider(TMapGpsManager.NETWORK_PROVIDER);
                gpsMaager.OpenGps();

                tMapView.setIconVisibility(true);
                tMapView.setTrackingMode(false);
                tMapView.setSightVisible(false); //시야표출여부를 설정
                tMapView.setCenterPoint(longitude, latitude, true);

                tMapView.removeAllMarkerItem();

                Bitmap bitmap2 = BitmapFactory.decodeResource(v.getContext().getResources(), R.drawable.custom_marker1);
                TMapMarkerItem tMapMarkerItem2 = new TMapMarkerItem(); //지도 마커 표시 변수 (api 클래스)

                tMapMarkerItem2.setIcon(bitmap2); //마커 아이콘 지정

                tMapMarkerItem2.setPosition(0.5f, 0.5f); //마커의 중심점을 중앙,하단으로 설정
                tMapMarkerItem2.setTMapPoint(tMapPoint); //마커의 좌표지정
                tMapMarkerItem2.setName("현재 위치"); //마커의 타이틀 지정
                tMapMarkerItem2.setCalloutTitle("나의 위치");
                tMapMarkerItem2.setCanShowCallout(true);

                tMapView.setZoomLevel(11); //줌레벨
                tMapView.setIconVisibility(false); //현위치 아이콘표시
                tMapView.addMarkerItem("tMapMarkerItem2", tMapMarkerItem2); //지도에 마커추가
                tMapView.setCenterPoint(longitude,latitude,true);


                TMapCircle tMapCircle = new TMapCircle();
                tMapCircle.setCenterPoint(tMapPoint);
                tMapCircle.setAreaColor(Color.GRAY);
                tMapCircle.setAreaAlpha(100); //투명도
                tMapCircle.setRadius(radiusChange);
                tMapCircle.setRadiusVisible(true);
                tMapView.addTMapCircle("범위",tMapCircle);


                isitclicked =true;

                Toast toast = Toast.makeText(getContext(), "현재 위치 조회완료", Toast.LENGTH_LONG);
                toast.show();


            }
        });


    }

    @Override
    public void onLocationChange(Location location) {
        if (isPerGranted == true) {
            tMapView.setLocationPoint(location.getLongitude(), location.getLatitude());
        }
    }

    public double getDistance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6373.0; //meters
        double dLat = Math.toRadians(lat2) - Math.toRadians(lat1);
        double dLng = Math.toRadians(lon2) - Math.toRadians(lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = earthRadius * c;

        return dist; //km로 환산
    }

    public void addTMapMarkerItem() { //마커 추가
        if(isitclicked == false){
            Toast toast = Toast.makeText(getContext(), "먼저 현재 위치를 조회하십시오", Toast.LENGTH_LONG);
            toast.show();
        } else {
            new Thread(new Runnable() {
                public void run() {
                    arrayList = new ArrayList<LocationDataInfo>();
                    LocationInfoAPI api = new LocationInfoAPI();


                    arrayList.addAll(api.getLocationInfo(contentChanger(), tMapPoint.getLongitude(), tMapPoint.getLatitude(), radiusChange));

                    // tMapView.removeAllMarkerItem();

                    adapter.clear();
                    adapter.notifyDataSetChanged();


                    for (int i = 0; i < arrayList.size(); i++) {

                        TMapMarkerItem tMapMarkerItem3 = new TMapMarkerItem();//지도 마커 표시 변수 (api 클래스)
                        Bitmap icon = BitmapFactory.decodeResource(v.getContext().getResources(), R.drawable.custom_marker2);

                        tMapMarkerItem3.setTMapPoint(new TMapPoint(arrayList.get(i).getMapY(), arrayList.get(i).getMapX()));

                        tMapMarkerItem3.setIcon(icon); //아이콘 지정

                        tMapMarkerItem3.setName(arrayList.get(i).getTitle());
                        tMapMarkerItem3.setVisible(TMapMarkerItem.VISIBLE);

                        tMapMarkerItem3.setCalloutTitle(arrayList.get(i).getTitle());
                        tMapMarkerItem3.setCalloutSubTitle(arrayList.get(i).getDist() / 1000 + "km");
                        tMapMarkerItem3.setCanShowCallout(true);

/*
                        tMapMarkerItem3.set
                        TMapPoint tMapPointStart = new TMapPoint(tMapPoint.getLatitude(), tMapPoint.getLongitude());
                        TMapPoint tMapPointEnd = new TMapPoint(arrayList.get(i).getMapY(), arrayList.get(i).getMapX());

                        try {
                            TMapPolyLine tMapPolyLine = new TMapData().findPathData(tMapPointStart, tMapPointEnd);
                            tMapPolyLine.setLineColor(Color.BLUE);
                            tMapPolyLine.setLineWidth(2);
                            tMapView.addTMapPolyLine("Line1", tMapPolyLine);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
*/


                        adapter.addItem(arrayList.get(i).getTitle(), arrayList.get(i).getAddress1(), arrayList.get(i).getFirstimage(), arrayList.get(i).getTel(), arrayList.get(i).getDist(), arrayList.get(i).getMapX(), arrayList.get(i).getMapY());
                        adapter.sort();

                        tMapView.addMarkerItem(arrayList.get(i).getTitle(), tMapMarkerItem3);

                        double distance = getDistance(tMapPoint.getLatitude(), tMapPoint.getLongitude(), arrayList.get(i).getMapY(), arrayList.get(i).getMapX());

                        Log.i("uuuuuu", arrayList.get(i).getTitle());
                        Log.i("uuuuuu", Double.toString(arrayList.get(i).getMapX()));
                        Log.i("uuuuuu", Double.toString(arrayList.get(i).getMapY()));
                        Log.i("uuuuuu", Double.toString(distance));
                        Log.i("uuuuuu", Double.toString(arrayList.get(i).getDist() / 1000));
                    }

                }
            }).start();

        }
    }



/*
    public void ShowDialog(){
        LayoutInflater dialog = LayoutInflater.from(getContext());
        final View locationDialog = dialog.inflate(R.layout.location_setting_dialog, null);
        final Dialog myDialog = new Dialog(getContext());

        final RadioGroup radioGroup;
        final Button submit;
        final Button cancel;
        final SeekBar seekBar;
        final TextView outcome;

        final RadioButton radio1;
        final RadioButton radio2;
        final RadioButton radio3;
        final RadioButton radio4;
        final RadioButton radio5;
        final RadioButton radio6;

        final int typeNumber;


        myDialog.setTitle("Setting");
        myDialog.setContentView(locationDialog);
        myDialog.show();

        submit = (Button) locationDialog.findViewById(R.id.submitButton);
        cancel = (Button) locationDialog.findViewById(R.id.cancelButton);
        radioGroup = (RadioGroup) locationDialog.findViewById(R.id.radioGroup1);
        seekBar = (SeekBar) locationDialog.findViewById(R.id.seekBar);
        outcome = (TextView) locationDialog.findViewById(R.id.outcomeText);

        radio1 = (RadioButton)locationDialog.findViewById(R.id.radio1);
        radio2 = (RadioButton)locationDialog.findViewById(R.id.radio2);
        radio3 = (RadioButton)locationDialog.findViewById(R.id.radio3);
        radio4 = (RadioButton)locationDialog.findViewById(R.id.radio4);
        radio5 = (RadioButton)locationDialog.findViewById(R.id.radio5);
        radio6 = (RadioButton)locationDialog.findViewById(R.id.radio6);


        if(radio1.isChecked()){
            typeNumber = 12;
        }
        else if(radio2.isChecked()){
            typeNumber = 39;
        }
        else if(radio3.isChecked()){
            typeNumber = 32;
        }
        else if(radio4.isChecked()){
            typeNumber = 38;
        }
        else if(radio5.isChecked()){
            typeNumber = 15;
        }
        else if(radio6.isChecked()){
            typeNumber = 14;
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio1:
                        typeNumber = 12;
                        break;
                    case R.id.rb_blue:
                        container.setBackgroundColor(Color.rgb(0, 0, 255));
                        break;
                    case R.id.rb_green:
                        container.setBackgroundColor(Color.rgb(0, 255, 0));
                        break;

                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekNumber = seekBar.getProgress();
                outcome.setText(new StringBuilder().append(seekNumber));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekNumber = seekBar.getProgress(); //식바의 움직임이 시작될 때 실행될 사항
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekNumber = seekBar.getProgress(); //식바의 움직임이 멈출다면 실행될 사항
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.cancel();
            }
        });





    }

*/

    public void ShowDialog() {
        LocationSettingDialog dialog = new LocationSettingDialog(getContext());

        dialog.setDialogListener(new LocationSettingDialog.CustomDialogListener() {
            @Override
            public void onSubmitClicked(int typeNumber, int rad) {
                typeNum = typeNumber;
                radiusChange = rad * 1000;

                // Toast toast = Toast.makeText(getContext(), Integer.toString(typeNum), Toast.LENGTH_LONG);
                Toast toast = Toast.makeText(getContext(), "테마 및 반경 설정완료", Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onCancelClicked() {

            }
        });
        dialog.show();
    }


    public String contentChanger() {
        if (typeNum == 0) {
            typeNum = 12;

            return Integer.toString(typeNum);
        } else {
            return Integer.toString(typeNum);
        }
    }


}







