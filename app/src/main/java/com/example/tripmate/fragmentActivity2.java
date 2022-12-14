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
                /* ????????? ????????????
                   1. ?????? ???????????? ?????? ???????????? ?????? ????????? ????????? ???????????? ?????? DB??? ????????????.
                */
                dialog(position);
            }

            @Override
            public void onItemUncheked(int position) {
                 /* ????????? ????????????
                   1. ?????? ???????????? ?????? ??????????????? ?????? ????????? ????????? ????????? 1??? ????????????. ?????? ?????? ??????, ????????? ????????? ????????????.
                */
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("?????? ?????? ??????")        // ?????? ??????
                        .setMessage("?????? ????????? ?????????????????????????")        // ????????? ??????
                        .setCancelable(false)        // ?????? ?????? ????????? ?????? ?????? ??????
                        .setPositiveButton("??????", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                dataDelete(position);
                            }
                        })
                        .setNegativeButton("??????", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = builder.create();    // ????????? ?????? ??????
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
                    sharePlace = "??????";
                } else {
                    sharePlace = editTitle.getText().toString();
                }

                if (editContents.getText().toString().length() == 0) {
                    shareContents = "??????";
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
