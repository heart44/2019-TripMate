package com.example.tripmate.Board.Matching;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.tripmate.Board.BoardListAdapter;
import com.example.tripmate.Board.BoardModel;
import com.example.tripmate.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MatchingActivity extends AppCompatActivity {
    private static ArrayList<BoardModel> dataList;
    private static BoardListAdapter adapter;
    private RecyclerView recyclerView;
    private static String nickname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        Intent intent = getIntent();
        String result = intent.getExtras().getString("result");
        System.out.println(result);
        nickname = intent.getExtras().getString("nickname");
        recyclerView = findViewById(R.id.matching_recyclerview);
        init(result);

    }

    private void init(String result) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        adapter = new BoardListAdapter(jsonParserForGetMatchinglist(result));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private ArrayList<BoardModel> jsonParserForGetMatchinglist(String result) {

        dataList = new ArrayList<>();

        JSONArray jarray = null;

        try {
            jarray = new JSONObject(result).getJSONArray("matching");
            if (jarray != null) {

                for (int i = 0; i < jarray.length(); i++) {
                    JSONObject jsonObject = jarray.getJSONObject(i);
                    String date = jsonObject.getString("date");
                    int gender = jsonObject.getInt("gender");
                    int maxage = jsonObject.getInt("maxage");
                    String purpose = jsonObject.getString("purpose");
                    int minage = jsonObject.getInt("minage");
                    String nickname = jsonObject.getString("nickname");
                    String destination = jsonObject.getString("destination");
                    String boardCode = jsonObject.getString("boardcode");
                    BoardModel boardModel = new BoardModel(boardCode, nickname, destination, gender, minage, maxage, purpose, date);
                    dataList.add(boardModel);
                }
            }
            else{
                Toast.makeText(this,"조건에 맞는 데이터가 없습니다",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
