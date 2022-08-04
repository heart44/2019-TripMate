package com.example.tripmate;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tripmate.Plan.AddListActivity;
import com.example.tripmate.Plan.DataClassFile.PlanListModel;
import com.example.tripmate.Plan.DesignClassFile.PlanListAdapter;
import com.example.tripmate.Plan.SelectPlanList;
import com.example.tripmate.Share.HttpShareInfoAdd;
import com.example.tripmate.Share.HttpShareInfoStateUpdate;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class fragmentActivity2 extends Fragment {

    private static RecyclerView recyclerView;
    private ArrayList<PlanListModel> planlist;
    private static PlanListAdapter adapter;
    private View view;
    private static String nickname;
    private static fragmentActivity2 instance;
    private static int refreshCount = 1;
    private String userid;
    private String sharePlace;
    private String shareContents;

    public static fragmentActivity2 getInstance() {
        if (instance == null) {
            instance = new fragmentActivity2();
            return instance;
        }
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main2, container, false);
        userid = SaveSharedPreference.getUserName(getContext());
        nickname = SaveSharedPreference.getNickName(getContext());
        recyclerView = view.findViewById(R.id.rc_list);

        init2();

        final FloatingActionButton addBt = view.findViewById(R.id.fab_addlist);
        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(), AddListActivity.class);
                startActivity(intent1);
            }
        });

        return view;
    }

    public void init() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        SelectPlanList data = new SelectPlanList();

        adapter = new PlanListAdapter(data.getList(getNickname()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();
    }

    private void init2() {

        RecyclerView recyclerView = view.findViewById(R.id.rc_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        SelectPlanList data = new SelectPlanList();
        planlist = data.getList(nickname);

        adapter = new PlanListAdapter(planlist, new PlanListAdapter.ItemClickListener() {
            @Override
            public void onItemCheked(int position) {
                /* 공유한 상태일때
                   1. 공유 게시판에 현재 포지션에 대한 아이템 정보를 가져와서 공유 DB에 넣어준다.
                */
                dialog(position);
            }

            @Override
            public void onItemUncheked(int position) {
                 /* 공유한 상태일때
                   1. 공유 게시판의 현재 플랜코드에 대한 게시물 정보의 상태를 1로 변경한다. 만약 없는 경우, 토스트 메시지 처리한다.
                */
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("공유 상태 취소")        // 제목 설정
                        .setMessage("공유 상태를 취소하시겠습니까?")        // 메세지 설정
                        .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                dataDelete(position);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();    // 알림창 객체 생성
                dialog.show();

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter.notifyDataSetChanged();

    }

    public void dataInsert(int position) {
        String userid1 = userid;
        String plancode1 = planlist.get(position).getPlanCode();
        String content1 = shareContents;
        String title1 = planlist.get(position).getPlanPlace();
        String shareContet = shareContents;

        HttpShareInfoAdd httpShareInfo = new HttpShareInfoAdd();
        HttpShareInfoAdd.SendTask sendTask = httpShareInfo.new SendTask();

        String result = null;

        try {
            result = sendTask.execute(userid1, plancode1, content1, title1, shareContet).get();

            if ("success".equals(result)) {

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void dataDelete(int position) {

        String plancode1 = planlist.get(position).getPlanCode();


        HttpShareInfoStateUpdate httpSharedelte = new HttpShareInfoStateUpdate();
        HttpShareInfoStateUpdate.SendTask sendTask = httpSharedelte.new SendTask();

        String result = null;

        try {
            result = sendTask.execute(plancode1).get();
            Log.i("23423423423", "333333333333");
            if ("success".equals(result)) {

            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void dialog(int position) {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(getContext()).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.share_dialog, null);

        final EditText editTitle = dialogView.findViewById(R.id.share_edit_title);
        final EditText editContents = dialogView.findViewById(R.id.share_edit_contents);
        Button button1 = dialogView.findViewById(R.id.share_result_btn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editTitle.getText().toString().length() == 0) {
                    sharePlace = "없음";
                } else {
                    sharePlace = editTitle.getText().toString();
                }

                if (editContents.getText().toString().length() == 0) {
                    shareContents = "없음";
                } else {
                    shareContents = editTitle.getText().toString();
                }

                dataInsert(position);
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }

    public String getNickname() {
        return nickname;
    }


}
