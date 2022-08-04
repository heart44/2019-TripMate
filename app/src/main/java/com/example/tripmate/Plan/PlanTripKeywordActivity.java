/* 관광지 리스트를 보여주는 화면 */

package com.example.tripmate.Plan;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.tripmate.Plan.DesignClassFile.PlanKeywordListAdapter;
import com.example.tripmate.R;
import com.example.tripmate.TourAPI.TripDataInfo;
import com.example.tripmate.TourAPI.TripInfoAPI;

import java.util.ArrayList;
import java.util.List;

public class PlanTripKeywordActivity extends AppCompatActivity implements PlanKeywordListAdapter.ItemClickListener{

    private List<TripDataInfo> list = new ArrayList(); // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private PlanKeywordListAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<TripDataInfo> arraylist;
    private TripInfoAPI api;
    private TextView textview;
    private Button placeAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyword_search);

        /* 검색 값 셋팅 부분 */
        Intent intent = getIntent(); /*데이터 수신*/
        final String keyword = intent.getStringExtra("tripplace"); /*String형*/

        ListView listview;
        final PlanKeywordListAdapter adapter;

        //Adapter 생성
        adapter = new PlanKeywordListAdapter();

        listview = (ListView) findViewById(R.id.mainListViewer);
        listview.setAdapter(adapter);

        adapter.addItemClickListener(this);
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

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent();
        intent.putExtra("itemInfo", arraylist.get(position));
        setResult(RESULT_OK, intent);
        finish();
    }
}
