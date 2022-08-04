/* 화면 : 메인 */

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
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tripmate.Main.MainRecyclerAdapter;
import com.example.tripmate.Main.MainSearchListActivity;
import com.example.tripmate.Plan.DataClassFile.PlanListModel;
import com.example.tripmate.Plan.SelectPlanList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class fragmentActivity1  extends Fragment {

    protected View mainFragment;
    protected ImageButton textBtn;
    protected ImageButton memberAdd;
    protected TextView main_name_text;
    protected TextView main_day_text;
    protected MainRecyclerAdapter adapter;
    protected String nickname;

    protected ArrayList<PlanListModel> planList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainFragment = inflater.inflate(R.layout.fragment_main1, container, false);
        nickname = SaveSharedPreference.getNickName(getContext());

        init();

        //검색창을 누르면 나타나는 버튼
        textBtn = mainFragment.findViewById(R.id.et_sch);
        textBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent1 = new Intent(getActivity(), MainSearchListActivity.class);
                startActivity(intent1);
            }
        });

        //이름 설정
        main_name_text = mainFragment.findViewById(R.id.main_name_text);
        main_name_text.setText(nickname);

        main_day_text = mainFragment.findViewById(R.id.main_day_text);

        try {
            String date = compareDate();
            main_day_text.setText(date);

        } catch (Exception e) {
            e.printStackTrace();
            main_day_text.setText("0");
        }

        return mainFragment;
    }

    public void init() {

        SelectPlanList list = new SelectPlanList();

        planList.addAll(list.getList(nickname));

        RecyclerView recyclerView = mainFragment.findViewById(R.id.main_recylcer_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);

        adapter = new MainRecyclerAdapter(planList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    public String compareDate() {

        final long dates;
        final long now = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd");
        ArrayList<Date> date = new ArrayList<Date >();

        for(int i = 0; i < planList.size(); i++) {
            try {
                Date temp = format.parse( planList.get(i).getPlanStart());
                date.add(temp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Date closest = Collections.min(date, new Comparator<Date>() {
            public int compare(Date d1, Date d2) {
                long diff1 = Math.abs(d1.getTime() - now);
                long diff2 = Math.abs(d2.getTime() - now);
                return Long.compare(diff1, diff2);
            }
        });

        Calendar cal = Calendar.getInstance();
        cal.setTime(closest);
        Calendar today = Calendar.getInstance();

        long day = today.getTimeInMillis()/86400000;
        long tday = cal.getTimeInMillis()/86400000;

        long count = tday - day;

        int values = (int) count+1;

        return Integer.toString(values);

    }

}
