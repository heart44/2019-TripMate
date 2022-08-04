/* 관광지 리스트를 보여주는 화면 */

package com.example.tripmate.Main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tripmate.R;
import com.example.tripmate.TourAPI.TripDataInfo;
import com.example.tripmate.TourAPI.TripInfoAPI;

import java.util.ArrayList;
import java.util.List;

public class MainKeywordActivity extends AppCompatActivity {
    private List<TripDataInfo> list = new ArrayList(); // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private KeywordListAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<TripDataInfo> arraylist;
    private TripInfoAPI api;
    private TextView textview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_search);

        ListView listview;
        final KeywordListAdapter adapter;

        //Adapter 생성
        adapter = new KeywordListAdapter();

        listview = (ListView) findViewById(R.id.mainListViewer);
        listview.setAdapter(adapter);

        /* 검색 값 셋팅 부분 */
        Intent intent = getIntent(); /*데이터 수신*/
        final String keyword = intent.getStringExtra("keyword"); /*String형*/
        Log.i("332423423",keyword);
        arraylist = new ArrayList<>();

        textview = (TextView)findViewById(R.id.top_title);
        textview.setText(keyword);

        new Thread(new Runnable() {
            public void run() {
                arraylist = new ArrayList<>();
                api = new TripInfoAPI();
                arraylist.addAll(api.getTripInfo(keyword));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for(int i = 0; i < arraylist.size(); i++) {
                            adapter.addItem(arraylist.get(i).getTitle(),arraylist.get(i).getAddress1(),arraylist.get(i).getAddress2(),arraylist.get(i).getImgURL(),arraylist.get(i).getTitle());
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

    }


}
